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

import com.yuloran.lib_core.bean.backend.response.Item;
import com.yuloran.module_base.ui.adapter.recyclerview.OnItemClickListener;
import com.yuloran.module_base.ui.adapter.recyclerview.databinding.BindingItemViewBinder;
import com.yuloran.module_base.ui.adapter.recyclerview.databinding.BindingViewHolder;
import com.yuloran.wanandroid_java.R;
import com.yuloran.wanandroid_java.databinding.ItemAccountArticleBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * [文章列表item渲染器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/17 20:46
 *
 * @since 1.0.0
 */
public class ArticleItemViewBinder extends BindingItemViewBinder<Item, ItemAccountArticleBinding>
{
    ArticleItemViewBinder(@Nullable OnItemClickListener<Item> onItemClickListener)
    {
        super(onItemClickListener);
    }

    @Override
    protected int getItemLayoutId()
    {
        return R.layout.item_account_article;
    }

    @Override
    protected void onBindViewHolder(@NonNull BindingViewHolder<ItemAccountArticleBinding> holder, @NonNull final Item
            item)
    {
        holder.getBinding().setItem(item);
        holder.getBinding().setVisible(getPosition(holder) < getAdapter().getItemCount() - 1);
        holder.getBinding().setPresenter(mOnItemClickListener);
        holder.getBinding().executePendingBindings();

    }
}
