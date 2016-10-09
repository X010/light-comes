package com.light.outside.comes.model;

import com.light.outside.comes.mybatis.mapper.PersistentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BanquetModel extends BaseModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 金额
     */
    private float amount;

    /**
     * 人数
     */
    private int outnumber;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 开始时间
     */
    private Date open_time;

    /**
     * 承办人名称
     */
    private String author_nickname;

    /**
     *  承办人电话
     */
    private String author_telephone;

    /**
     * 备注
     */
    private String memo;

    /**
     * 状态
     */
    private int status;

    /**
     *  承办地址
     */
    private String author_address;

    /**
     * 桌数
     */
    private int desk_num;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getOutnumber() {
        return outnumber;
    }

    public void setOutnumber(int outnumber) {
        this.outnumber = outnumber;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getOpen_time() {
        return open_time;
    }

    public void setOpen_time(Date open_time) {
        this.open_time = open_time;
    }

    public String getAuthor_nickname() {
        return author_nickname;
    }

    public void setAuthor_nickname(String author_nickname) {
        this.author_nickname = author_nickname;
    }

    public String getAuthor_telephone() {
        return author_telephone;
    }

    public void setAuthor_telephone(String author_telephone) {
        this.author_telephone = author_telephone;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAuthor_address() {
        return author_address;
    }

    public void setAuthor_address(String author_address) {
        this.author_address = author_address;
    }

    public int getDesk_num() {
        return desk_num;
    }

    public void setDesk_num(int desk_num) {
        this.desk_num = desk_num;
    }
}
