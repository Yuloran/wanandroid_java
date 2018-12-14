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
package com.yuloran.module_base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.yuloran.module_base.R;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * [自定义的ActionBar]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 20:04
 *
 * @since 1.0.0
 */
public class ActionBar extends RelativeLayout
{

    private static final String TAG = "ActionBar";

    private AppCompatImageView mBack;

    private AppCompatTextView mTitle;

    private AppCompatImageView mOption1;

    private AppCompatImageView mOption2;

    public ActionBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        mBack = findViewById(R.id.my_action_bar_back);
        mTitle = findViewById(R.id.my_action_bar_title);
        mOption1 = findViewById(R.id.my_action_bar_option1);
        mOption2 = findViewById(R.id.my_action_bar_option2);
    }

    public AppCompatImageView getBack()
    {
        return mBack;
    }

    public AppCompatTextView getTitle()
    {
        return mTitle;
    }

    public AppCompatImageView getOption1()
    {
        return mOption1;
    }

    public AppCompatImageView getOption2()
    {
        return mOption2;
    }

}
