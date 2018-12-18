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

import com.yuloran.lib_core.bean.backend.response.Item;

import java.util.List;

/**
 * [公众号文章]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/19 1:29
 *
 * @since 1.0.0
 */
public class ArticlesBean
{
    private boolean isOver;

    private List<Item> articles;

    public ArticlesBean(boolean isOver)
    {
        this.isOver = isOver;
    }

    public ArticlesBean(boolean isOver, List<Item> articles)
    {
        this.isOver = isOver;
        this.articles = articles;
    }

    public boolean isOver()
    {
        return isOver;
    }

    public void setOver(boolean over)
    {
        isOver = over;
    }

    public List<Item> getArticles()
    {
        return articles;
    }

    public void setArticles(List<Item> articles)
    {
        this.articles = articles;
    }
}
