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
package com.yuloran.lib_core.bean.backend.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yuloran.lib_core.annotations.Useful;

/**
 * [栏目详情，如公众号详情、项目详情等]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/11/30 23:29
 *
 * @since 1.0.0
 */
public class Section
{

    /** 栏目id */
    @Useful
    @Expose
    @SerializedName("id")
    private int id;

    /** 栏目名称 */
    @Useful
    @Expose
    @SerializedName("name")
    private String name;

    /** 上级栏目id */
    @Useful
    @Expose
    @SerializedName("parentChapterId")
    private int parentChapterId;

    @Expose
    private int order;
    @Expose
    private int courseId;
    @Expose
    private boolean userControlSetTop;
    @Expose
    private int visible;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getOrder()
    {
        return order;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }

    public int getCourseId()
    {
        return courseId;
    }

    public void setCourseId(int courseId)
    {
        this.courseId = courseId;
    }

    public int getParentChapterId()
    {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId)
    {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop()
    {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop)
    {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible()
    {
        return visible;
    }

    public void setVisible(int visible)
    {
        this.visible = visible;
    }

    @Override
    public String toString()
    {
        return "Section{" + "id=" + id + ", name='" + name + '\'' + ", parentChapterId=" + parentChapterId + ", " +
                "order=" + order + ", courseId=" + courseId + ", userControlSetTop=" + userControlSetTop + ", " +
                "visible=" + visible + '}';
    }
}
