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

import com.alibaba.android.arouter.launcher.ARouter;
import com.yuloran.lib_core.BuildConfig;
import com.yuloran.lib_core.constant.Cons;
import com.yuloran.lib_core.template.Singleton;
import com.yuloran.lib_core.utils.Logger;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * [路由服务]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/20 17:32
 *
 * @since 1.0.0
 */
public final class RouterService implements IInit
{
    private static final String TAG = "RouterService";

    private static final Singleton<RouterService> INSTANCE = new Singleton<RouterService>()
    {
        @Override
        protected RouterService create()
        {
            return new RouterService();
        }
    };

    private RouterService()
    {
    }

    public static RouterService getInstance()
    {
        return INSTANCE.get();
    }

    @Override
    public boolean lazyInit()
    {
        return false;
    }

    @Override
    public void init(Application application)
    {
        if (BuildConfig.DEBUG)
        {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
    }

    public void navigate(@NonNull String path, @Nullable Bundle bundle)
    {
        Objects.requireNonNull(path, "path is null!");

        Activity activeActivity = ActivityMgrService.getInstance().getActiveActivity();
        if (activeActivity == null)
        {
            Logger.warn(TAG, "navigate: activeActivity is null!");
            return;
        }

        ARouter.getInstance().build(path).withBundle(Cons.KEY_BUNDLE, bundle).navigation(activeActivity);
    }
}
