package com.light.outside.comes.utils;

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
public class CONST {


    public static String SITE_URL = "http://qulk.dssmp.com/";
    public static String QBLK_PHOTO_URL = "http://120.27.154.7:8069/yeshizuile/frontend/web/images/goods/list_img/";

    public static int RAFFLE_STATUS_NORMAL = 2;//正常
    public static int RAFFLE_STATUS_INIT = 1;//初始化
    public static int RAFFLE_STATUS_BIND = 3;//已绑定到用户
    public static int RAFFLE_STATUS_DELETE = 9;//删除
    public static int RAFFLE_STATUS_OVER = 8;//结束

    public static int COUPON_STATUS_NORMAL = 1;//正常
    public static int COUPON_STATUS_NOTUSED = 2;//未使用
    public static int COUPON_STATUS_USED = 3;//已使用
    public static int COUPON_STATUS_EXPIRATION = 4;//过期


    public static int COUPON_B_INIT = 1;//未结算
    public static int COUPON_B_OVER = 8;//已结算

    public static String EDIT = "EDIT";//修改的标识
    public static int ORDER_CREATE = 1;//创建订单
    public static int ORDER_OVER = 9;//订单过期
    public static int ORDER_PAY = 2;//订单支付
    public static int ORDER_WAITING = 3;//待支付

    public static int WIN = 5;//获得者

    public static int FOCUS_RAFFLE = 1;//抽奖
    public static int FOCUS_OVERCHARGER = 2;//砍价
    public static int FOCUS_AUCTION = 3;//拍续
    public static int FOCUS_BANQUET = 4;//约饭

    public static int PAY_WEIXIN = 1;

    public static int AUCTION_STATUS_BID = 1;//出价中
    public static int AUCTION_STATUS_SUCCESSED = 2;//已拍得
    public static int AUCTION_STATUS_FAIL = 3;//未拍得

    public static int PAST_ID = 9999; //签到活动ID
    public static int DRUNK_SELF = 1; //自己独杯
    public static int DRUNK_OTHER = 2;//与家人干杯
}
