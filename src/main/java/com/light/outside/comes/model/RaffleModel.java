package com.light.outside.comes.model;

import java.io.Serializable;
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
public class RaffleModel implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 开始时间
     */
    private Date start_time;

    /**
     * 结束时间
     */
    private Date end_time;

    /**
     * 劵类型
     */
    private int raffle_type;

    /**
     * 开始使用时间
     */
    private Date use_start_time;

    /**
     * 结束使用时间
     */
    private Date use_end_time;

    /**
     * 可以使用张数
     */
    private int use_times;

    /**
     * 劵金额
     */
    private float amount;

    /**
     * 可生成张数
     */
    private int total;

    /**
     * 备注
     */
    private String memo;

    /**
     * 每天抽奖张数
     */
    private int day_times;

    /**
     * 图片
     */
    private String photo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public int getRaffle_type() {
        return raffle_type;
    }

    public void setRaffle_type(int raffle_type) {
        this.raffle_type = raffle_type;
    }

    public Date getUse_start_time() {
        return use_start_time;
    }

    public void setUse_start_time(Date use_start_time) {
        this.use_start_time = use_start_time;
    }

    public Date getUse_end_time() {
        return use_end_time;
    }

    public void setUse_end_time(Date use_end_time) {
        this.use_end_time = use_end_time;
    }

    public int getUse_times() {
        return use_times;
    }

    public void setUse_times(int use_times) {
        this.use_times = use_times;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getDay_times() {
        return day_times;
    }

    public void setDay_times(int day_times) {
        this.day_times = day_times;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
