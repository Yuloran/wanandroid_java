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
import com.yuloran.lib_core.constant.Cons;
import com.yuloran.lib_core.init.NetworkService;
import com.yuloran.lib_core.utils.ArrayUtil;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_repository.database.OfficialAccount;
import com.yuloran.lib_repository.viewdata.BaseViewData;
import com.yuloran.module_base.ui.base.BaseTabLayoutViewPagerFragment;
import com.yuloran.wanandroid_java.viewmodel.OfficialAccountVM;

import java.util.ArrayList;
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

    private OfficialAccountVM mVM;

    private OfficialAccountPagerAdapter mPagerAdapter;

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

        mVM = ViewModelProviders.of(this).get(OfficialAccountVM.class);
        mVM.getAccounts().observe(this, new Observer<BaseViewData<List<OfficialAccount>>>()
        {
            @Override
            public void onChanged(BaseViewData<List<OfficialAccount>> viewData)
            {
                if (viewData == null)
                {
                    Logger.warn(TAG, "viewData is null!");
                    return;
                }

                mViewState = viewData.getViewState();
                switch (mViewState.getState())
                {
                    case Cons.STATE_UNINITIALIZED:
                        onInit();
                        break;
                    case Cons.STATE_LOADING:
                        onLoading(mViewState);
                        break;
                    case Cons.STATE_LOAD_SUCCESS:
                        onLoadSuccess(mViewState, viewData.getViewData());
                        break;
                    case Cons.STATE_LOAD_FAILURE:
                        onLoadFailure(mViewState);
                        break;
                    default:
                }
            }
        });

        NetworkService.getInstance().getNetworkInfoLiveData().observe(this, new Observer<NetworkService.NetworkInfo>()
        {
            @Override
            public void onChanged(NetworkService.NetworkInfo networkInfo)
            {
                // TODO Bug: Fragment生命周期改变时会回调LiveData的onStateChanged(), 导致发射上一次的值, 目前无法修复
                if (networkInfo.isConnected())
                {
                    if (mViewState.isFailed())
                    {
                        Logger.info(TAG, "network resumed, reload.");
                        mVM.fetch(OfficialAccountFragment.this);
                    }
                }
            }
        });
    }

    @Override
    public void onTabLayoutViewPagerCreated()
    {
        if (mPagerAdapter == null)
        {
            Logger.info(TAG, "onTabLayoutViewPagerCreated: init pagerAdapter.");
            mPagerAdapter = new OfficialAccountPagerAdapter(getChildFragmentManager(), new ArrayList<OfficialAccount>
                    ());
            mViewPager.setAdapter(mPagerAdapter);
            return;
        }

        Logger.info(TAG, "onTabLayoutViewPagerCreated: rebind pagerAdapter.");
        // FragmentPagerAdapter：Fragment超出缓存范围后，View会被销毁，但是Fragment只是调用onStop。
        // 再次跳至该Fragment时，需要重新绑定adapter。
        if (mViewState != null)
        {
            setViewState(mViewState);
        }
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void onInit()
    {
        // 删除初始化adapter逻辑，不能依赖LiveData的回调来初始化。因为当设备配置改变时，Activity如果没有在manifest中配置，那么
        // Activity的生命周期如下：paused->stateSaved->stopped->destroyed->created->started->resumed. destroy时，LiveData
        // 会自动移除之前的监听。我们在onCreate()重新加了一个监听，当进入started状态后，LiveData就会重新发射上次的值(因为observer
        // 内部的版本号与value的版本号不一致)，这个值的viewState不确定，因为之前没有初始化adapter，所以可能导致NPE。
        Logger.info(TAG, "onChanged: fetch data.");
        mVM.fetch(OfficialAccountFragment.this);
    }

    private void onLoading(ViewState viewState)
    {
        Logger.info(TAG, "onChanged: loading...");
        setViewState(viewState);
    }

    private void onLoadSuccess(ViewState viewState, List<OfficialAccount> accounts)
    {
        Logger.info(TAG, "onChanged: load success, %d accounts.", ArrayUtil.sizeof(accounts));
        setViewState(viewState);
        mPagerAdapter.setDataSource(ArrayUtil.nonNull(accounts));
        mPagerAdapter.notifyDataSetChanged();
    }

    private void onLoadFailure(ViewState viewState)
    {
        Logger.info(TAG, "onChanged: load failure(%d/%s).", viewState.getErrCode(), viewState.getErrMsg());
        setViewState(viewState);
    }
}
