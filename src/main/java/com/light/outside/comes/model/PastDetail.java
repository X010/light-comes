package com.light.outside.comes.model;

import java.util.Date;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class PastDetail extends BaseModel {

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 用户ID
     */
    private long uid;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 朋友 ID
     */
    private long friend_uid;

    /**
     * 朋友手机号
     */
    private String friend_phone;

    /**
     * 类型,1 自喝，2 干杯
     */
    private int drunk_type;

    /**
     * 喝的量
     */
    private int drunk_num;

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getFriend_uid() {
        return friend_uid;
    }

    public void setFriend_uid(long friend_uid) {
        this.friend_uid = friend_uid;
    }

    public String getFriend_phone() {
        return friend_phone;
    }

    public void setFriend_phone(String friend_phone) {
        this.friend_phone = friend_phone;
    }

    public int getDrunk_type() {
        return drunk_type;
    }

    public void setDrunk_type(int drunk_type) {
        this.drunk_type = drunk_type;
    }

    public int getDrunk_num() {
        return drunk_num;
    }

    public void setDrunk_num(int drunk_num) {
        this.drunk_num = drunk_num;
    }
}
