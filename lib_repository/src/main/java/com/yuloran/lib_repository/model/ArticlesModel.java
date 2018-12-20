/*
 * Copyright (C) 2018 Yuloran(https://github.com/Yuloran)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuloran.lib_repository.model;

import android.text.Html;

import com.trello.rxlifecycle3.LifecycleProvider;
import com.yuloran.lib_core.bean.backend.response.Item;
import com.yuloran.lib_core.bean.backend.response.Page;
import com.yuloran.lib_core.bean.backend.response.PageResp;
import com.yuloran.lib_core.constant.ErrCode;
import com.yuloran.lib_core.template.threadsafe.SafeMutableLiveData;
import com.yuloran.lib_core.utils.ArrayUtil;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.utils.StringUtil;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.lib_repository.http.ApiProvider;
import com.yuloran.lib_repository.http.common.CommonRequestException;
import com.yuloran.lib_repository.http.common.CommonRequestSubscriber;
import com.yuloran.lib_repository.http.common.ResponsePredicate;
import com.yuloran.lib_repository.viewdata.ArticlesViewData;
import com.yuloran.lib_repository.viewdata.ViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * [公众号文章Model]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/17 20:01
 *
 * @since 1.0.0
 */
public class ArticlesModel
{
    private static final String TAG = "ArticlesModel";

    private SafeMutableLiveData<ArticlesViewData> mArticles = new SafeMutableLiveData<>();

    private volatile Page mPage;

    private boolean mIsLoading;

    public ArticlesModel()
    {
        // 使添加第一个observer时，也能收到回调
        mArticles.setValue(new ArticlesViewData(ViewState.UNINITIALIZED));
    }

    @NonNull
    @MainThread
    public SafeMutableLiveData<ArticlesViewData> getArticles()
    {
        return mArticles;
    }

    @MainThread
    public <T> void fetch(@NonNull OfficialAccount account, LifecycleProvider<T> lifecycleProvider)
    {
        Objects.requireNonNull(account, "account is null!");

        int nextPage = 1;
        if (mPage != null)
        {
            nextPage = mPage.getCurPage() + 1;
            if (nextPage > mPage.getPageCount())
            {
                Logger.warn(TAG, "fetch: over already, why fetch again?");
                return;
            }
        }

        if (mIsLoading)
        {
            Logger.info(TAG, "fetch: is loading, skip.");
            return;
        }

        mArticles.setValue(new ArticlesViewData(ViewState.LOADING));
        mIsLoading = true;

        ApiProvider.getInstance()
                   .getWanAndroidApi()
                   .getOfficialAccountArticles(account.getAccountId(), nextPage)
                   .filter(new ResponsePredicate<>())
                   .map(new Function<PageResp, ArticlesViewData>()
                   {
                       @Override
                       public ArticlesViewData apply(PageResp pageResp)
                       {
                           mPage = pageResp.getPage();
                           if (mPage == null)
                           {
                               throw new CommonRequestException(ErrCode.NULL_RESPONSE, "page response is null!");
                           }
                           return new ArticlesViewData(mPage.getCurPage() >= mPage.getPageCount(), mPage.getItems());
                       }
                   })
                   .map(new Function<ArticlesViewData, ArticlesViewData>()
                   {
                       @Override
                       public ArticlesViewData apply(ArticlesViewData articlesViewData)
                       {
                           List<Item> articles = articlesViewData.getViewData();
                           if (!ArrayUtil.isEmpty(articles))
                           {
                               List<Item> filtered = new ArrayList<>(articles);
                               for (Item item : articles)
                               {
                                   // 过滤非法数据
                                   if (StringUtil.isEmpty(item.getTitle()) || StringUtil.isEmpty(item.getLink()))
                                   {
                                       continue;
                                   }
                                   // wanAndroidApi返回的title常带有html符号，需要转换一下
                                   item.setTitle(Html.fromHtml(item.getTitle()).toString());
                                   filtered.add(item);
                               }
                               articlesViewData.setViewData(filtered);
                           }
                           return articlesViewData;
                       }
                   })
                   .doOnNext(new Consumer<ArticlesViewData>()
                   {
                       @Override
                       public void accept(ArticlesViewData articlesViewData)
                       {
                           mArticles.setValue(articlesViewData);
                       }
                   })
                   .compose(lifecycleProvider.<ArticlesViewData>bindToLifecycle())
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .doFinally(new Action()
                   {
                       @Override
                       public void run() throws Exception
                       {
                           mIsLoading = false;
                       }
                   })
                   .subscribe(new CommonRequestSubscriber<ArticlesViewData>()
                   {
                       @Override
                       protected void onSuccess(@NonNull ArticlesViewData articles)
                       {
                           Logger.info(TAG, "getOfficialArticles$onSuccess, %d articles.", ArrayUtil.sizeof(articles
                                   .getViewData()));
                       }

                       @Override
                       protected void onError(int errCode, @Nullable String errMsg)
                       {
                           Logger.error(TAG, "getOfficialArticles$onError: errCode:%d, errMsg:%s.", errCode, errMsg);
                           mArticles.setValue(new ArticlesViewData(errCode, errMsg));
                       }
                   });
    }
}
