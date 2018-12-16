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

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * [微信公众号表]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/16 11:57
 *
 * @since 1.0.0
 */
@Entity(tableName = "official_account")
public class OfficialAccount
{
    @PrimaryKey
    @ColumnInfo(name = "account_id")
    private int accountId;

    @NonNull
    private String author;

    public OfficialAccount(int accountId, @NonNull String author)
    {
        this.accountId = accountId;
        this.author = author;
    }

    public int getAccountId()
    {
        return accountId;
    }

    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }

    @NonNull
    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(@NonNull String author)
    {
        this.author = author;
    }

    @Override
    public String toString()
    {
        return "OfficialAccount{" +
                "accountId='" + accountId + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
