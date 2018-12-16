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
package com.yuloran.lib_core.template;

/**
 * [带一个参数的单例模板类]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 12:13
 *
 * @since 1.0.0
 */
public abstract class Singleton1<T, P>
{
    private T mInstance;

    protected abstract T create(P arg);

    public final T get(P arg)
    {
        synchronized (this)
        {
            if (mInstance == null)
            {
                mInstance = create(arg);
            }
            return mInstance;
        }
    }
}