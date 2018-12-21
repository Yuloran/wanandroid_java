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
import android.content.Context;

import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.template.Singleton;

import java.io.File;
import java.util.Objects;

import androidx.annotation.MainThread;
import io.reactivex.annotations.NonNull;

/**
 * [App环境变量]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 15:29
 *
 * @since 1.0.0
 */
public final class EnvService implements IInit
{
    private static final String TAG = "EnvService";

    private static final Singleton<EnvService> INSTANCE = new Singleton<EnvService>()
    {
        @Override
        protected EnvService create()
        {
            return new EnvService();
        }
    };

    private Context mApplicationContext;

    private EnvService()
    {
    }

    public static EnvService getInstance()
    {
        return INSTANCE.get();
    }

    @MainThread
    public void init(@NonNull Application application)
    {
        Objects.requireNonNull(application);
        mApplicationContext = application.getApplicationContext();
    }

    @Override
    public boolean canInitInBackground()
    {
        return false;
    }

    public Context getApplicationContext()
    {
        return mApplicationContext;
    }

    /**
     * 获取应用文件存储路径
     *
     * @return /data/user/0/com.yuloran.wanandroid_java/files
     */
    public File getFilesDir()
    {
        File filesDir = mApplicationContext.getFilesDir();
        Logger.debug(TAG, "getFilesDir: " + filesDir);
        return filesDir;
    }

    /**
     * 获取okHttp缓存路径
     *
     * @return /data/user/0/com.yuloran.wanandroid_java/files/cache
     */
    public File getCacheDir()
    {
        File cacheDir = new File(mApplicationContext.getFilesDir(), "/cache");
        if (!cacheDir.exists())
        {
            boolean success = cacheDir.mkdir();
            Logger.debug(TAG, success ? "make cache dir success!" : "make cache dir failed!");
        }
        return cacheDir;
    }
}
