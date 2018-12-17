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
package com.yuloran.lib_repository.http.interceptor;

import com.yuloran.lib_core.init.NetworkService;
import com.yuloran.lib_core.utils.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * [缓存控制拦截器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 21:05
 *
 * @since 1.0.0
 */
public class CacheControlInterceptor implements Interceptor
{
    private static final String TAG = "CacheControlInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        // step1. 修改请求头：无网络时，强制使用缓存
        Request request = chain.request();
        if (!NetworkService.getInstance().getNetworkInfo().isConnected())
        {
            Logger.debug(TAG, "Request$intercept: network unavailable, force cache.");
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }

        // step2. 修改响应头：有网时，不使用缓存。无网络时，缓存有效期为7天。
        Response.Builder respBuilder = chain.proceed(request).newBuilder();
        if (NetworkService.getInstance().getNetworkInfo().isConnected())
        {
            Logger.debug(TAG, "Response$intercept: network available, disable cache.");
            respBuilder.header("Cache-Control", "public, max-age=0").removeHeader("Pragma");
        } else
        {
            Logger.debug(TAG, "Response$intercept: network unavailable, stale cache until 7days.");
            respBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + TimeUnit.DAYS.toSeconds(7))
                       .removeHeader("Pragma");
        }
        return respBuilder.build();
    }
}
