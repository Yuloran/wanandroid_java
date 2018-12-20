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
package com.yuloran.lib_repository.viewdata;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * [UI State: init->loading->loadSuccess|loadFailure]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/20 20:59
 *
 * @param <T> UI需要的数据类型
 * @since 1.0.0
 */
public class BaseViewData<T>
{
    @NonNull
    private ViewState viewState;

    private T viewData;

    public BaseViewData(@NonNull ViewState viewState)
    {
        Objects.requireNonNull(viewState, "viewState is null!");
        this.viewState = viewState;
    }

    public BaseViewData(@NonNull T viewData)
    {
        this.viewState = ViewState.LOAD_SUCCESS;
        this.viewData = viewData;
    }

    @Nullable
    public T getViewData()
    {
        return viewData;
    }

    public void setViewData(@NonNull T data)
    {
        this.viewData = data;
    }

    @NonNull
    public ViewState getViewState()
    {
        return viewState;
    }

    public void setViewState(ViewState viewState)
    {
        this.viewState = viewState;
    }
}
