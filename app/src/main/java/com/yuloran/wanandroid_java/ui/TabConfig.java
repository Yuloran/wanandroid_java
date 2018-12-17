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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * [主页Tab配置]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 10:36
 *
 * @since 1.0.0
 */
public final class TabConfig
{
    private static final String TAG = "TabConfig";

    private static final List<Tab> MAIN_TABS = new ArrayList<>(5);

    static
    {
        MAIN_TABS.add(Tab.RECOMMEND);
        MAIN_TABS.add(Tab.OFFICIAL_ACCOUNT);
        MAIN_TABS.add(Tab.KNOWLEDGE_STRUCTURE);
        MAIN_TABS.add(Tab.PROJECT);
        MAIN_TABS.add(Tab.NAVIGATION);
    }

    private TabConfig()
    {
    }

    /**
     * 获取主页所有Tabs:<br />
     * <ol>
     * <li>{@link Tab#RECOMMEND}
     * <li>{@link Tab#OFFICIAL_ACCOUNT}
     * <li>{@link Tab#KNOWLEDGE_STRUCTURE}
     * <li>{@link Tab#PROJECT}
     * <li>{@link Tab#NAVIGATION}
     * </ol>
     *
     * @return 主页所有tabs
     */
    static List<Tab> getMainTabs()
    {
        return MAIN_TABS;
    }

    public enum Tab
    {
        /** 微信公众号 */
        RECOMMEND(com.yuloran.module_base.R.string.tab_recommend),
        /** 推荐 */
        OFFICIAL_ACCOUNT(com.yuloran.module_base.R.string.tab_official_account),
        /** 知识体系 */
        KNOWLEDGE_STRUCTURE(com.yuloran.module_base.R.string.tab_knowledge_structure),
        /** 项目 */
        PROJECT(com.yuloran.module_base.R.string.tab_project),
        /** 导航 */
        NAVIGATION(com.yuloran.module_base.R.string.tab_navigation);

        /** 页面标题的字符串id */
        private int mTitleResId;

        Tab(int titleResId)
        {
            mTitleResId = titleResId;
        }

        @NonNull
        public String getTitle()
        {
            return ResUtil.getString(mTitleResId);
        }
    }
}
