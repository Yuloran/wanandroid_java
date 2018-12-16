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
package com.yuloran.module_base.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle3.components.support.RxFragment;
import com.yuloran.lib_core.utils.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    protected abstract String logTag();

    @Override
    public void onAttach(Context context)
    {
        Logger.debug(logTag(), "onAttach.");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Logger.debug(logTag(), "onCreate.");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        Logger.debug(logTag(), "onCreateView.");
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Logger.debug(logTag(), "onActivityCreated.");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        Logger.debug(logTag(), "onStart.");
        super.onStart();
    }

    @Override
    public void onResume()
    {
        Logger.debug(logTag(), "onResume.");
        super.onResume();
    }

    @Override
    public void onPause()
    {
        Logger.debug(logTag(), "onPause.");
        super.onPause();
    }

    @Override
    public void onStop()
    {
        Logger.debug(logTag(), "onStop.");
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        Logger.debug(logTag(), "onDestroyView.");
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        Logger.debug(logTag(), "onDestroy.");
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        Logger.debug(logTag(), "onDetach.");
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        Logger.debug(logTag(), "setUserVisibleHint: " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

}
