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
package com.yuloran.lib_core.template.nullsafe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yuloran.lib_core.utils.Logger;
import com.yuloran.lib_core.utils.StringUtil;

import androidx.annotation.NonNull;

/**
 * [null安全的广播接收器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 22:56
 *
 * @since 1.0.0
 */
public abstract class SafeBroadCastReceiver extends BroadcastReceiver
{
    private static final String TAG = "SafeBroadCastReceiver";

    protected abstract void onSafeReceive(Context context, @NonNull Intent intent, @NonNull String action);

    @Override
    public void onReceive(Context context, Intent intent)
    {
        final String action = intent == null ? null : intent.getAction();
        if (StringUtil.isEmpty(action))
        {
            Logger.warn(TAG, "Invalid Action!");
            return;
        }

        onSafeReceive(context, intent, action);
    }
}
