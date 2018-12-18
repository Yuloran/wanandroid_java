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

import com.yuloran.lib_core.utils.Logger;
import com.yuloran.module_base.ui.adapter.recyclerview.MultiTypeAdapterEx;

import java.util.Objects;

import androidx.annotation.AnyThread;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * [上滑加载实现类]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/18 16:28
 *
 * @since 1.0.0
 */
public final class LoadMoreDelegate
{
    @IntDef({LOAD_COMPLETE, LOAD_OVER})
    public @interface LoadSate
    {
    }

    private static final String TAG = "LoadMoreDelegate";

    private static final int IDLE = -1;
    private static final int LOADING = 1;
    public static final int LOAD_COMPLETE = 2;
    public static final int LOAD_OVER = 3;

    @NonNull
    private MultiTypeAdapterEx mAdapter;

    private OnLoadMoreListener mOnLoadMoreListener;

    private volatile int mLoadState = IDLE;

    public LoadMoreDelegate(@NonNull RecyclerView recyclerView, @NonNull MultiTypeAdapterEx adapter)
    {
        Objects.requireNonNull(recyclerView, "recyclerView is null!");
        Objects.requireNonNull(adapter, "adapter is null!");

        mAdapter = adapter;
        recyclerView.addOnScrollListener(new OnRecyclerViewScrollListener());
    }

    public void setOnLoadMoreListener(@Nullable OnLoadMoreListener onLoadMoreListener)
    {
        Objects.requireNonNull(onLoadMoreListener, "onLoadMoreListener is null!");
        mOnLoadMoreListener = onLoadMoreListener;
    }

    @AnyThread
    public void setLoadState(@LoadSate int loadState)
    {
        Logger.debug(TAG, "setLoadState.");
        if (mLoadState == LOADING)
        {
            mAdapter.remove(mAdapter.getItemCount() - 1);
            mLoadState = loadState;
        }
    }

    private class OnRecyclerViewScrollListener extends RecyclerView.OnScrollListener
    {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
        {
            if (mOnLoadMoreListener == null)
            {
                return;
            }

            // 下滑
            if (dy <= 0)
            {
                return;
            }

            if (mLoadState == LOADING || mLoadState == LOAD_OVER)
            {
                return;
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager)
            {
                LinearLayoutManager l = (LinearLayoutManager) layoutManager;
                int lastVisibleItemPosition = l.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == layoutManager.getItemCount() - 1)
                {
                    Logger.info(TAG, "onLoadMore.");
                    mLoadState = LOADING;
                    LoadMoreItem item = new LoadMoreItem();
                    mAdapter.add(item);
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        }
    }

    public interface OnLoadMoreListener
    {
        void onLoadMore();
    }
}
