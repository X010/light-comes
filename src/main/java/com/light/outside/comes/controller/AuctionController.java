package com.light.outside.comes.controller;

import com.light.outside.comes.model.*;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.AuctionService;
import com.light.outside.comes.service.PayService;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.DateUtils;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("auction")
public class AuctionController extends BaseController {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private FocusImageService focusImageService;
    @Autowired
    private PayService payService;

    /**
     * 拍卖活动列表
     *
     * @return
     */
    @RequestMapping("auction.action")
    public String auction(Map<String, Object> data) {
        //输出焦点图
        List<FocusImageModel> focusImageModelList = this.focusImageService.queryFocusImageByColumn(CONST.FOCUS_AUCTION);
        if (focusImageModelList != null) {
            data.put("focus", focusImageModelList);
        }
        //输出拍卖活动列表
        PageModel pageModel = new PageModel();
        pageModel.setPage(1);
        pageModel.setSize(Integer.MAX_VALUE);
        PageResult<AuctionModel> auctionModelPageResult = auctionService.getAuctions(pageModel);
        List<AuctionModel> auctionModels = auctionModelPageResult.getData();
        if (auctionModels != null) {
            data.put("auctions", auctionModels);
        }
        return "auction";
    }

    /**
     * 拍卖活动列表API
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("auction_list.action")
    @ResponseBody
    public String auctionList(Map<String, Object> data, HttpServletRequest request, HttpServletRequest response) {
        int page = RequestTools.RequestInt(request, "page", 1);
        int size = RequestTools.RequestInt(request, "size", Integer.MAX_VALUE);
        PageModel pageModel = new PageModel();
        pageModel.setPage(page);
        pageModel.setSize(size);
        PageResult<AuctionModel> auctionModelPageResult = this.auctionService.getAuctions(pageModel);
        List<AuctionModel> raffleModels = auctionModelPageResult.getData();
        if (raffleModels != null && raffleModels.size() > 0) {
            return JsonTools.jsonSer(raffleModels);
        } else {
            return "";
        }
    }

    /**
     * 出价
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("bid.action")
    @ResponseBody
    public String bid(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        //出价
        float price = Float.parseFloat(request.getParameter("price").toString());
        long aid = Long.parseLong(request.getParameter("aid").toString());
        UserModel userModel = getAppUserInfo();
        AuctionModel auctionModel = auctionService.getAuctionById(aid);
        //TODO 判断保证金是否支付
        //获取截止时间
        long seconds = DateUtils.endSeconds(auctionModel.getEnd_time());
        int code = 0;
        String msg = "出价失败！";
        boolean isSuccess = false;
        if (seconds <= 0) {
            msg = "拍卖已截止！";
        } else {
            //查询是否有更高的出价
            AuctionRecordsModel auctionRecordsModel = auctionService.queryTopRecord(aid, userModel.getId());
            float topPrice = 0;
            if (auctionRecordsModel != null) {
                topPrice = auctionRecordsModel.getPrice();
            }
            if (price > topPrice) {
                isSuccess = auctionService.bidAuction(userModel, aid, price);
                if (isSuccess) {
                    code = 1;
                    msg = "出价成功！";
                }
            } else {
                msg = "出价失败，目前最高价格:" + topPrice;
                data.put("topPrice", topPrice);
            }
        }
        data.put("isSuccess", isSuccess);
        data.put("msg", msg);
        data.put("code", code);
        //保存出价记录返回结果
        return JsonTools.jsonSer(data);
    }

    /**
     * 拍卖页面
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("auction_d.action")
    public String auctionDetail(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        int auctionId = RequestTools.RequestInt(request, "aid", 0);
        //查询拍卖活动
        AuctionModel auctionModel = auctionService.queryAuctionById(auctionId);
        //查询出价记录
        List<AuctionRecordsModel> auctionRecordsModels = auctionService.queryAuctionRecordsByAid(auctionId);
        //秒数
        if (auctionModel != null) {
            long seconds = DateUtils.endSeconds(auctionModel.getEnd_time());
            auctionModel.setTime_second((int) seconds);
            data.put("seconds", seconds);
        }
        data.put("isSuccess",true);
        data.put("auction", auctionModel);
        data.put("auctionRecords", auctionRecordsModels);
        return "auction_d";
    }

    @RequestMapping("auction_margin.action")
    public String margin(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        boolean isSuccess = true;
        //OrderModel orderModel=new OrderModel();
        //long id=payService.createOrder(orderModel);
        data.put("isSuccess", isSuccess);
        return "auction_d";
    }

}
