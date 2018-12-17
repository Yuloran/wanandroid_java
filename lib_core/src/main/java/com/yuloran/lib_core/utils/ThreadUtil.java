package com.yuloran.lib_core.utils;

import android.os.Looper;

/**
 * [Description]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/17 12:51
 *
 * @since 1.0.0
 */
public final class ThreadUtil
{
    private ThreadUtil()
    {
    }

    public static boolean isMainThread()
    {
        return Looper.getMainLooper().isCurrentThread();
    }

    public static void assertMainThread(String methodName)
    {
        if (!isMainThread())
        {
            throw new IllegalStateException("Cannot invoke " + methodName + " on a background" + " thread");
        }
    }
}
