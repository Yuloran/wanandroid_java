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
package com.yuloran.module_base.ui.adapter.recyclerview;

import com.yuloran.lib_core.utils.ArrayUtil;
import com.yuloran.module_base.ui.adapter.recyclerview.loadmore.LoadMoreDelegate;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * [扩展{@link MultiTypeAdapter}, 增加常用操作接口]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/17 16:34
 *
 * @since 1.0.0
 */
public class MultiTypeAdapterEx extends MultiTypeAdapter
{
    private static final String TAG = "MultiTypeAdapterEx";

    private List<Object> mItems = new ArrayList<>();

    private LoadMoreDelegate mLoadMoreDelegate;

    private LoadMoreDelegate.OnLoadMoreListener mOnLoadMoreListener;

    public MultiTypeAdapterEx()
    {
        super();
        setItems(mItems);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
    {
        mLoadMoreDelegate = new LoadMoreDelegate(recyclerView, this);
        if (mOnLoadMoreListener != null)
        {
            mLoadMoreDelegate.setOnLoadMoreListener(mOnLoadMoreListener);
        }
    }

    public void setOnLoadMoreListener(@NonNull LoadMoreDelegate.OnLoadMoreListener onLoadMoreListener)
    {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void setLoadState(@LoadMoreDelegate.LoadSate int loadState)
    {
        if (mLoadMoreDelegate != null)
        {
            mLoadMoreDelegate.setLoadState(loadState);
        }
    }

    @NonNull
    @Override
    public List<Object> getItems()
    {
        return mItems;
    }

    /**
     * reset the items, clear first, then add all.
     *
     * @param items the new items
     * @param <T>   the generic type of the element
     */
    public <T> void setDataSource(List<T> items)
    {
        mItems.clear();
        if (!ArrayUtil.isEmpty(items))
        {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void add(Object item)
    {
        if (item != null)
        {
            int positionStart = getItemCount();
            mItems.add(item);
            notifyItemInserted(positionStart);

            // 因为最后1个item的底部分割线不可见，所以此处还需局部刷新原来的最后一个item
            notifyItemChanged(positionStart - 1, new Object());
        }
    }

    public <T> void addAll(List<T> items)
    {
        if (!ArrayUtil.isEmpty(items))
        {
            int positionStart = getItemCount();
            mItems.addAll(items);
            notifyItemRangeInserted(positionStart, items.size());
        }
    }

    /**
     * 局部刷新
     *
     * @param position 元素索引
     * @param item     新元素
     */
    public void update(int position, Object item)
    {
        if (item != null)
        {
            Object oldValue = ArrayUtil.setSafely(mItems, position, item);
            if (oldValue != null)
            {
                notifyItemChanged(position, item);
            }
        }
    }

    public void remove(int position)
    {
        Object oldValue = ArrayUtil.removeSafely(mItems, position);
        if (oldValue != null)
        {
            notifyItemRemoved(position);
        }
    }

    public void removeAll(Items items)
    {
        if (!ArrayUtil.isEmpty(items))
        {
            mItems.removeAll(items);
            notifyDataSetChanged();
        }
    }

    public void clear()
    {
        mItems.clear();
        notifyDataSetChanged();
    }
}
