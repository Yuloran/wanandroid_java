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

import android.app.Application;

import com.yuloran.lib_core.template.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

/**
 * [服务中心]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 15:57
 *
 * @since 1.0.0
 */
public final class ServiceManager implements IInit
{
    private static final String TAG = "ServiceManager";

    private static final Singleton<ServiceManager> INSTANCE = new Singleton<ServiceManager>()
    {
        @Override
        protected ServiceManager create()
        {
            return new ServiceManager();
        }
    };

    private List<IInit> mServices = new ArrayList<>();

    {
        mServices.add(EnvService.getInstance());
        mServices.add(ActivityMgr.getInstance());
    }

    private ServiceManager()
    {
    }

    public static ServiceManager getInstance()
    {
        return INSTANCE.get();
    }

    @Override
    public boolean lazyInit()
    {
        return false;
    }

    @MainThread
    @Override
    public void init(@NonNull final Application application)
    {
        Objects.requireNonNull(application);

        for (final IInit init : mServices)
        {
            if (init.lazyInit())
            {
                SingleWorker.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        init.init(application);
                    }
                });
            } else
            {
                init.init(application);
            }
        }
    }

}
