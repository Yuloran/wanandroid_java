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
package com.yuloran.wanandroid_java.ui.main.officialaccount;

import android.os.Bundle;

import com.yuloran.lib_core.bean.backend.response.Item;
import com.yuloran.lib_core.utils.ArrayUtil;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.module_base.ui.adapter.recyclerview.MultiTypeAdapterEx;
import com.yuloran.module_base.ui.base.BaseRecyclerViewFragment;
import com.yuloran.wanandroid_java.viewmodel.AccountArticleVM;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * [公众号文章列表页面]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/17 19:06
 *
 * @since 1.0.0
 */
public class AccountArticlesFragment extends BaseRecyclerViewFragment
{
    private static final String TAG = "AccountArticlesFragment";

    @Override
    protected String logTag()
    {
        return TAG;
    }

    private OfficialAccount mOfficialAccount;

    private AccountArticleVM mVM;

    private MultiTypeAdapterEx mMultiTypeAdapter;

    private int fetchCount;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            mOfficialAccount = bundle.getParcelable("official_account");
        }

        mVM = ViewModelProviders.of(this).get(AccountArticleVM.class);
        mVM.getArticles().observe(this, new Observer<List<Item>>()
        {
            @Override
            public void onChanged(List<Item> items)
            {
                Logger.info(TAG, "onChanged: " + items);

                items = ArrayUtil.nonNull(items);

                // 仅fetch一次，否则当服务器数据为空时，会进入死循环
                if (items.isEmpty() && fetchCount == 0)
                {
                    fetchCount++;
                    mVM.fetch(mOfficialAccount, AccountArticlesFragment.this);
                }

                if (mMultiTypeAdapter == null)
                {
                    Logger.info(TAG, "onChanged: init multiTypeAdapter.");
                    mMultiTypeAdapter = new MultiTypeAdapterEx();
                    mMultiTypeAdapter.register(Item.class, new ArticleItemViewBinder());
                    mMultiTypeAdapter.setDataSource(items);
                    mRecyclerView.setAdapter(mMultiTypeAdapter);
                    return;
                }

                Logger.info(TAG, "onChanged: update data source.");
                mMultiTypeAdapter.addAll(items);
            }
        });
    }

    @Override
    protected void onRecyclerViewCreated()
    {
        if (mMultiTypeAdapter != null)
        {
            mRecyclerView.setAdapter(mMultiTypeAdapter);
        }
    }
}
