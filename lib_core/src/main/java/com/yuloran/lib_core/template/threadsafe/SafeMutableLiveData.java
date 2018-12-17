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
package com.yuloran.lib_core.template.threadsafe;

import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.utils.ThreadUtil;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * [1.setValue()线程安全 2.仅在value更新时回调onChange]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/17 11:46
 *
 * @since 1.0.0
 */
public final class SafeMutableLiveData<T> extends LiveData<T>
{
    private static final String TAG = "SafeMutableLiveData";

    /**
     * Sets the value. If there are active observers, the value will be dispatched to them.
     * <p>
     * This method can be called from any thread. And only the updated value will be dispatched.
     *
     * @param value The new value
     */
    @Override
    public void setValue(T value)
    {
        if (Objects.equals(value, getValue()))
        {
            Logger.debug(TAG, "setValue: values equals, skip.");
            return;
        }

        if (ThreadUtil.isMainThread())
        {
            super.setValue(value);
        } else
        {
            postValue(value);
        }
    }

    /**
     * Returns the current value.
     * Note that calling this method on a background thread does not guarantee that the latest
     * value set will be received.
     *
     * @param defaultValue if the current value is null
     * @return returns the default value if the current value is null
     */
    @NonNull
    public T getValue(@NonNull T defaultValue)
    {
        Objects.requireNonNull(defaultValue, "defaultValue is null!");
        T value = super.getValue();
        return value == null ? defaultValue : value;
    }
}
