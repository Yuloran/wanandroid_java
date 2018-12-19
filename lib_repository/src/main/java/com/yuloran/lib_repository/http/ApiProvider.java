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
package com.yuloran.lib_repository.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuloran.lib_core.init.EnvService;
import com.yuloran.lib_core.template.Singleton;
import com.yuloran.lib_repository.http.interceptor.CacheControlInterceptor;
import com.yuloran.lib_repository.http.interceptor.RequestHeadersInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * [创建OkHttpClient，对外提供服务器访问接口]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 16:58
 *
 * @since 1.0.0
 */
public class ApiProvider
{
    private static final String TAG = "ApiProvider";

    private static final Singleton<ApiProvider> INSTANCE = new Singleton<ApiProvider>()
    {
        @Override
        protected ApiProvider create()
        {
            return new ApiProvider();
        }
    };

    private Apis.IWanAndroidApi mWanAndroidApi;

    private ApiProvider()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Apis.IWanAndroidApi.BASE_URL)
                                                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                                  .addConverterFactory(GsonConverterFactory.create(initGson()))
                                                  .client(initOkHttpClient())
                                                  .build();
        mWanAndroidApi = retrofit.create(Apis.IWanAndroidApi.class);
    }

    public static ApiProvider getInstance()
    {
        return INSTANCE.get();
    }

    public Apis.IWanAndroidApi getWanAndroidApi()
    {
        return mWanAndroidApi;
    }

    private Gson initGson()
    {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    private OkHttpClient initOkHttpClient()
    {
        // 缓存大小 100M
        int size = 100 * 1024 * 1024;
        Cache cache = new Cache(EnvService.getInstance().getCacheDir(), size);

        // 请求头拦截器
        RequestHeadersInterceptor headersInterceptor = new RequestHeadersInterceptor();

        // 日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        // 缓存控制拦截器
        CacheControlInterceptor cacheControlInterceptor = new CacheControlInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache)
               .addInterceptor(headersInterceptor)
               .addInterceptor(loggingInterceptor) // 日志拦截器在其他普通拦截器之后设置，否则无法打印所有请求头
               .addNetworkInterceptor(cacheControlInterceptor)
               .retryOnConnectionFailure(true)
               .connectTimeout(10, TimeUnit.SECONDS)
               .readTimeout(15, TimeUnit.SECONDS)
               .writeTimeout(15, TimeUnit.SECONDS);
        return builder.build();
    }
}
