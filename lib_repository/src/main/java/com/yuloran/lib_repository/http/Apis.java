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

import com.yuloran.lib_core.bean.backend.response.PageResp;
import com.yuloran.lib_core.bean.backend.response.SectionResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * [Apis]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/1 16:59
 *
 * @since 1.0.0
 */
public interface Apis
{

    /**
     * [IWanAndroidApi]
     * <p>
     * Author: Yuloran
     * Date Added: 2018/12/1 17:48
     *
     * @since 1.0.0
     */
    interface IWanAndroidApi
    {

        String BASE_URL = "http://www.wanandroid.com/";

        /**
         * 获取公众号列表
         *
         * @return {@link SectionResp}
         */
        @GET("wxarticle/chapters/json")
        Call<SectionResp> getOfficialAccounts();

        /**
         * 查看某个公众号历史数据
         *
         * @param id   公众号id
         * @param page 页码
         * @return {@link PageResp}
         */
        @GET("wxarticle/list/{id}/{page}/json")
        Call<PageResp> getOfficialAccountArticles(@Path("id") String id, @Path("page") String page);

        /**
         * 在某个公众号中搜索历史文章
         *
         * @param id      公众号id
         * @param page    页码
         * @param keyword 关键字
         * @return {@link PageResp}
         */
        @GET("wxarticle/list/{id}/{page}/json")
        Call<PageResp> searchOfficialAccountArticles(@Path("id") String id, @Path("page") String page, @Query("k")
                String keyword);

        /**
         * 获取项目分类
         *
         * @return {@link SectionResp}
         */
        @GET("project/tree/json")
        Call<SectionResp> getProjects();

        /**
         * 查询项目列表数据
         *
         * @param page      查询页码
         * @param sectionId 项目id
         * @return {@link PageResp}
         */
        @GET("project/list/{page}/json")
        Call<PageResp> getProjectItems(@Path("page") String page, @Query("cid") String sectionId);
    }
}
