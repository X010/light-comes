package com.light.outside.comes.controller;

import com.light.outside.comes.model.*;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.BackListService;
import com.light.outside.comes.service.RaffleService;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.DateUtils;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Controller
@RequestMapping("raffle")
public class RaffleController extends BaseController {

    @Autowired
    private RaffleService raffleService;


    @Autowired
    private FocusImageService focusImageService;
    @Autowired
    private BackListService backListService;

    /**
     * 抽奖列表
     *
     * @return
     */
    @RequestMapping("lottery.action")
    public String lottery(Map<String, Object> data) {
        //输出焦点图
        List<FocusImageModel> focusImageModelList = this.focusImageService.queryFocusImageByColumn(CONST.FOCUS_RAFFLE);
        if (focusImageModelList != null) {
            data.put("focus", focusImageModelList);
        }
        //输出抽奖活动列表
        PageModel pageModel = new PageModel();
        pageModel.setPage(1);
        pageModel.setSize(Integer.MAX_VALUE);
        PageResult<RaffleModel> raffleModelPageResult = this.raffleService.getRaffles(pageModel);
        List<RaffleModel> raffleModels = raffleModelPageResult.getData();
        if (raffleModels != null) {
            data.put("raffles", raffleModels);
        }
        return "lottery";
    }

    /**
     * 下拉接个口
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("lottery_list.action")
    @ResponseBody
    public String lotterList(Map<String, Object> data, HttpServletRequest request, HttpServletRequest response) {
        int page = RequestTools.RequestInt(request, "page", 1);
        int size = RequestTools.RequestInt(request, "size", Integer.MAX_VALUE);
        PageModel pageModel = new PageModel();
        pageModel.setPage(page);
        pageModel.setSize(size);
        PageResult<RaffleModel> raffleModelPageResult = this.raffleService.getRaffles(pageModel);
        List<RaffleModel> raffleModels = raffleModelPageResult.getData();
        if (raffleModels != null && raffleModels.size() > 0) {
            return JsonTools.jsonSer(raffleModels);
        } else {
            return "";
        }
    }

    /**
     * 抽奖
     *
     * @return
     */
    @RequestMapping("lottery_d.action")
    public String lottery_d(Map<String, Object> data, HttpServletRequest request, HttpServletRequest response) {
        long rid = RequestTools.RequestLong(request, "rid", 0);
        UserModel userModel = getAppUserInfo();
        long uid = userModel.getId();
        List<CouponRecordModel> couponRecordModels = raffleService.queryCouponRecords(rid);
        //List<RaffleCouponModel> raffleCouponModels = raffleService.getRaffleCoupons(rid);
        RaffleModel raffleModel = raffleService.getRaffleById(rid);
        int rCount = raffleService.getUserRaffleCount(uid, rid);
        if (raffleModel != null) {
            int remainCount = raffleModel.getTimes() - rCount;
            if (remainCount < 0) {
                rCount = 0;
            } else {
                rCount = remainCount;
            }
        }
        data.put("raffle", raffleModel);
        data.put("rCount", rCount);
        data.put("records", couponRecordModels);
//        data.put("coupons", JsonTools.jsonSer(raffleModel.getRaffleCouponModels()));
        return "lottery_d";
    }

    @RequestMapping("lottery_raffle.action")
    @ResponseBody
    public String lottery_raffle(Map<String,Object> data,HttpServletRequest request, HttpServletRequest response){
        long rid = RequestTools.RequestLong(request, "rid", 0);
        RaffleModel raffleModel = raffleService.getRaffleById(rid);
        return JsonTools.jsonSer(raffleModel);
    }

    @RequestMapping("lottery_raffle_coupon.action")
    @ResponseBody
    public String raffle_coupon(Map<String, Object> data, HttpServletRequest request, HttpServletRequest response) {
        long rid = RequestTools.RequestLong(request, "rid", 0);
        List<RaffleCouponModel> raffleCouponModels = raffleService.getRaffleCoupons(rid);
        String result = JsonTools.jsonSer(raffleCouponModels);
        return result;
    }

    /**
     * 抽奖
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("lottery_draw.action")
    @ResponseBody
    public String lottery_draw(Map<String, Object> data, HttpServletRequest request, HttpServletRequest response) {
        UserModel userModel = getAppUserInfo();
        long id = RequestTools.RequestLong(request, "id", 0);
        long rid = RequestTools.RequestInt(request, "rid", 0);
        long uid = userModel.getId();
        String phone = userModel.getPhone();//"18888888888";
        int code = 0;
        String msg = "很遗憾，没有中奖!";
        int rCount = 0;
        //获取最新的抽奖次数
        rCount = raffleService.getUserRaffleCount(uid, rid);
        //获取允许抽奖次数
        RaffleModel raffleModel = raffleService.getRaffleById(rid);
        Date endTime = raffleModel.getEnd_time();
        long seconds = DateUtils.endSeconds(endTime);
        int remainCount = raffleModel.getTimes() - rCount;
        if (seconds < 0) {
            msg = "抽奖活动已截止!";
        } else if (remainCount > 0) {
            //查询黑名单
            //TODO 获取用户手机号码
            BackList backList = backListService.getBackListByPhoneAndCtype(phone, CONST.FOCUS_RAFFLE);
            //用户不在黑名单中
            if (backList == null) {
                RaffleCouponModel raffleCouponModel = raffleService.drawRaffleByRage(id,uid, phone);
                if (raffleCouponModel != null) {
                    code = 1;
                    msg = "恭喜你，抽中" + raffleCouponModel.getTitle();
                    data.put("id", raffleCouponModel.getId());
                }
            }
        } else {
            msg = "您的抽奖次数已用完!";
        }
        //更新抽奖次数(如果不存在则新增，存在则+1)
        raffleService.addRaffleCount(uid, rid, 1);
        remainCount--;
        if (remainCount < 0) {
            remainCount = 0;
        }
        data.put("code", code);
        data.put("msg", msg);
        data.put("rCount", remainCount);
        String result = JsonTools.jsonSer(data);
        System.out.println(result);
        return result;
    }



    @RequestMapping("mine_coupon_list.action")
    @ResponseBody
    public String mine_coupon_list(Map<String, Object> data, HttpServletRequest request) {
        int status = RequestTools.RequestInt(request, "status", 0);
        int page = RequestTools.RequestInt(request, "page", 1);
        int size = RequestTools.RequestInt(request, "size", Integer.MAX_VALUE);
        PageModel pageModel = new PageModel();
        pageModel.setPage(page);
        pageModel.setSize(size);
        UserModel userModel = getAppUserInfo();
        PageResult<CouponRecordViewModel> list = raffleService.getRaffleCouponPageByUser(userModel.getId(), status, pageModel);
        List<CouponRecordViewModel> couponRecordModels = list.getData();
        if (couponRecordModels != null && couponRecordModels.size() > 0) {
            return JsonTools.jsonSer(couponRecordModels);
        } else {
            return "";
        }
    }
}
