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

import com.yuloran.lib_core.bean.ViewState;
import com.yuloran.lib_core.bean.backend.response.Item;
import com.yuloran.lib_core.constant.Cons;
import com.yuloran.lib_core.init.NetworkService;
import com.yuloran.lib_core.init.RouterService;
import com.yuloran.lib_core.router.BaseModule;
import com.yuloran.lib_core.utils.ArrayUtil;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.lib_repository.viewdata.ArticlesViewData;
import com.yuloran.module_base.ui.adapter.recyclerview.MultiTypeAdapterEx;
import com.yuloran.module_base.ui.adapter.recyclerview.OnItemClickListener;
import com.yuloran.module_base.ui.adapter.recyclerview.loadmore.LoadMoreDelegate;
import com.yuloran.module_base.ui.adapter.recyclerview.loadmore.LoadMoreItem;
import com.yuloran.module_base.ui.adapter.recyclerview.loadmore.LoadMoreItemViewBinder;
import com.yuloran.module_base.ui.base.BaseRecyclerViewFragment;
import com.yuloran.wanandroid_java.viewmodel.ArticlesVM;

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
public class ArticlesFragment extends BaseRecyclerViewFragment
        implements LoadMoreDelegate.OnLoadMoreListener, OnItemClickListener<Item>
{
    private static final String TAG = "ArticlesFragment";

    private OfficialAccount mOfficialAccount;

    private ArticlesVM mVM;

    private MultiTypeAdapterEx mMultiTypeAdapter;

    /** Fragment整体的加载状态 */
    private ViewState mViewState = ViewState.UNINITIALIZED;

    @Override
    protected String logTag()
    {
        return TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            mOfficialAccount = bundle.getParcelable("official_account");
        }

        mVM = ViewModelProviders.of(this).get(ArticlesVM.class);
        mVM.getArticles().observe(this, new Observer<ArticlesViewData>()
        {
            @Override
            public void onChanged(ArticlesViewData viewData)
            {
                if (viewData == null)
                {
                    Logger.warn(TAG, "onChanged: viewData is null!");
                    return;
                }

                // 因为这个页面是分页加载的，所以只要第一页加载成功后，就无需再刷新viewState
                ViewState latest = viewData.getViewState();

                if (!mViewState.isSuccessful())
                {
                    mViewState = latest;
                }

                switch (latest.getState())
                {
                    case Cons.STATE_UNINITIALIZED:
                        initAdapter();
                        break;
                    case Cons.STATE_LOADING:
                        onLoading(latest);
                        break;
                    case Cons.STATE_LOAD_SUCCESS:
                        onLoadSuccess(viewData);
                        break;
                    case Cons.STATE_LOAD_FAILURE:
                        onLoadFailure(latest);
                        break;
                    default:
                }
            }
        });

        mVM.getNavigation().observe(this, new Observer<Item>()
        {
            @Override
            public void onChanged(Item item)
            {
                if (item != null)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString(Cons.KEY_TITLE, item.getTitle());
                    bundle.putString(Cons.KEY_URL, item.getLink());
                    RouterService.getInstance().navigate(BaseModule.Activity.HTML_ACTIVITY, bundle);
                }
            }
        });

        NetworkService.getInstance().getNetworkInfoLiveData().observe(this, new Observer<NetworkService.NetworkInfo>()
        {
            @Override
            public void onChanged(NetworkService.NetworkInfo networkInfo)
            {
                if (networkInfo.isConnected())
                {
                    if (mViewState.isFailed())
                    {
                        Logger.info(TAG, "network resumed, reload.");
                        mVM.fetch(mOfficialAccount, ArticlesFragment.this);
                    }
                }
            }
        });
    }

    @Override
    protected void onRecyclerViewCreated()
    {
        if (mMultiTypeAdapter != null)
        {
            mMultiTypeAdapter.setOnLoadMoreListener(this);
            mRecyclerView.setAdapter(mMultiTypeAdapter);
        }
    }

    private void initAdapter()
    {
        Logger.info(TAG, "onChanged: init multiAdapter.");
        mMultiTypeAdapter = new MultiTypeAdapterEx();
        mMultiTypeAdapter.register(Item.class, new ArticleItemViewBinder(this));
        mMultiTypeAdapter.register(LoadMoreItem.class, new LoadMoreItemViewBinder());
        mMultiTypeAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        Logger.info(TAG, "onChanged: fetch data.");
        mVM.fetch(mOfficialAccount, ArticlesFragment.this);
    }

    private void onLoading(ViewState latest)
    {
        boolean page1Loaded = mViewState.isSuccessful();
        Logger.info(TAG, "onChanged: onLoading(is page1 loaded? %b)...", page1Loaded);
        if (!page1Loaded)
        {
            setViewState(latest);
        }
    }

    private void onLoadSuccess(ArticlesViewData viewData)
    {
        Logger.info(TAG, "obChanged: load success, %d articles.", ArrayUtil.sizeof(viewData.getViewData()));
        setViewState(viewData.getViewState());
        mMultiTypeAdapter.setLoadState(viewData.isOver() ? LoadMoreDelegate.LOAD_OVER : LoadMoreDelegate.LOAD_COMPLETE);
        mMultiTypeAdapter.addAll(viewData.getViewData());
    }

    private void onLoadFailure(ViewState latest)
    {
        boolean page1Loaded = mViewState.isSuccessful();
        Logger.info(TAG, "onChanged: load failure(is page1 loaded? %b, %d/%s).", page1Loaded, latest.getErrCode(),
                latest
                .getErrMsg());
        if (!page1Loaded)
        {
            setViewState(latest);
        }
    }

    @Override
    public void onLoadMore()
    {
        mVM.fetch(mOfficialAccount, ArticlesFragment.this);
    }

    @Override
    public void onItemClick(Item item)
    {
        mVM.navigate(item);
    }
}
