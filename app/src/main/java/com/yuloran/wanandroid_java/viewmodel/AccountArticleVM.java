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
package com.yuloran.wanandroid_java.viewmodel;

import com.trello.rxlifecycle3.LifecycleProvider;
import com.yuloran.lib_core.bean.backend.response.Item;
import com.yuloran.lib_core.template.threadsafe.SafeMutableLiveData;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.utils.ThreadUtil;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.lib_repository.model.AccountArticleModel;

import java.util.List;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * [公众号文章ViewModel]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/17 19:58
 *
 * @since 1.0.0
 */
public class AccountArticleVM extends ViewModel
{
    private static final String TAG = "AccountArticleVM";

    private AccountArticleModel mModel;

    private SafeMutableLiveData<List<Item>> mArticles;

    public AccountArticleVM()
    {
        mModel = new AccountArticleModel();
    }

    @NonNull
    @MainThread
    public LiveData<List<Item>> getArticles()
    {
        ThreadUtil.assertMainThread("getArticles");
        if (mArticles == null)
        {
            mArticles = mModel.getArticles();
        }
        return mArticles;
    }

    public <T> void fetch(@NonNull OfficialAccount account, LifecycleProvider<T> lifecycleProvider)
    {
        Logger.info(TAG, "fetch: cache expired, fetch from server.");
        mModel.fetch(account, lifecycleProvider);
    }
}