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
package com.yuloran.wanandroid_java.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.yuloran.module_base.ui.base.BaseActivity;
import com.yuloran.module_base.ui.widget.ActionBar;
import com.yuloran.wanandroid_java.R;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

/**
 * [MainActivity]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 18:54
 *
 * @since 1.0.0
 */
public class MainActivity extends BaseActivity
{
    private static final String TAG = "MainActivity";

    private View mRootView;
    private ViewPager mViewPager;
    private TabLayout mBottomTab;

    @Override
    protected void onActionBarCreated(@NonNull ActionBar actionBar)
    {
        actionBar.setVisibility(View.GONE);
    }

    @Override
    protected void onCreateContentLayout(@NonNull ConstraintLayout contentRoot)
    {
        mRootView = getLayoutInflater().inflate(R.layout.activity_main, contentRoot, true);
        mViewPager = mRootView.findViewById(R.id.my_view_pager);
        mBottomTab = mRootView.findViewById(R.id.my_tab_layout);

        mViewPager.setAdapter(new MainTabPagerAdapter(getSupportFragmentManager()));
        mBottomTab.setupWithViewPager(mViewPager);
        for (int i = 0; i < mBottomTab.getTabCount(); i++)
        {
            TabLayout.Tab tab = mBottomTab.getTabAt(i);
            if (tab != null)
            {
                View customView = getLayoutInflater().inflate(R.layout.layout_tab_customview, mBottomTab, false);
                ImageView customImageView = customView.findViewById(R.id.tab_icon);
                customImageView.setImageResource(TabConfig.getMainTabs().get(i).getIconResId());
                TextView customTextView = customView.findViewById(R.id.tab_title);
                customTextView.setText(TabConfig.getMainTabs().get(i).getTitle());
                tab.setCustomView(customView);
            }
        }
    }
}
