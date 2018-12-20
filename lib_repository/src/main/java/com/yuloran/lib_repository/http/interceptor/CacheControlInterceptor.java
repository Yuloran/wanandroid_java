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

import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.utils.StringUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * [缓存控制拦截器]<br />
 * <strong>注：</strong>
 * <ol>
 * <li>网上博客错误用法太多，全是复制粘贴坑人的！
 * <li><a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control">Http Cache-Control 详解</a>
 * <li><a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Caching_FAQ">Http 缓存详解</a>
 * <li>本例为正确用法，详见 <a href="https://www.jianshu.com/p/d3e67d57f287">你真的了解 OkHttp 缓存控制吗？</a>
 * </ol>
 * <p>
 * <strong>public vs private</strong>
 * <ul>
 * <li>public: Indicates that the response may be cached by any cache, even if the response would normally be
 * non-cacheable (e.g. if the response does not contain a max-age directive or the Expires header).
 * <li>private: Indicates that the response is intended for a single user and must not be stored by a shared cache. A
 * private cache may store the response.
 * </ul>
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
        Logger.debug(TAG, "CacheControlInterceptor.");
        Response response = chain.proceed(chain.request());
        String cacheControl = response.header("Cache-Control");
        if (StringUtil.isEmpty(cacheControl))
        {
            Logger.debug(TAG, "'Cache-Control' not set by the backend, add it ourselves.");
            // assume response is fresh within 1min, this is optional
            return response.newBuilder().removeHeader("Pragma").header("Cache-Control", "public, max-age=60").build();
        }
        return response;
    }
}
