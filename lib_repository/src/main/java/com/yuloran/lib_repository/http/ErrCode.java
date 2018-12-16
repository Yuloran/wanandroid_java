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
package com.yuloran.lib_repository.http;

/**
 * [错误码定义]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 22:20
 *
 * @since 1.0.0
 */
public interface ErrCode
{
    int UNKNOWN_ERROR = -100000;

    int NULL_RESPONSE = 100000;

    int SOCKET_TIME_OUT = 100001;

    int IO_ERROR = 100002;

    int UNKNOWN_HOST = 100003;
}
