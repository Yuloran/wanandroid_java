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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yuloran.module_base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * [可以禁止左右滑动的ViewPager]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 20:06
 *
 * @since 1.0.0
 */
public class NoScrollableViewPager extends ViewPager
{
    private boolean mNoScrollable;

    public NoScrollableViewPager(@NonNull Context context, boolean noScrollable)
    {
        this(context, null);
        mNoScrollable = noScrollable;
    }

    public NoScrollableViewPager(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NoScrollableViewPager, 0, 0);

        try
        {
            mNoScrollable = a.getBoolean(R.styleable.NoScrollableViewPager_noScrollable, false);
        } finally
        {
            a.recycle();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return !mNoScrollable && super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return !mNoScrollable && super.onTouchEvent(ev);
    }
}
