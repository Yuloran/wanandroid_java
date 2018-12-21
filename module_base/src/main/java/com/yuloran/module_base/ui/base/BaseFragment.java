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
import android.view.ViewStub;

import com.trello.rxlifecycle3.components.support.RxFragment;
import com.yuloran.lib_core.constant.Cons;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.bean.ViewState;
import com.yuloran.module_base.R;
import com.yuloran.module_base.ui.widget.EmptyView;

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
    private static final boolean DEBUG = false;

    private View mRoot;

    private ViewGroup mContentParent;

    private EmptyView mEmptyView;

    protected abstract String logTag();

    protected abstract void onRootInflated(@NonNull LayoutInflater inflater, @NonNull ViewGroup contentParent);

    @Override
    public void onAttach(Context context)
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onAttach.");
        }
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onCreate.");
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onCreateView.");
        }
        mRoot = inflater.inflate(R.layout.base_fragment, container, false);
        mContentParent = mRoot.findViewById(R.id.fragment_content_root);
        onRootInflated(inflater, mContentParent);
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onActivityCreated.");
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onStart.");
        }
        super.onStart();
    }

    @Override
    public void onResume()
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onResume.");
        }
        super.onResume();
    }

    @Override
    public void onPause()
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onPause.");
        }
        super.onPause();
    }

    @Override
    public void onStop()
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onStop.");
        }
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onDestroyView.");
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onDestroy.");
        }
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        if (DEBUG)
        {
            Logger.debug(logTag(), "onDetach.");
        }
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        Logger.debug(logTag(), "setUserVisibleHint: " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void setViewState(ViewState viewState)
    {
        ViewStub stub = mRoot.findViewById(R.id.fragment_stub_import);
        if (stub != null)
        {
            mEmptyView = (EmptyView) stub.inflate();
        }

        switch (viewState.getViewState())
        {
            case Cons.STATE_LOADING:
                mContentParent.setVisibility(View.GONE);
                mEmptyView.setViewState(viewState);
                break;
            case Cons.STATE_LOAD_SUCCESS:
                mEmptyView.setViewState(viewState);
                mContentParent.setVisibility(View.VISIBLE);
                break;
            case Cons.STATE_LOAD_FAILURE:
                mContentParent.setVisibility(View.INVISIBLE);
                mEmptyView.setViewState(viewState);
                break;
            default:
        }
    }

}
