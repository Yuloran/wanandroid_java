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
package com.yuloran.lib_repository.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * [微信公众号DAO]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 12:05
 *
 * @since 1.0.0
 */
@Dao
public interface OfficialAccountDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(OfficialAccount officialAccount);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<OfficialAccount> officialAccounts);

    @Query("select * from official_account")
    List<OfficialAccount> query();

    @Query("delete from official_account")
    void clear();
}
