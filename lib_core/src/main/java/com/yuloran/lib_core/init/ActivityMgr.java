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
package com.yuloran.lib_core.init;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.template.Singleton;

import io.reactivex.annotations.NonNull;

/**
 * [Activity管理器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 19:02
 *
 * @since 1.0.0
 */
public final class ActivityMgr implements IInit, Application.ActivityLifecycleCallbacks
{
    private static final String TAG = "ActivityMgr";

    private static final Singleton<ActivityMgr> INSTANCE = new Singleton<ActivityMgr>()
    {
        @Override
        protected ActivityMgr create()
        {
            return new ActivityMgr();
        }
    };

    private Activity mActiveActivity;

    private ActivityMgr()
    {
    }

    /**
     * 是否可以懒初始化
     *
     * @return true：可以懒初始化
     */
    @Override
    public boolean lazyInit()
    {
        return false;
    }

    /**
     * 初始化
     *
     * @param application application
     */
    @Override
    public void init(@NonNull Application application)
    {
        application.registerActivityLifecycleCallbacks(this);
    }

    public static ActivityMgr getInstance()
    {
        return INSTANCE.get();
    }

    public Activity getActiveActivity()
    {
        return mActiveActivity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        Logger.info(TAG, "%s created.", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity)
    {
        Logger.info(TAG, "%s started.", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        Logger.info(TAG, "%s resumed.", activity.getClass().getSimpleName());
        mActiveActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity)
    {
        Logger.info(TAG, "%s paused.", activity.getClass().getSimpleName());
        mActiveActivity = null;
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
        Logger.info(TAG, "%s stopped.", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {
        Logger.info(TAG, "%s created.", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        Logger.info(TAG, "%s destroyed.", activity.getClass().getSimpleName());
    }
}
