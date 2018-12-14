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

import java.util.Collection;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * [数组、集合工具类：不会产生NullPointerException、IndexOutOfBoundsException]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 0:01
 *
 * @since 1.0.0
 */
public final class ArrayUtil
{

    private ArrayUtil()
    {
    }

    public static boolean isEmpty(Collection<?> collection)
    {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isEmpty(T[] array)
    {
        return array == null || array.length == 0;
    }

    public static int sizeof(Collection<?> collection)
    {
        return collection == null ? 0 : collection.size();
    }

    public static <T> int sizeof(T[] array)
    {
        return array == null ? 0 : array.length;
    }

    @Nullable
    public static <T> T getSafely(List<T> list, int index)
    {
        return index >= 0 && index < sizeof(list) ? list.get(index) : null;
    }

    @Nullable
    public static <T> T getSafely(T[] array, int index)
    {
        return index >= 0 && index < sizeof(array) ? array[index] : null;
    }
}
