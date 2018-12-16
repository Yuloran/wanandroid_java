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

import com.trello.rxlifecycle3.LifecycleTransformer;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.lib_repository.model.OfficialAccountModel;

import java.util.List;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * [微信公众号ViewModel]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 11:19
 *
 * @since 1.0.0
 */
public class OfficialAccountVM extends ViewModel
{
    private static final String TAG = "OfficialAccountVM";

    private final OfficialAccountModel mModel;

    private LiveData<List<OfficialAccount>> mAccounts;

    public OfficialAccountVM()
    {
        mModel = new OfficialAccountModel();
    }

    @MainThread
    public LiveData<List<OfficialAccount>> getAccounts(LifecycleTransformer<List<OfficialAccount>> lifecycleTransformer)
    {
        if (mAccounts == null)
        {
            Logger.info(TAG, "getAccounts: init accounts liveData.");
            mAccounts = mModel.getOfficialAccounts(lifecycleTransformer);
        } else
        {
            Logger.info(TAG, "getAccounts: invalidate accounts liveData.");
            mModel.invalidate(lifecycleTransformer);
        }
        return mAccounts;
    }
}
