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

import java.util.List;

/**
 * [栏目分页详情]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/11/30 22:35
 * Version: 1.0.0
 */
public class Page {

    /** 当前查询页码 */
    @Useful
    @Expose
    @SerializedName("curPage")
    private int curPage;

    /** 本次查询第一个item的索引偏移量 */
    @Useful
    @Expose
    @SerializedName("offset")
    private int offset;

    /** 是否超出最大查询页码(注意{@link #curPage} = {@link #pageCount}时，也为true) */
    @Useful
    @Expose
    @SerializedName("over")
    private boolean over;

    /** 最大查询页码 */
    @Useful
    @Expose
    @SerializedName("pageCount")
    private int pageCount;

    /** 本次查询返回的栏目内容个数(默认返回20个) */
    @Useful
    @Expose
    @SerializedName("size")
    private int size;

    /** item总数 */
    @Useful
    @Expose
    @SerializedName("total")
    private int total;

    /** item */
    @Useful
    @Expose
    @SerializedName("datas")
    private List<Item> items;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
