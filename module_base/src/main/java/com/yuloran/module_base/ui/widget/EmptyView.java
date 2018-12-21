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
package com.yuloran.module_base.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.yuloran.lib_core.constant.Cons;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.bean.ViewState;
import com.yuloran.module_base.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * [空布局]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/20 23:18
 *
 * @since 1.0.0
 */
public class EmptyView extends LinearLayout
{
    private static final String TAG = "EmptyView";

    private ProgressBar mLoading;
    private AppCompatImageView mErrIcon;
    private AppCompatTextView mErrDescription;


    public EmptyView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        mLoading = findViewById(R.id.loading_view);
        mErrIcon = findViewById(R.id.error_icon);
        mErrDescription = findViewById(R.id.error_description);
    }

    public void setViewState(ViewState viewState)
    {
        Logger.debug(TAG, "setViewState: " + viewState);
        switch (viewState.getState())
        {
            case Cons.STATE_LOADING:
                mLoading.setVisibility(VISIBLE);
                mErrIcon.setVisibility(GONE);
                mErrDescription.setVisibility(GONE);
                setVisibility(VISIBLE);
                break;
            case Cons.STATE_LOAD_SUCCESS:
                setVisibility(GONE);
                break;
            case Cons.STATE_LOAD_FAILURE:
                mLoading.setVisibility(GONE);
                mErrIcon.setVisibility(VISIBLE);
                mErrDescription.setVisibility(VISIBLE);
                setVisibility(VISIBLE);
                break;
            default:
        }
    }

}
