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
package com.yuloran.lib_repository.http.common;

import com.yuloran.lib_core.bean.backend.response.BaseResp;
import com.yuloran.lib_repository.http.ErrCode;

import io.reactivex.functions.Predicate;

/**
 * [响应异常检测]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 13:11
 *
 * @since 1.0.0
 */
public class ResponsePredicate<T extends BaseResp> implements Predicate<T>
{
    @Override
    public boolean test(T t) throws Exception
    {
        if (t == null)
        {
            throw new CommonRequestException(ErrCode.NULL_RESPONSE, "Backend Response is null!");
        }

        if (!t.isSuccessful())
        {
            throw new CommonRequestException(t.getErrorCode(), "Backend Response Code is not 0!");
        }

        return true;
    }
}
