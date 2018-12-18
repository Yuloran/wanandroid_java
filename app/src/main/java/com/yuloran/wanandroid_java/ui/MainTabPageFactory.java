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

import com.yuloran.module_base.util.ResUtil;
import com.yuloran.wanandroid_java.ui.main.category.CategoryFragment;
import com.yuloran.wanandroid_java.ui.main.links.LinksFragment;
import com.yuloran.wanandroid_java.ui.main.officialaccount.OfficialAccountFragment;
import com.yuloran.wanandroid_java.ui.main.project.ProjectFragment;
import com.yuloran.wanandroid_java.ui.main.recommend.RecommendFragment;

import androidx.fragment.app.Fragment;

/**
 * [主页各页面Fragment生成器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 18:50
 *
 * @since 1.0.0
 */
final class MainTabPageFactory
{
    private static final String TAG = "MainTabPageFactory";

    private MainTabPageFactory()
    {
    }

    static Fragment instantiate(TabConfig.Tab tab)
    {
        switch (tab)
        {
            case RECOMMEND:
                return ResUtil.instantiateFragment(RecommendFragment.class.getName(), null);
            case OFFICIAL_ACCOUNT:
                return ResUtil.instantiateFragment(OfficialAccountFragment.class.getName(), null);
            case CATEGORY:
                return ResUtil.instantiateFragment(CategoryFragment.class.getName(), null);
            case PROJECT:
                return ResUtil.instantiateFragment(ProjectFragment.class.getName(), null);
            case LINKS:
                return ResUtil.instantiateFragment(LinksFragment.class.getName(), null);
            default:
        }
        return null;
    }
}
