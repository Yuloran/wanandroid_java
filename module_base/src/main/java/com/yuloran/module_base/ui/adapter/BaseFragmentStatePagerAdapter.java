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
package com.yuloran.module_base.ui.adapter;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * [BaseFragmentStatePagerAdapter]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 14:20
 *
 * @since 1.0.0
 */
public abstract class BaseFragmentStatePagerAdapter<E, T extends List<E>> extends FragmentStatePagerAdapter
{
    protected final T mData;

    public BaseFragmentStatePagerAdapter(FragmentManager fm, @NonNull T data)
    {
        super(fm);

        Objects.requireNonNull(data, "data is null!");
        mData = data;
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    public void setDataSource(@NonNull T data)
    {
        Objects.requireNonNull(data, "data is null!");
        mData.clear();
        mData.addAll(data);
    }

}
