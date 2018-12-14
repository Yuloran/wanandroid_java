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
package com.yuloran.module_base.base;

import android.os.Bundle;

import com.trello.rxlifecycle3.components.support.RxFragment;
import com.yuloran.lib_core.utils.Logger;

/**
 * [BaseFragment]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 16:58
 *
 * @since 1.0.0
 */
public abstract class BaseFragment extends RxFragment
{
    protected boolean mIsViewInitiated;

    protected boolean mIsDataLoaded;

    protected abstract String logTag();

    protected abstract void reallyLoad();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mIsViewInitiated = true;
        loadIfVisible(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            loadIfVisible(false);
        }
    }

    public void loadIfVisible(boolean forceUpdate)
    {
        if (getUserVisibleHint() && mIsViewInitiated && (!mIsDataLoaded || forceUpdate))
        {
            Logger.debug(logTag(), "loadIfVisible: forceUpdate? " + forceUpdate);
            reallyLoad();
            return;
        }
        Logger.debug(logTag(), "loadIfVisible: invisible.");
    }
}
