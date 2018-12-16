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
import com.yuloran.lib_core.INoProguard;
import com.yuloran.lib_core.annotations.Useful;
import com.yuloran.lib_core.constant.ISection;

import java.util.List;

/**
 * [内容详情，如首页推荐文章详情、公众号文章详情、某个项目详情等]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/11/30 23:48
 *
 * @since 1.0.0
 */
public class Item implements INoProguard
{
    /** 内容id */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("id")
    private int id;

    /** 所属栏目id */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("chapterId")
    private int chapterId;

    /** 所属栏目名称 */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("chapterName")
    private String chapterName;

    /** 所属栏目的所属栏目id */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("superChapterId")
    private int superChapterId;

    /** 所属栏目的所属栏目名称 */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("superChapterName")
    private String superChapterName;

    /** 内容作者名字 */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("author")
    private String author;

    /** 内容标题 */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("title")
    private String title;

    /** 内容站内地址 */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("link")
    private String link;

    /** 格式化后的发布日期，如 '2天前' */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("niceDate")
    private String niceDate;

    /** 发布时间(unix timestamp, ms) */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("publishTime")
    private long publishTime;

    /** 内容是否收藏(需要登录) */
    @Useful({ISection.OFFICIAL_ACCOUNT, ISection.PROJECT})
    @Expose
    @SerializedName("collect")
    private boolean collect;

    /** 项目描述 */
    @Useful(ISection.PROJECT)
    @Expose
    @SerializedName("desc")
    private String desc;

    /** 项目封面 */
    @Useful(ISection.PROJECT)
    @Expose
    @SerializedName("envelopePic")
    private String envelopePic;

    /** 项目github地址 */
    @Useful(ISection.PROJECT)
    @Expose
    @SerializedName("projectLink")
    private String projectLink;

    @Expose
    private String apkLink;
    @Expose
    private int courseId;
    @Expose
    private boolean fresh;
    @Expose
    private String origin;
    @Expose
    private int type;
    @Expose
    private int userId;
    @Expose
    private int visible;
    @Expose
    private int zan;
    @Expose
    private List<TagsBean> tags;

    public String getApkLink()
    {
        return apkLink;
    }

    public void setApkLink(String apkLink)
    {
        this.apkLink = apkLink;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public int getChapterId()
    {
        return chapterId;
    }

    public void setChapterId(int chapterId)
    {
        this.chapterId = chapterId;
    }

    public String getChapterName()
    {
        return chapterName;
    }

    public void setChapterName(String chapterName)
    {
        this.chapterName = chapterName;
    }

    public boolean isCollect()
    {
        return collect;
    }

    public void setCollect(boolean collect)
    {
        this.collect = collect;
    }

    public int getCourseId()
    {
        return courseId;
    }

    public void setCourseId(int courseId)
    {
        this.courseId = courseId;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getEnvelopePic()
    {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic)
    {
        this.envelopePic = envelopePic;
    }

    public boolean isFresh()
    {
        return fresh;
    }

    public void setFresh(boolean fresh)
    {
        this.fresh = fresh;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getNiceDate()
    {
        return niceDate;
    }

    public void setNiceDate(String niceDate)
    {
        this.niceDate = niceDate;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getProjectLink()
    {
        return projectLink;
    }

    public void setProjectLink(String projectLink)
    {
        this.projectLink = projectLink;
    }

    public long getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(long publishTime)
    {
        this.publishTime = publishTime;
    }

    public int getSuperChapterId()
    {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId)
    {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName()
    {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName)
    {
        this.superChapterName = superChapterName;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getVisible()
    {
        return visible;
    }

    public void setVisible(int visible)
    {
        this.visible = visible;
    }

    public int getZan()
    {
        return zan;
    }

    public void setZan(int zan)
    {
        this.zan = zan;
    }

    public List<TagsBean> getTags()
    {
        return tags;
    }

    public void setTags(List<TagsBean> tags)
    {
        this.tags = tags;
    }

    public static class TagsBean
    {

        /** 公众号 */
        private String name;

        /** /wxarticle/list/408/1 */
        private String url;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }
}
