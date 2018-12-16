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
package com.yuloran.module_base.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.yuloran.lib_core.init.EnvService;
import com.yuloran.lib_core.utils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import androidx.fragment.app.Fragment;

/**
 * [资源管理器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 18:00
 *
 * @since 1.0.0
 */
public final class ResUtil
{
    private static final String TAG = "ResUtil";

    private static Resources sResources = EnvService.getInstance().getApplicationContext().getResources();

    private static final SimpleArrayMap<String, Class<?>> sClassMap = new SimpleArrayMap<String, Class<?>>();

    private ResUtil()
    {
    }

    @NonNull
    public static List<String> getStringArray(int id)
    {
        if (id <= 0)
        {
            return Collections.emptyList();
        }

        try
        {
            String[] stringArray = sResources.getStringArray(id);
            // 注意：int、char等基本类型不能直接使用asList()转换
            return Arrays.asList(stringArray);
        } catch (Resources.NotFoundException e)
        {
            Logger.error(TAG, "getStringArray: not found with id " + id);
        }
        return Collections.emptyList();
    }

    @NonNull
    public static String getString(int id)
    {
        if (id <= 0)
        {
            return "";
        }

        try
        {
            return sResources.getString(id);
        } catch (Resources.NotFoundException e)
        {
            Logger.error(TAG, "getString: not found with id " + id);
        }
        return "";
    }

    /**
     * 根据Fragment的名字反射创建Fragment，因此该Fragment必须有一个空构造器
     *
     * @param fullFragmentName The class name of the fragment to instantiate.
     * @param arguments        Bundle of arguments to supply to the fragment, which it
     *                         can retrieve with {@link Fragment#getArguments()}.  May be null.
     * @return Returns a new fragment instance.
     * @throws Fragment.InstantiationException If there is a failure in instantiating
     *                                         the given fragment class.  This is a runtime exception; it is not
     *                                         normally expected to happen.
     */
    public static Fragment instantiateFragment(String fullFragmentName, @Nullable Bundle arguments)
    {
        try
        {
            Context context = EnvService.getInstance().getApplicationContext();
            Class<?> clazz = sClassMap.get(fullFragmentName);
            if (clazz == null)
            {
                // Class not found in the cache, see if it's real, and try to add it
                clazz = context.getClassLoader().loadClass(fullFragmentName);
                sClassMap.put(fullFragmentName, clazz);
            }
            Fragment f = (Fragment) clazz.getConstructor().newInstance();
            if (arguments != null)
            {
                arguments.setClassLoader(f.getClass().getClassLoader());
                f.setArguments(arguments);
            }
            return f;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + fullFragmentName + ": make " +
                    "sure class name exists, is public, and has an empty constructor that is public", e);
        } catch (NoSuchMethodException e)
        {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + fullFragmentName + ": could" +
                    " not find Fragment constructor", e);
        } catch (InvocationTargetException e)
        {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + fullFragmentName + ": " +
                    "calling Fragment" + " constructor caused an exception", e);
        }
    }
}
