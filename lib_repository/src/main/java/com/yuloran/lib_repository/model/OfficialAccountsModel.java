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

import android.annotation.SuppressLint;

import com.trello.rxlifecycle3.LifecycleProvider;
import com.yuloran.lib_core.bean.ViewState;
import com.yuloran.lib_core.bean.backend.response.Section;
import com.yuloran.lib_core.bean.backend.response.SectionResp;
import com.yuloran.lib_core.template.threadsafe.SafeMutableLiveData;
import com.yuloran.lib_core.utils.ArrayUtil;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.utils.StringUtil;
import com.yuloran.lib_repository.database.AppDatabase;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.lib_repository.database.OfficialAccountDao;
import com.yuloran.lib_repository.http.ApiProvider;
import com.yuloran.lib_repository.http.common.CommonRequestSubscriber;
import com.yuloran.lib_repository.http.common.ResponsePredicate;
import com.yuloran.lib_repository.viewdata.BaseViewData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * [获取微信公众号的Model]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 11:31
 *
 * @since 1.0.0
 */
public class OfficialAccountsModel
{
    private static final String TAG = "OfficialAccountsModel";

    private SafeMutableLiveData<BaseViewData<List<OfficialAccount>>> mOfficialAccounts = new SafeMutableLiveData<>();

    private OfficialAccountDao mDao;

    private boolean mIsLoading;

    public OfficialAccountsModel()
    {
        mOfficialAccounts.setValue(new BaseViewData<List<OfficialAccount>>(ViewState.UNINITIALIZED));
        mDao = AppDatabase.getInstance().officialAccountDao();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @NonNull
    public LiveData<BaseViewData<List<OfficialAccount>>> getOfficialAccounts()
    {
        Single.fromCallable(new Callable<List<OfficialAccount>>()
        {
            @Override
            public List<OfficialAccount> call() throws Exception
            {
                return mDao.query();
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<OfficialAccount>>()
        {
            @Override
            public void accept(List<OfficialAccount> officialAccounts) throws Exception
            {
                if (!officialAccounts.isEmpty())
                {
                    mOfficialAccounts.setValue(new BaseViewData<>(officialAccounts));
                }
            }
        });
        return mOfficialAccounts;
    }

    public <T> void fetch(LifecycleProvider<T> lifecycleProvider)
    {
        if (mIsLoading)
        {
            Logger.info(TAG, "fetch: is loading, skip.");
            return;
        }

        mOfficialAccounts.setValue(new BaseViewData<List<OfficialAccount>>(ViewState.LOADING));
        mIsLoading = true;

        ApiProvider.getInstance()
                   .getWanAndroidApi()
                   .getOfficialAccounts()
                   .filter(new ResponsePredicate<>())
                   .map(new SectionResp2Accounts())
                   .doOnNext(new CacheAccounts())
                   .compose(lifecycleProvider.<List<OfficialAccount>>bindToLifecycle())
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
                   .subscribe(new CommonRequestSubscriber<List<OfficialAccount>>()
                   {
                       @Override
                       protected void onSuccess(@NonNull List<OfficialAccount> accounts)
                       {
                           Logger.info(TAG, "getOfficialAccounts$onSuccess, %d accounts.", accounts.size());
                       }

                       @Override
                       protected void onError(int errCode, @Nullable String errMsg)
                       {
                           Logger.error(TAG, "getOfficialAccounts$onError: errCode:%d, errMsg:%s.", errCode, errMsg);
                           mOfficialAccounts.setValue(new BaseViewData<List<OfficialAccount>>(new ViewState(errCode,
                                   errMsg)));
                       }
                   });
    }

    private static class SectionResp2Accounts implements Function<SectionResp, List<OfficialAccount>>
    {
        @Override
        public List<OfficialAccount> apply(SectionResp sectionResp)
        {
            List<Section> sections = sectionResp.getSections();
            Logger.info(TAG, "getOfficialAccounts$map: " + sections);
            if (!ArrayUtil.isEmpty(sections))
            {
                List<OfficialAccount> accounts = new ArrayList<>(sections.size());
                for (Section section : sections)
                {
                    String name = section.getName();
                    if (StringUtil.isEmpty(name))
                    {
                        continue;
                    }
                    accounts.add(new OfficialAccount(section.getId(), name));
                }
                return accounts;
            }
            return Collections.emptyList();
        }
    }

    private class CacheAccounts implements Consumer<List<OfficialAccount>>
    {
        @Override
        public void accept(List<OfficialAccount> officialAccounts)
        {
            Logger.info(TAG, "getOfficialAccounts$doOnNext: " + officialAccounts);
            // 使用liveData的问题：此处为增量更新，即有删除、更新、新增。这些操作是一个整体，
            // 但是liveData每个操作都会回调onChange()。
            mDao.clear();
            mDao.bulkInsert(officialAccounts);
            mOfficialAccounts.setValue(new BaseViewData<>(officialAccounts));
        }
    }
}
