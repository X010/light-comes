package com.light.outside.comes.controller;

import com.light.outside.comes.model.AuctionModel;
import com.light.outside.comes.model.AuctionRecordsModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.AuctionService;
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
@RequestMapping("auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private FocusImageService focusImageService;

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
            data.put("auction", auctionModels);
        }
        return "auction";
    }
    /**
     * 出价
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("bid.action")
    @ResponseBody
    public String bid(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response){
        //出价
        float price= Float.parseFloat(request.getParameter("price").toString());
        long aid=Long.parseLong(request.getParameter("aid").toString());
        UserModel userModel= (UserModel) request.getSession().getAttribute("user");
        boolean isSuccess=auctionService.bidAuction(userModel, aid, price);
        int code=0;
        String msg="出价失败！";
        if(isSuccess) {
            code=1;
            msg="出价成功！";
        }
        data.put("isSuccess", isSuccess);
        data.put("msg",msg);
        data.put("code",code);
        //保存出价记录返回结果
        return JsonTools.jsonSer(data);
    }

    @RequestMapping("auction_detail.acton")
    public String auctionDetail(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response){
        int auctionId=RequestTools.RequestInt(request,"id",0);
        //查询拍卖活动
        AuctionModel auctionModel=auctionService.queryAuctionById(auctionId);
        //查询出价记录
        List<AuctionRecordsModel> auctionRecordsModels=auctionService.queryAuctionRecordsByAid(auctionId);
        //秒数
        long second=DateUtils.betweenSeconds(auctionModel.getEnd_time());
        auctionModel.setTime_second((int) second);
        data.put("auction", auctionModel);
        data.put("auctionRecords",auctionRecordsModels);
        data.put("second",second);
        return "admin/auction_d";
    }

}
