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
public class PastTotal extends BaseModel {

    /**
     * 用户ID
     */
    private long uid;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 今天喝的次数
     */
    private int today_times;

    /**
     * 今天还有多少次
     */
    private int today_have_times;

    /**
     * 今天喝的量
     */
    private int today_drunk;

    /**
     * 朋友喝的次数
     */
    private int today_other_times;

    /**
     * 朋友喝的量
     */
    private int today_other_drunk;

    /**
     * 周期次数
     */
    private int cycle_times;

    /**
     * 瓶子总量
     */
    private int total_drunk;

    /**
     * 总期量
     */
    private int cycle_drunk;

    /**
     * 更新时间
     */
    private Date update_time;
    /**
     *
     */
    private int drunk_num;

    public int getDrunk_num() {
        return drunk_num;
    }

    public void setDrunk_num(int drunk_num) {
        this.drunk_num = drunk_num;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getTotal_drunk() {
        return total_drunk;
    }

    public void setTotal_drunk(int total_drunk) {
        this.total_drunk = total_drunk;
    }

    public int getToday_have_times() {
        return today_have_times;
    }

    public void setToday_have_times(int today_have_times) {
        this.today_have_times = today_have_times;
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

    public int getToday_times() {
        return today_times;
    }

    public void setToday_times(int today_times) {
        this.today_times = today_times;
    }

    public int getToday_drunk() {
        return today_drunk;
    }

    public void setToday_drunk(int today_drunk) {
        this.today_drunk = today_drunk;
    }

    public int getToday_other_times() {
        return today_other_times;
    }

    public void setToday_other_times(int today_other_times) {
        this.today_other_times = today_other_times;
    }

    public int getToday_other_drunk() {
        return today_other_drunk;
    }

    public void setToday_other_drunk(int today_other_drunk) {
        this.today_other_drunk = today_other_drunk;
    }

    public int getCycle_times() {
        return cycle_times;
    }

    public void setCycle_times(int cycle_times) {
        this.cycle_times = cycle_times;
    }

    public int getCycle_drunk() {
        return cycle_drunk;
    }

    public void setCycle_drunk(int cycle_drunk) {
        this.cycle_drunk = cycle_drunk;
    }
}
