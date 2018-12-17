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
import com.yuloran.lib_core.bean.backend.response.Section;
import com.yuloran.lib_core.bean.backend.response.SectionResp;
import com.yuloran.lib_core.utils.ArrayUtil;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.utils.StringUtil;
import com.yuloran.lib_repository.database.AppDatabase;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.lib_repository.database.OfficialAccountDao;
import com.yuloran.lib_repository.http.ApiProvider;
import com.yuloran.lib_repository.http.common.CommonRequestSubscriber;
import com.yuloran.lib_repository.http.common.ResponsePredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
public class OfficialAccountModel
{
    private static final String TAG = "OfficialAccountModel";

    @NonNull
    public LiveData<List<OfficialAccount>> getOfficialAccounts()
    {
        OfficialAccountDao officialAccountDao = AppDatabase.getInstance().officialAccountDao();
        return officialAccountDao.query();
    }

    public <T> void fetch(LifecycleProvider<T> lifecycleProvider)
    {
        ApiProvider.getInstance()
                   .getWanAndroidApi()
                   .getOfficialAccounts()
                   .filter(new ResponsePredicate<>())
                   .map(new SectionResp2Accounts())
                   .doOnNext(new CacheAccounts())
                   .compose(lifecycleProvider.<List<OfficialAccount>>bindToLifecycle())
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new CommonRequestSubscriber<List<OfficialAccount>>()
                   {
                       @Override
                       protected void onSuccess(@NonNull List<OfficialAccount> response)
                       {
                           Logger.info(TAG, "getOfficialAccounts$onSuccess, %d accounts.", response.size());
                       }

                       @Override
                       protected void onError(int errCode, @Nullable String errMsg)
                       {
                           Logger.error(TAG, "getOfficialAccounts$onError: errCode:%d, errMsg:%s.", errCode, errMsg);
                       }
                   });
    }

    private static class SectionResp2Accounts implements Function<SectionResp, List<OfficialAccount>>
    {
        @Override
        public List<OfficialAccount> apply(SectionResp sectionResp) throws Exception
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

    private static class CacheAccounts implements Consumer<List<OfficialAccount>>
    {
        @Override
        public void accept(List<OfficialAccount> officialAccounts) throws Exception
        {
            Logger.info(TAG, "getOfficialAccounts$doOnNext: " + officialAccounts);
            // 使用liveData的问题：此处为增量更新，即有删除、更新、新增。这些操作是一个整体，
            // 但是liveData每个操作都会回调onChange()。
            // TODO Room通过创建触发器监听表的修改(UPDATE, DELETE, INSERT), 需要修复多余回调问题
            // TODO (1) 使用RxJava的debounce (2) 返回普通类型，自行封装为LiveData(推荐!)
            AppDatabase.getInstance().officialAccountDao().bulkInsert(officialAccounts);
        }
    }
}
