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
package com.yuloran.lib_repository.http.common;

import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.constant.ErrCode;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import androidx.annotation.Nullable;
import retrofit2.HttpException;

/**
 * [通用服务器接口请求订阅对象，所有订阅对象都应该继承自该类]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/15 11:02
 *
 * @since 1.0.0
 */
public abstract class CommonRequestSubscriber<T> implements Subscriber<T>
{
    private static final String TAG = "CommonRequestSubscriber";

    protected abstract void onSuccess(@androidx.annotation.NonNull T response);

    protected abstract void onError(int errCode, @Nullable String errMsg);

    /**
     * Invoked after calling {@link Publisher#subscribe(Subscriber)}.
     * <p>
     * No data will start flowing until {@link Subscription#request(long)} is invoked.
     * <p>
     * It is the responsibility of this {@link Subscriber} instance to call {@link Subscription#request(long)}
     * whenever more data is wanted.
     * <p>
     * The {@link Publisher} will send notifications only in response to {@link Subscription#request(long)}.
     *
     * @param s {@link Subscription} that allows requesting data via {@link Subscription#request(long)}
     */
    @Override
    public void onSubscribe(Subscription s)
    {
        s.request(1);
    }

    /**
     * Data notification sent by the {@link Publisher} in response to requests to
     * {@link Subscription#request(long)}.
     *
     * @param t the element signaled
     */
    @Override
    public void onNext(T t)
    {
        onSuccess(t);
    }

    /**
     * Failed terminal state.
     * <p>
     * No further events will be sent even if {@link Subscription#request(long)} is invoked again.
     *
     * @param t the throwable signaled
     */
    @Override
    public void onError(Throwable t)
    {
        Logger.error(TAG, t, "Request Error: %s: %s.", t.getClass().getSimpleName(), t.getMessage());

        if (t instanceof SocketTimeoutException)
        {
            onError(ErrCode.SOCKET_TIME_OUT, null);
            return;
        }

        if (t instanceof UnknownHostException)
        {
            onError(ErrCode.UNKNOWN_HOST, null);
            return;
        }

        if (t instanceof HttpException)
        {
            HttpException httpException = (HttpException) t;
            onError(httpException.code(), httpException.message());
            return;
        }

        if (t instanceof CommonRequestException)
        {
            CommonRequestException commonRequestException = (CommonRequestException) t;
            onError(commonRequestException.getErrCode(), commonRequestException.getErrMsg());
            return;
        }

        onError(ErrCode.UNKNOWN_ERROR, null);
    }

    /**
     * Successful terminal state.
     * <p>
     * No further events will be sent even if {@link Subscription#request(long)} is invoked again.
     */
    @Override
    public void onComplete()
    {
    }
}
