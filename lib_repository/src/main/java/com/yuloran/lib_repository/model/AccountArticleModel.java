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

import com.trello.rxlifecycle3.LifecycleProvider;
import com.yuloran.lib_core.bean.backend.response.Item;
import com.yuloran.lib_core.bean.backend.response.Page;
import com.yuloran.lib_core.bean.backend.response.PageResp;
import com.yuloran.lib_core.template.threadsafe.SafeMutableLiveData;
import com.yuloran.lib_core.utils.ArrayUtil;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.lib_repository.http.ApiProvider;
import com.yuloran.lib_repository.http.common.CommonRequestSubscriber;
import com.yuloran.lib_repository.http.common.ResponsePredicate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
public class AccountArticleModel
{
    private static final String TAG = "AccountArticleModel";

    private SafeMutableLiveData<List<Item>> mArticles = new SafeMutableLiveData<>();

    public AccountArticleModel()
    {
        // 使添加第一个observer时，也能收到回调
        mArticles.setValue(Collections.<Item>emptyList());
    }

    @NonNull
    @MainThread
    public SafeMutableLiveData<List<Item>> getArticles()
    {
        return mArticles;
    }

    public <T> void fetch(@NonNull OfficialAccount account, LifecycleProvider<T> lifecycleProvider)
    {
        Objects.requireNonNull(account, "account is null!");

        ApiProvider.getInstance()
                   .getWanAndroidApi()
                   .getOfficialAccountArticles(account.getAccountId(), 1)
                   .filter(new ResponsePredicate<>())
                   .map(new Function<PageResp, List<Item>>()
                   {
                       @Override
                       public List<Item> apply(PageResp pageResp) throws Exception
                       {
                           Page page = pageResp.getPage();
                           List<Item> items = page.getItems();
                           return ArrayUtil.nonNull(items);
                       }
                   })
                   .doOnNext(new Consumer<List<Item>>()
                   {
                       @Override
                       public void accept(List<Item> items) throws Exception
                       {
                           mArticles.setValue(items);
                       }
                   })
                   .compose(lifecycleProvider.<List<Item>>bindToLifecycle())
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new CommonRequestSubscriber<List<Item>>()
                   {
                       @Override
                       protected void onSuccess(@NonNull List<Item> articles)
                       {
                           Logger.info(TAG, "getOfficialArticles$onSuccess, %d articles.", articles.size());
                       }

                       @Override
                       protected void onError(int errCode, @Nullable String errMsg)
                       {
                           Logger.error(TAG, "getOfficialArticles$onError: errCode:%d, errMsg:%s.", errCode, errMsg);
                       }
                   });
    }
}
