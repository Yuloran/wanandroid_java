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
package com.yuloran.lib_core.utils;

import org.reactivestreams.Publisher;

import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * [RxJava工具类]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/15 10:40
 *
 * @since 1.0.0
 */
public final class RxJavaUtil
{
    private static final String TAG = "RxJavaUtil";

    private RxJavaUtil()
    {
    }

    /**
     * 在IO线程池中执行upstream，在UI线程中监听
     *
     * @param upstream 上游
     * @param <T>      数据类型
     * @return 变换后的 {@code Flowable<T>}
     */
    public static <T> Flowable<T> doOnIOSubscribeOnUI(@NonNull Flowable<T> upstream)
    {
        Objects.requireNonNull(upstream);
        return upstream.compose(new FlowableTransformer<T, T>()
        {
            @Override
            public Publisher<T> apply(Flowable<T> upstream)
            {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        });
    }
}
