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
package com.yuloran.module_base.ui.adapter.recyclerview.loadmore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuloran.module_base.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * [LoadMoreItemViewBinder]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/18 23:08
 *
 * @since 1.0.0
 */
public final class LoadMoreItemViewBinder
        extends ItemViewBinder<LoadMoreItem, LoadMoreItemViewBinder.FooterLoadMoreViewHolder>
{
    @NonNull
    @Override
    protected FooterLoadMoreViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent)
    {
        return new FooterLoadMoreViewHolder(inflater.inflate(R.layout.layout_load_more, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull FooterLoadMoreViewHolder holder, @NonNull LoadMoreItem item)
    {
    }

    class FooterLoadMoreViewHolder extends RecyclerView.ViewHolder
    {
        FooterLoadMoreViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
