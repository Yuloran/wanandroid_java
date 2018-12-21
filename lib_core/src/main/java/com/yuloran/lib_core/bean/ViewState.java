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
package com.yuloran.lib_core.bean;

import com.yuloran.lib_core.constant.Cons;

/**
 * [View State]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/20 21:55
 *
 * @since 1.0.0
 */
public final class ViewState
{
    public static final ViewState UNINITIALIZED = new ViewState(Cons.STATE_UNINITIALIZED);

    public static final ViewState LOADING = new ViewState(Cons.STATE_LOADING);

    public static final ViewState LOAD_SUCCESS = new ViewState(Cons.STATE_LOAD_SUCCESS);

    private int state;

    private int errCode;

    private String errMsg;

    private ViewState(int state)
    {
        this.state = state;
    }

    public ViewState(int errCode, String errMsg)
    {
        this.state = Cons.STATE_LOAD_FAILURE;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getState()
    {
        return state;
    }

    public int getErrCode()
    {
        return errCode;
    }

    public String getErrMsg()
    {
        return errMsg;
    }

    public boolean isUnInitialized()
    {
        return state == Cons.STATE_UNINITIALIZED;
    }

    public boolean isLoading()
    {
        return state == Cons.STATE_LOADING;
    }

    public boolean isSuccessful()
    {
        return state == Cons.STATE_LOAD_SUCCESS;
    }

    public boolean isFailed()
    {
        return state == Cons.STATE_LOAD_FAILURE;
    }

    @Override
    public String toString()
    {
        return "ViewState{" + "state=" + state + ", errCode=" + errCode + ", errMsg='" + errMsg + '\'' + '}';
    }
}
