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
import com.yuloran.wanandroid_java.ui.knowledgestructure.KnowledgeStructureFragment;
import com.yuloran.wanandroid_java.ui.navigation.NavigationFragment;
import com.yuloran.wanandroid_java.ui.officialaccount.OfficialAccountFragment;
import com.yuloran.wanandroid_java.ui.project.ProjectFragment;
import com.yuloran.wanandroid_java.ui.recommend.RecommendFragment;

import androidx.fragment.app.Fragment;

/**
 * [主页各页面Fragment生成器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 18:50
 *
 * @since 1.0.0
 */
public final class MainPageFragmentFactory
{
    /** 获取页面类型的key */
    public static final String KEY_GET_PAGE = "KEY_GET_PAGE";

    private static final String TAG = "MainPageFragmentFactory";

    private MainPageFragmentFactory()
    {
    }

    static Fragment instantiate(ResUtil.MainPage mainPage)
    {
        byte pageType = mainPage.getType();
        switch (pageType)
        {
            case ResUtil.OFFICIAL_ACCOUNT:
                return OfficialAccountFragment.newInstance(mainPage);
            case ResUtil.RECOMMEND:
                return RecommendFragment.newInstance(mainPage);
            case ResUtil.KNOWLEDGE_STRUCTURE:
                return KnowledgeStructureFragment.newInstance(mainPage);
            case ResUtil.PROJECT:
                return ProjectFragment.newInstance(mainPage);
            case ResUtil.NAVIGATION:
                return NavigationFragment.newInstance(mainPage);
            default:
        }
        return null;
    }
}
