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

import android.webkit.WebSettings;

import com.yuloran.lib_core.init.EnvService;
import com.yuloran.lib_core.init.NetworkService;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.utils.StringUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * [添加公共请求头]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 16:08
 *
 * @since 1.0.0
 */
public class RequestHeadersInterceptor implements Interceptor
{
    private static final String TAG = "RequestHeadersInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Logger.debug(TAG, "RequestHeadersInterceptor.");
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.header("Content-Type", "application/json;charset=UTF-8")
               .header("Accept-Charset", "UTF-8")
               .header("User-Agent", getUserAgent());
        if (!NetworkService.getInstance().getNetworkInfo().isConnected())
        {
            // 无网络时，强制使用缓存
            Logger.debug(TAG, "network unavailable, force cache.");
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(builder.build());
    }

    private String getUserAgent()
    {
        String userAgent;
        try
        {
            userAgent = WebSettings.getDefaultUserAgent(EnvService.getInstance().getApplicationContext());
        } catch (Exception e)
        {
            userAgent = System.getProperty("http.agent");
        }

        if (StringUtil.isEmpty(userAgent))
        {
            return "Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, " +
                    "like Gecko) Version/4.0 Chrome/70.0.3538.110 Mobile Safari/537.36";
        }

        // 中文转码
        StringBuilder sb = new StringBuilder();
        for (int i = 0, length = userAgent.length(); i < length; i++)
        {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f')
            {
                sb.append(String.format("\\u%04x", (int) c));
            } else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
