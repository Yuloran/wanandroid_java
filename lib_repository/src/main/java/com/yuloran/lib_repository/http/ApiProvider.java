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
import com.yuloran.lib_core.utils.Singleton;

import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * [ApiProvider]
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

    @NonNull
    private Apis.IWanAndroidApi mWanAndroidApi;

    private ApiProvider()
    {
        int size = 200 * 1024 * 1024; // 200M
        Cache cache = new Cache(EnvService.getInstance().getCacheDir(), size);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().cache(cache)
                                                        .addInterceptor(logging)
                                                        .readTimeout(10, TimeUnit.SECONDS)
                                                        .writeTimeout(10, TimeUnit.SECONDS)
                                                        .build();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Apis.IWanAndroidApi.BASE_URL)
                                                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                                  .addConverterFactory(GsonConverterFactory.create(gson))
                                                  .client(client)
                                                  .build();

        mWanAndroidApi = retrofit.create(Apis.IWanAndroidApi.class);
    }

    public static ApiProvider getInstance()
    {
        return INSTANCE.get();
    }

    @NonNull
    public Apis.IWanAndroidApi getWanAndroidApi()
    {
        return mWanAndroidApi;
    }
}
