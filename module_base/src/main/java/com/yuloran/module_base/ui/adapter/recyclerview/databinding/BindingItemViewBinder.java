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
package com.yuloran.module_base.ui.adapter.recyclerview.databinding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yuloran.module_base.ui.adapter.recyclerview.OnItemClickListener;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import me.drakeet.multitype.ItemViewBinder;

/**
 * [ItemViewBinder for DataBinding]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/17 16:11
 *
 * @since 1.0.0
 */
public abstract class BindingItemViewBinder<T, E extends ViewDataBinding>
        extends ItemViewBinder<T, BindingViewHolder<E>>
{
    protected OnItemClickListener<T> mOnItemClickListener;

    public BindingItemViewBinder()
    {
    }

    public BindingItemViewBinder(@Nullable OnItemClickListener<T> onItemClickListener)
    {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener<T> onItemClickListener)
    {
        mOnItemClickListener = onItemClickListener;
    }

    @LayoutRes
    protected abstract int getItemLayoutId();

    @NonNull
    @Override
    protected BindingViewHolder<E> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent)
    {
        return new BindingViewHolder<>(DataBindingUtil.<E>inflate(inflater, getItemLayoutId(), parent, false));
    }
}
