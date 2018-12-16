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

import android.util.Log;

import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * [Log Util]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 16:30
 *
 * @since 1.0.0
 */
public final class Logger
{
    private static final String APP_TAG = "WanAndroid";

    private static final boolean DEBUG = true;

    private Logger()
    {
    }

    public static void debug(@NonNull String tag, @NonNull String msg)
    {
        if (DEBUG)
        {
            Log.d(APP_TAG, wrap(tag, msg));
        }
    }

    public static void debug(@NonNull String tag, @NonNull String formatMsg, Object... args)
    {
        if (DEBUG)
        {
            Log.d(APP_TAG, wrap(tag, formatMsg, args));
        }
    }

    public static void info(@NonNull String tag, @NonNull String msg)
    {
        if (DEBUG)
        {
            Log.i(APP_TAG, wrap(tag, msg));
        }
    }

    public static void info(@NonNull String tag, @NonNull String formatMsg, Object... args)
    {
        if (DEBUG)
        {
            Log.i(APP_TAG, wrap(tag, formatMsg, args));
        }
    }

    public static void warn(@NonNull String tag, @NonNull String msg)
    {
        Log.w(APP_TAG, wrap(tag, msg));
    }

    public static void warn(@NonNull String tag, @NonNull String formatMsg, Object... args)
    {
        Log.w(APP_TAG, wrap(tag, formatMsg, args));
    }

    public static void warn(@NonNull String tag, @NonNull String msg, Throwable t)
    {
        Log.w(APP_TAG, wrap(tag, msg), t);
    }

    public static void warn(@NonNull String tag, Throwable t, @NonNull String formatMsg, Object... args)
    {
        Log.w(APP_TAG, wrap(tag, formatMsg, args), t);
    }

    public static void error(@NonNull String tag, @NonNull String msg)
    {
        Log.e(APP_TAG, wrap(tag, msg));
    }

    public static void error(@NonNull String tag, @NonNull String formatMsg, Object... args)
    {
        Log.e(APP_TAG, wrap(tag, formatMsg, args));
    }

    public static void error(@NonNull String tag, @NonNull String msg, Throwable t)
    {
        Log.e(APP_TAG, wrap(tag, msg), t);
    }

    public static void error(@NonNull String tag, Throwable t, @NonNull String formatMsg, Object... args)
    {
        Log.e(APP_TAG, wrap(tag, formatMsg, args), t);
    }

    private static String wrap(@NonNull String tag, @NonNull String msg)
    {
        return tag + ": " + msg;
    }

    private static String wrap(@NonNull String tag, @NonNull String msg, Object... args)
    {
        return tag + ": " + String.format(Locale.US, msg, args);
    }
}
