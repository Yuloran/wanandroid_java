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
package com.yuloran.wanandroid_java;

import android.app.Application;

import com.yuloran.lib_core.init.ServiceManager;

/**
 * [MyApplication]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/11/22 23:21
 *
 * @since 1.0.0
 */
public class MyApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();

        ServiceManager.getInstance().init(this);
    }

}
