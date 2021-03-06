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
public abstract class BaseActivity extends RxFragmentActivity
{
    private static final String TAG = "BaseActivity";

    protected abstract void onActionBarCreated(@NonNull ActionBar actionBar);

    protected abstract void onCreateContentLayout(@NonNull ConstraintLayout contentRoot);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_activity);

        // 因为BaseActivity也设置了background，所以此时可以去除AppTheme中设置的windowBackground，避免过度绘制
        getWindow().setBackgroundDrawable(null);

        ActionBar actionBar = findViewById(R.id.action_bar);
        actionBar.getBack().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        onActionBarCreated(actionBar);

        ConstraintLayout contentRoot = findViewById(R.id.my_content_root);
        onCreateContentLayout(contentRoot);
    }

}
