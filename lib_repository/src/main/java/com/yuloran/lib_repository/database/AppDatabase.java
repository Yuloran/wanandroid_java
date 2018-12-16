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
package com.yuloran.lib_repository.database;

import android.content.Context;

import com.yuloran.lib_core.init.EnvService;
import com.yuloran.lib_core.template.Singleton1;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * [数据库创建器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 11:54
 *
 * @since 1.0.0
 */
@Database(entities = {OfficialAccount.class}, version = DBVersion.VERSION_100000, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    private static final String TAG = "AppDatabase";

    private static final String DB_NAME = "app_database.db";

    private static final Singleton1<AppDatabase, Context> INSTANCE = new Singleton1<AppDatabase, Context>()
    {
        @Override
        protected AppDatabase create(Context context)
        {
            // 内部通过反射调用AppDatabase的空构造器创建，所以不能将AppDatabase构造器私有化
            return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
        }
    };

    public static AppDatabase getInstance()
    {
        return INSTANCE.get(EnvService.getInstance().getApplicationContext());
    }

    public abstract OfficialAccountDao officialAccountDao();

}
