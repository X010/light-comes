package com.light.outside.comes.task;

import com.light.outside.comes.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
@Component
public class TaskService {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private RaffleService raffleService;

    @Autowired
    private OverchargedService overchargedService;

    @Autowired
    private BanquetService banquetService;

    @Autowired
    private PastService pastService;
    @Autowired
    private CouponService couponService;


    /**
     * 拍卖定时任务
     */
    @Scheduled(cron = "0 */1 * * * * ")//每分钟执行一次
    public void auctionScheuled() {
        System.out.println("clear auction");
        this.auctionService.clearAuction();
    }

    /**
     * 砍价定时任务
     */
    @Scheduled(cron = "0 */1 *  * * * ")
    public void overchargedScheuled() {
        this.overchargedService.clearOvercharged();
    }

    /**
     * 抽奖定时任务
     */
    @Scheduled(cron = "0 */1 *  * * * ")
    public void raffleScheuled() {
        this.raffleService.clearRaffle();
    }

    /**
     * 约饭定时任务
     */
    @Scheduled(cron = "0 */1 *  * * * ")
    public void banquetScheuled() {
        this.banquetService.clearBanquet();
    }

    /**
     * 清空签到信息
     */
    @Scheduled(cron = "0 15 1 ? * * ")
    public void clearEveryDayPastInfo() {
        this.pastService.clearEveryDayPastInfo();
    }

    /**
     * 修改过期优惠券状态
     */
    @Scheduled(cron = "0 */1 *  * * * ")
    public void clearCoupon(){
        this.couponService.changeCouponRecordStatus();
    }
}
