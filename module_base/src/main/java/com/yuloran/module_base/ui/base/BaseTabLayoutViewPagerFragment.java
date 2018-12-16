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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.yuloran.module_base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * [BaseTabLayoutViewPagerFragment]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/15 17:08
 *
 * @since 1.0.0
 */
public abstract class BaseTabLayoutViewPagerFragment extends BaseFragment
{
    private static final String TAG = "BaseTabLayoutViewPagerFragment";

    protected View mRootView;
    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.simple_tablayout_with_viewpager, container, false);
        mTabLayout = mRootView.findViewById(R.id.my_tab_layout);
        mViewPager = mRootView.findViewById(R.id.my_view_pager);
        mTabLayout.setupWithViewPager(mViewPager);
        return mRootView;
    }
}
