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
package com.yuloran.wanandroid_java.ui.datadinding;

import android.text.Html;
import android.widget.TextView;

import com.yuloran.lib_core.utils.StringUtil;

import androidx.databinding.BindingAdapter;

/**
 * [wanAndroidApi返回的title常带有html符号，需要解析]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/17 23:29
 *
 * @since 1.0.0
 */
public final class TextViewHtmlBindingAdapter
{
    @BindingAdapter("app:text")
    public static void setText(TextView textView, String title)
    {
        textView.setText(Html.fromHtml(StringUtil.emptyIfNull(title)));
    }
}
