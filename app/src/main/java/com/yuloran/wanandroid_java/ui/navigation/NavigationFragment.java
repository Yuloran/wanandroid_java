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
package com.yuloran.wanandroid_java.ui.navigation;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuloran.module_base.base.BaseFragment;
import com.yuloran.module_base.util.ResUtil;
import com.yuloran.wanandroid_java.ui.MainPageFragmentFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * [导航页的Fragment]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 18:53
 *
 * @since 1.0.0
 */
public class NavigationFragment extends BaseFragment
{
    private static final String TAG = "OfficialAccountFragment";

    private ResUtil.MainPage mMainPage;

    @Override
    protected String logTag()
    {
        return TAG;
    }

    @Override
    protected void reallyLoad()
    {
    }

    /**
     * Create a new instance of {@link NavigationFragment}, providing "mainPage"
     * as an argument.
     */
    public static NavigationFragment newInstance(ResUtil.MainPage mainPage)
    {
        NavigationFragment fragment = new NavigationFragment();

        // Supply mainPage input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(MainPageFragmentFactory.KEY_GET_PAGE, mainPage);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null)
        {
            mMainPage = arguments.getParcelable(MainPageFragmentFactory.KEY_GET_PAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        View root = inflater.inflate(android.R.layout.simple_list_item_1, container, false);
        TextView textView = root.findViewById(android.R.id.text1);
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setText(mMainPage.getTitle());
        return root;
    }
}
