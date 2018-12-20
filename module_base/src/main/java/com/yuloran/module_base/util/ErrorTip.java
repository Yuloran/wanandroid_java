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

import android.util.SparseIntArray;

import com.yuloran.lib_core.constant.ErrCode;
import com.yuloran.module_base.R;

import io.reactivex.annotations.NonNull;

/**
 * [错误码对应的提示]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 22:26
 *
 * @since 1.0.0
 */
public final class ErrorTip
{
    private static final String TAG = "ErrorTip";

    private ErrorTip()
    {
    }

    private static final SparseIntArray ERR_TIPS = new SparseIntArray();

    static
    {
        ERR_TIPS.put(ErrCode.UNKNOWN_ERROR, R.string.err_unknown);
        ERR_TIPS.put(ErrCode.NULL_RESPONSE, R.string.err_null_response);
        ERR_TIPS.put(ErrCode.SOCKET_TIME_OUT, R.string.err_time_out);
        ERR_TIPS.put(ErrCode.IO_ERROR, R.string.err_io_exception);
        ERR_TIPS.put(ErrCode.UNKNOWN_HOST, R.string.err_unknown_host);
    }

    @NonNull
    public static String getErrMsg(int errCode)
    {
        int resId = ERR_TIPS.get(errCode);
        String string = ResUtil.getString(resId);
        return string.isEmpty() ? ResUtil.getString(ERR_TIPS.get(ErrCode.UNKNOWN_ERROR)) : string;
    }
}
