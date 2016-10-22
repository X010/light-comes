package com.light.outside.comes.controller;

import com.light.outside.comes.model.*;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.service.RaffleService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
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
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Controller
@RequestMapping("raffle")
public class RaffleController {

    @Autowired
    private RaffleService raffleService;


    @Autowired
    private FocusImageService focusImageService;

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
     * 抽奖
     *
     * @return
     */
    @RequestMapping("lottery_d.action")
    public String lottery_d(Map<String, Object> data,HttpServletRequest request,HttpServletRequest response) {
        long rid=RequestTools.RequestLong(request, "rid", 13);
        List<CouponRecordModel> couponRecordModels=raffleService.queryCouponRecords(rid);
        List<RaffleCouponModel> raffleCouponModels= raffleService.getRaffleCoupons(rid);
        data.put("records",couponRecordModels);
        data.put("coupons", JsonTools.jsonSer(raffleCouponModels));
        return "lottery_d";
    }

    @RequestMapping("raffle_coupon.action")
    @ResponseBody
    public String raffle_coupon(Map<String, Object> data,HttpServletRequest request,HttpServletRequest response) {
        long rid=RequestTools.RequestLong(request, "rid", 13);
        List<RaffleCouponModel> raffleCouponModels= raffleService.getRaffleCoupons(rid);
        String result=JsonTools.jsonSer(raffleCouponModels);
        return result;
    }
    /**
     * 抽奖
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("lottery_draw.action")
    @ResponseBody
    public String lottery_draw(Map<String, Object> data,HttpServletRequest request,HttpServletRequest response){
        long id=RequestTools.RequestLong(request, "id", 22);
        long rid= RequestTools.RequestInt(request,"rid",13);
        int code=0;
        String msg="谢谢参与!";
        //RaffleCouponModel raffleCouponModel=raffleService.drawRaffle(id);
        RaffleCouponModel raffleCouponModel=raffleService.drawRaffleByRage(id);
        if(raffleCouponModel!=null){
            code=1;
            msg="恭喜你，抽中"+raffleCouponModel.getTitle();
            data.put("id",raffleCouponModel.getId());
        }
        data.put("code",code);
        data.put("msg", msg);
        String result=JsonTools.jsonSer(data);
        System.out.println(result);
        return result;
    }
}
