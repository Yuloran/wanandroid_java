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
import android.view.View;

import com.trello.rxlifecycle3.components.support.RxFragmentActivity;
import com.yuloran.module_base.R;
import com.yuloran.module_base.ui.widget.ActionBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * [BaseActivity]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 16:58
 *
 * @since 1.0.0
 */
public class BaseActivity extends RxFragmentActivity
{
    private static final String TAG = "BaseActivity";

    private ActionBar mActionBar;

    private ConstraintLayout mContentRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_activity);

        mActionBar = findViewById(R.id.action_bar);
        mActionBar.getBack().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        onActionBarCreated(mActionBar);

        mContentRoot = findViewById(R.id.my_content_root);
        onCreateContentLayout(mContentRoot);
    }

    protected void onActionBarCreated(@NonNull ActionBar actionBar)
    {
    }

    protected void onCreateContentLayout(@NonNull ConstraintLayout contentRoot)
    {
    }

}
