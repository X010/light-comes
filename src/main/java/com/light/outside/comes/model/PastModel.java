package com.light.outside.comes.model;

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
public class PastModel extends BaseModel {

    /**
     * 周期天数
     */
    private int interval_day;

    /**
     * 每天获的最小范围
     */
    private int min_drunk;

    /**
     * 每天喝的最大值
     */
    private int max_drunk;

    /**
     * 需要喝的总量
     */
    private int total_drunk;

    /**
     * 每天可以签到的次数
     */
    private int past_times;


    /**
     * 总换活动ID
     */
    private long coupon_id;

    /**
     * 奖品名称
     */
    private String prizes_name;

    /**
     * 固定量
     */
    private int fix_drunk;

    /**
     * 类型
     */
    private int past_type;

    public int getInterval_day() {
        return interval_day;
    }

    public void setInterval_day(int interval_day) {
        this.interval_day = interval_day;
    }

    public int getMin_drunk() {
        return min_drunk;
    }

    public void setMin_drunk(int min_drunk) {
        this.min_drunk = min_drunk;
    }

    public int getMax_drunk() {
        return max_drunk;
    }

    public void setMax_drunk(int max_drunk) {
        this.max_drunk = max_drunk;
    }

    public int getTotal_drunk() {
        return total_drunk;
    }

    public void setTotal_drunk(int total_drunk) {
        this.total_drunk = total_drunk;
    }

    public int getPast_times() {
        return past_times;
    }

    public void setPast_times(int past_times) {
        this.past_times = past_times;
    }

    public long getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(long coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getPrizes_name() {
        return prizes_name;
    }

    public void setPrizes_name(String prizes_name) {
        this.prizes_name = prizes_name;
    }

    public int getPast_type() {
        return past_type;
    }

    public void setPast_type(int past_type) {
        this.past_type = past_type;
    }

    public int getFix_drunk() {
        return fix_drunk;
    }

    public void setFix_drunk(int fix_drunk) {
        this.fix_drunk = fix_drunk;
    }
}
