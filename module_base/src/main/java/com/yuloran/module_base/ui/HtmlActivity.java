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
package com.yuloran.module_base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.just.agentweb.AgentWeb;
import com.yuloran.lib_core.constant.Cons;
import com.yuloran.lib_core.router.BaseModule;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.utils.StringUtil;
import com.yuloran.module_base.R;
import com.yuloran.module_base.ui.base.BaseActivity;
import com.yuloran.module_base.ui.widget.ActionBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * [HtmlActivity]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/20 15:27
 *
 * @since 1.0.0
 */
@Route(path = BaseModule.Activity.HTML_ACTIVITY)
public class HtmlActivity extends BaseActivity
{
    private static final String TAG = "HtmlActivity";

    private String mUrl;

    private AgentWeb mAgentWeb;

    @Override
    protected void onActionBarCreated(@NonNull ActionBar actionBar)
    {
        Intent intent = getIntent();
        Bundle bundle = intent == null ? null : intent.getBundleExtra(Cons.KEY_BUNDLE);

        if (bundle == null)
        {
            Logger.warn(TAG, "bundle is null!");
            finish();
            return;
        }

        String title = bundle.getString(Cons.KEY_TITLE);
        mUrl = bundle.getString(Cons.KEY_URL);
        if (StringUtil.isEmpty(title) || StringUtil.isEmpty(mUrl))
        {
            Logger.warn(TAG, "url is null!");
            finish();
            return;
        }

        actionBar.getTitle().setText(title);
    }

    @Override
    protected void onCreateContentLayout(@NonNull ConstraintLayout contentRoot)
    {
        if (!StringUtil.isEmpty(mUrl))
        {
            mAgentWeb = AgentWeb.with(this)
                                .setAgentWebParent(contentRoot, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                                        .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                                .useDefaultIndicator()
                                .setMainFrameErrorView(R.layout.layout_network_abnormal, R.id.error_icon)
                                .createAgentWeb()
                                .ready()
                                .go(mUrl);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (mAgentWeb != null)
        {
            mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (mAgentWeb != null)
        {
            mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mAgentWeb != null)
        {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }
}
