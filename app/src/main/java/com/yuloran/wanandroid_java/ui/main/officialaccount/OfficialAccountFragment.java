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

import com.yuloran.lib_core.utils.ArrayUtil;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.module_base.ui.base.BaseTabLayoutViewPagerFragment;
import com.yuloran.wanandroid_java.viewmodel.OfficialAccountVM;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * [微信公众号的Fragment]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 18:53
 *
 * @since 1.0.0
 */
public class OfficialAccountFragment extends BaseTabLayoutViewPagerFragment
{
    private static final String TAG = "OfficialAccountFragment";

    private OfficialAccountVM mAccountVM;

    private OfficialAccountPagerAdapter mPagerAdapter;

    private int fetchCount;

    @Override
    protected String logTag()
    {
        return TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mAccountVM = ViewModelProviders.of(this).get(OfficialAccountVM.class);
        mAccountVM.getAccounts().observe(this, new Observer<List<OfficialAccount>>()
        {
            @Override
            public void onChanged(List<OfficialAccount> officialAccounts)
            {
                Logger.info(TAG, "onChanged: " + officialAccounts);

                officialAccounts = ArrayUtil.nonNull(officialAccounts);

                // 仅fetch一次，否则当服务器数据为空时，会进入死循环
                if (officialAccounts.isEmpty() && fetchCount == 0)
                {
                    fetchCount++;
                    mAccountVM.fetch(OfficialAccountFragment.this);
                }

                if (mPagerAdapter == null)
                {
                    Logger.info(TAG, "onChanged: init pagerAdapter.");
                    mPagerAdapter = new OfficialAccountPagerAdapter(getChildFragmentManager(), officialAccounts);
                    mViewPager.setAdapter(mPagerAdapter);
                    return;
                }

                Logger.info(TAG, "onChanged: update data source.");
                mPagerAdapter.setDataSource(officialAccounts);
                mPagerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onViewCreated()
    {
        // FragmentPagerAdapter：Fragment超出缓存范围后，View会被销毁，但是Fragment只是调用onStop。
        // 再次跳至该Fragment时，需要重新绑定adapter。
        if (mPagerAdapter != null)
        {
            mViewPager.setAdapter(mPagerAdapter);
        }
    }
}
