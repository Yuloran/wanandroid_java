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
package com.yuloran.lib_core.init;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import com.yuloran.lib_core.template.Singleton;
import com.yuloran.lib_core.template.threadsafe.SafeMutableLiveData;
import com.yuloran.lib_core.utils.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * [网络状态管理器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 22:54
 *
 * @since 1.0.0
 */
public final class NetworkService implements IInit
{
    private static final String TAG = "NetworkService";

    private static final Singleton<NetworkService> INSTANCE = new Singleton<NetworkService>()
    {
        @Override
        protected NetworkService create()
        {
            return new NetworkService();
        }
    };

    private static final NetworkInfo DISCONNECTED = new NetworkInfo(false, NetworkType.UNKNOWN);

    private ConnectivityManager mConnectivityManager;

    private SafeMutableLiveData<NetworkInfo> mNetworkInfoLiveData = new SafeMutableLiveData<>();

    private NetworkInfo mLatestNetworkInfo;

    private NetworkService()
    {
        // 根据源码，当liveData的mData为初始值时，observer.mLastVersion == mVersion，所以此时添加observer，不会触发回调
        mNetworkInfoLiveData.observeForever(new Observer<NetworkInfo>()
        {
            @Override
            public void onChanged(NetworkInfo networkInfo)
            {
                mLatestNetworkInfo = networkInfo;
            }
        });
    }

    public static NetworkService getInstance()
    {
        return INSTANCE.get();
    }

    @Override
    public boolean canInitInBackground()
    {
        return true;
    }

    @Override
    public void init(Application application)
    {
        mConnectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);

        // 初始化连接信息
        parseAndSet(mConnectivityManager.getActiveNetwork());

        // 监听连接状态
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
               .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
               .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
               .addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        mConnectivityManager.registerNetworkCallback(builder.build(), new ConnectivityManager.NetworkCallback()
        {
            @Override
            public void onAvailable(Network network)
            {
                // 网络已连接
                Logger.info(TAG, "onAvailable.");
                parseAndSet(network);
            }

            @Override
            public void onLost(Network network)
            {
                // 某个网络断开连接，不一定所有网络都断开连接。比如mobile断开连接，但wifi已连接
                Logger.info(TAG, "onLost.");
                parseAndSet(mConnectivityManager.getActiveNetwork());
            }
        });
    }

    private void parseAndSet(Network network)
    {
        NetworkCapabilities networkCapabilities = mConnectivityManager.getNetworkCapabilities(network);
        if (networkCapabilities == null)
        {
            NetworkInfo latest = DISCONNECTED;
            NetworkInfo previous = mNetworkInfoLiveData.getValue(DISCONNECTED);
            latest.setConnectivityAction(previous.isConnected ? ConnectivityAction.CONNECTED_TO_DISCONNECTED :
                    ConnectivityAction.CONNECTIVITY_NOT_CHANGE);
            mNetworkInfoLiveData.setValue(latest);
            Logger.info(TAG, "parseAndSet: no capabilities, " + latest);
            return;
        }

        // case1. 如果仅连接mobile或wifi其中一种：则连接时回调onAvailable()，断开时回调onLost()
        // case2. 如果mobile与wifi同时连接：则mobile断开或连接不会触发任何回调
        // case3. 如果mobile与wifi同时连接：
        //        (1) 断开wifi：wifi.onLost()->mobile.onAvailable().
        //        (2) 连接wifi：wifi.onAvailable()->mobile.onLost().
        boolean hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        boolean hasWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        boolean hasCellular = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        NetworkInfo latest = new NetworkInfo(hasInternet, transformNetworkType(hasWifi, hasCellular));
        NetworkInfo previous = mNetworkInfoLiveData.getValue(DISCONNECTED);
        parseConnectivityAction(previous, latest);
        Logger.info(TAG, "parseAndSet: " + latest);
        mNetworkInfoLiveData.setValue(latest);
    }

    private void parseConnectivityAction(@NonNull NetworkInfo previous, @NonNull NetworkInfo latest)
    {
        if (previous.isConnected == latest.isConnected)
        {
            if (latest.isConnected && previous.networkType != latest.networkType)
            {
                // 此处默认只要是已连接，网络类型就一定为wifi或者mobile(存在错误风险)
                latest.setConnectivityAction(latest.networkType == NetworkType.WIFI ? ConnectivityAction
                        .CONNECTIVITY_MOBILE_TO_WIFI : ConnectivityAction.CONNECTIVITY_WIFI_TO_MOBILE);
            } else
            {
                latest.setConnectivityAction(ConnectivityAction.CONNECTIVITY_NOT_CHANGE);
            }
        } else
        {
            latest.setConnectivityAction(latest.isConnected ? ConnectivityAction.DISCONNECTED_TO_CONNECTED :
                    ConnectivityAction.CONNECTED_TO_DISCONNECTED);
        }
    }

    private NetworkType transformNetworkType(boolean hasWifi, boolean hasCellular)
    {
        if (hasWifi)
        {
            return NetworkType.WIFI;
        }
        if (hasCellular)
        {
            return NetworkType.MOBILE;
        }
        return NetworkType.UNKNOWN;
    }

    @NonNull
    public LiveData<NetworkInfo> getNetworkInfoLiveData()
    {
        return mNetworkInfoLiveData;
    }

    @NonNull
    public NetworkInfo getNetworkInfo()
    {
        return mLatestNetworkInfo == null ? DISCONNECTED : mLatestNetworkInfo;
    }

    /** 网络信息 */
    public static class NetworkInfo
    {
        private boolean isConnected;

        @NonNull
        private NetworkType networkType;

        private ConnectivityAction connectivityAction;

        NetworkInfo(boolean isConnected, @NonNull NetworkType networkType)
        {
            this.isConnected = isConnected;
            this.networkType = networkType;
        }

        public boolean isConnected()
        {
            return isConnected;
        }

        @NonNull
        public NetworkType getNetworkType()
        {
            return networkType;
        }

        @Nullable
        public ConnectivityAction getConnectivityAction()
        {
            return connectivityAction;
        }

        void setConnectivityAction(ConnectivityAction connectivityAction)
        {
            this.connectivityAction = connectivityAction;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            NetworkInfo that = (NetworkInfo) o;

            if (isConnected != that.isConnected)
            {
                return false;
            }
            return networkType == that.networkType;
        }

        @Override
        public int hashCode()
        {
            int result = (isConnected ? 1 : 0);
            result = 31 * result + networkType.hashCode();
            return result;
        }

        @Override
        public String toString()
        {
            return "NetworkInfo{" + "isConnected=" + isConnected + ", networkType=" + networkType.description + ", "
                    + "connectivityAction=" + (connectivityAction == null ? "unknown" : connectivityAction
                    .description) + '}';
        }
    }

    /** 网络类型 */
    public enum NetworkType
    {
        WIFI("wifi"),
        MOBILE("mobile"),
        UNKNOWN("unknown");

        private String description;

        NetworkType(String description)
        {
            this.description = description;
        }

        public String getDescription()
        {
            return description;
        }
    }

    /** 网络连接动作类型 */
    public enum ConnectivityAction
    {
        /**
         * 网络已连接，且成mobile切换成wifi
         */
        CONNECTIVITY_MOBILE_TO_WIFI("changed: mobile to wifi."),
        /**
         * 网络已连接，且成wifi切换成mobile。测试发现：断开wifi时，即使mobile是连接状态，返回的也是无网络，稍候才能返回mobile已连接。
         * 所以，这个状态实际不会出现。
         */
        CONNECTIVITY_WIFI_TO_MOBILE("changed: wifi to mobile."),
        /**
         * 网络连通性没有变化
         */
        CONNECTIVITY_NOT_CHANGE("connectivity not change."),
        DISCONNECTED_TO_CONNECTED("disconnected to connected."),
        CONNECTED_TO_DISCONNECTED("connected to disconnected.");

        private String description;

        ConnectivityAction(String description)
        {
            this.description = description;
        }

        public String getDescription()
        {
            return description;
        }
    }
}
