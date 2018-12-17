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

import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.module_base.ui.adapter.BaseFragmentStatePagerAdapter;
import com.yuloran.module_base.util.ResUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * [公众号列表页面]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 14:31
 *
 * @since 1.0.0
 */
public class OfficialAccountPagerAdapter extends BaseFragmentStatePagerAdapter<OfficialAccount, List<OfficialAccount>>
{
    OfficialAccountPagerAdapter(FragmentManager fm, @NonNull List<OfficialAccount> data)
    {
        super(fm, data);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mData.get(position).getAuthor();
    }

    @Override
    public Fragment getItem(int position)
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable("official_account", mData.get(position));
        return ResUtil.instantiateFragment(AccountArticlesFragment.class.getName(), bundle);
    }
}
