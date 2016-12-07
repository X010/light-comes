package com.light.outside.comes.controller;

import com.alibaba.fastjson.JSONObject;
import com.light.outside.comes.controller.pay.TenWeChatGenerator;
import com.light.outside.comes.controller.pay.config.TenWeChatConfig;
import com.light.outside.comes.controller.pay.util.PubUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private Logger LOG = LoggerFactory.getLogger(AuctionController.class);
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
        //获取截止时间
        long seconds = DateUtils.endSeconds(auctionModel.getEnd_time());
        int code = 0;
        String msg = "出价失败！";
        boolean isSuccess = false;
        if (seconds <= 0) {
            msg = "拍卖已截止！";
        } else {
            //TODO 判断保证金是否支付
            OrderModel orderModel = payService.getOrderByUidAndAid(userModel.getId(), aid);
            if (orderModel != null && orderModel.getStatus() == CONST.ORDER_PAY) {
                //查询是否有更高的出价
                AuctionRecordsModel auctionRecordsModel = auctionService.queryTopRecord(aid);
                float topPrice = 0;
                if (auctionRecordsModel != null) {
                    topPrice = auctionRecordsModel.getPrice();
                }
                if ((price - topPrice) < auctionModel.getSetp_amount()) {
                    msg = "加价幅度必须大于" + auctionModel.getSetp_amount() + "元";
                } else if (price > topPrice) {
                    isSuccess = auctionService.bidAuction(userModel, aid, price);
                    LOG.info("auction id:" + auctionModel.getId() + " title:" + auctionModel.getTitle() + " aid:" + aid + " phone: " + userModel.getPhone() + " oruce:" + price);
                    if (isSuccess) {
                        code = 1;
                        msg = "出价成功！";
                    }
                } else {
                    msg = "出价失败，目前最高价格:" + topPrice;
                    data.put("topPrice", topPrice);
                }
            } else {
                msg = "请先支付保证金!";
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
        boolean isPay = false;
        UserModel userModel = getAppUserInfo();
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
            OrderModel orderModel = payService.getOrderByUidAndAid(userModel.getId(), auctionId);
            if (orderModel != null && orderModel.getStatus() == CONST.ORDER_PAY) {
                isPay = true;
            }
        }
        data.put("isPay", isPay);
        data.put("auction", auctionModel);
        data.put("auctionRecords", auctionRecordsModels);
        return "auction_d";
    }

    /**
     * 支付保证金页面
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("auction_margin.action")
    public String margin(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        boolean isPay = false;
        UserModel userModel = getAppUserInfo();
        float amount = Float.parseFloat(request.getParameter("amount"));
        long aid = RequestTools.RequestLong(request, "aid", 0);
        int status = CONST.ORDER_PAY;
        OrderModel orderModel = payService.getOrderByUidAndAid(userModel.getId(), aid);
        //查询拍卖详情
        AuctionModel auctionModel = auctionService.queryAuctionById(aid);
        if (orderModel == null) {
            orderModel = new OrderModel();
            float deposit = auctionModel.getDeposit();
            orderModel.setAmount(amount);
            orderModel.setAtype(CONST.FOCUS_AUCTION);
            orderModel.setAid(aid);
            orderModel.setUid(userModel.getId());
            orderModel.setPhone(userModel.getPhone());
            orderModel.setAname(auctionModel.getTitle());
            orderModel.setStatus(CONST.ORDER_PAY);
            orderModel.setCreatetime(new Date());
            long id = payService.createOrder(orderModel);//创建订单
            if (id > 0) {
                isPay = true;
            }
        } else {
            payService.updateOrder(orderModel.getId(), status);
        }
        //暂时先跳过支付保证金
        data.put("isPay", isPay);
        return auctionDetail(data, request, response);
    }

    @RequestMapping("wechart_redirect.action")
    public String pay(Map<String, Object> data, HttpServletRequest request) {
        String title = RequestTools.RequestString(request, "title", "未知商品");
        String ip = getRemoteHost(request);
        String payPrice = RequestTools.RequestString(request, "price", "0");
        long aid = RequestTools.RequestLong(request, "aid", 0);
        String tourl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + TenWeChatConfig.app_id + "&redirect_uri=" +
                "http%3A%2F%2Fwww.qubulikou.com%2Fqblk%2Fauction%2Fauction_margin_pay.action%3Ftitle%3D" + title + "%26price%3D" + payPrice + "%26aid%3D" + aid +
                "%26response_type%3Dcode%26scope%3Dsnsapi_base%26state%3D321%23wechat_redirect";
//        String tourl="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TenWeChatConfig.app_id+"&redirect_uri=" +
//                "http%3A%2F%2Fwww.qubulikou.com%2Findex.php%3Fr%3Dpromotion%252Flist%26shopid%3D1%26promotionid%3D3" +
//                "&response_type=code&scope=snsapi_base&state=321#wechat_redirect";
        return "redirect:" + tourl;
    }

    @RequestMapping("auction_margin_pay.action")
    public String h5WeixinPay(Map<String, Object> data, HttpServletRequest request) {
        UserModel userModel = getAppUserInfo();
        String title = RequestTools.RequestString(request, "title", "未知商品");
        String ip = getRemoteHost(request);
        String payPrice = RequestTools.RequestString(request, "price", "0");
        String tradeNo = PubUtils.getUniqueSn() + "";
        String code = RequestTools.RequestString(request, "code", "");
        long aid = RequestTools.RequestLong(request, "aid", 0);
        AuctionModel auctionModel = auctionService.getAuctionById(aid);
        if (auctionModel.getDeposit() == Float.parseFloat(payPrice)) {
            JSONObject jsonObject = TenWeChatGenerator.getOpenIdStepOne(code);
            System.out.println(jsonObject);
            String openid = jsonObject.getString("openid");
            try {
                //生成预支付订单
                Map<String, Object> payMap = TenWeChatGenerator.genPayOrder("曲不离口-保证金-" + title, tradeNo, payPrice, openid, ip);
                int status = CONST.ORDER_PAY;
                OrderModel orderModel = payService.getOrderByUidAndAid(userModel.getId(), aid);
                //查询拍卖详情
                //AuctionModel auctionModel = auctionService.queryAuctionById(aid);
                if (orderModel == null) {
                    orderModel = new OrderModel();
                    float deposit = auctionModel.getDeposit();
                    orderModel.setAmount(deposit);
                    orderModel.setAtype(CONST.FOCUS_AUCTION);
                    orderModel.setAid(aid);
                    orderModel.setUid(userModel.getId());
                    orderModel.setPhone(userModel.getPhone());
                    orderModel.setAname("曲不离口-保证金-"+auctionModel.getTitle());
                    orderModel.setStatus(CONST.ORDER_PAY);
                    orderModel.setCreatetime(new Date());
                    orderModel.setOrderNo(tradeNo);
                    payService.createOrder(orderModel);//创建订单
                } else {
                    payService.updateOrder(orderModel.getId(), status);
                }

                data.putAll(payMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "H5Weixin";
    }

    public String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * 拍卖API
     *
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("mine_auction_list.action")
    @ResponseBody
    public String mine_auction_list(Map<String, Object> data, HttpServletRequest request) {
        int status = RequestTools.RequestInt(request, "status", 0);
        int page = RequestTools.RequestInt(request, "page", 1);
        int size = RequestTools.RequestInt(request, "size", Integer.MAX_VALUE);
        PageModel pageModel = new PageModel();
        pageModel.setPage(page);
        pageModel.setSize(size);
        UserModel userModel = getAppUserInfo();
        PageResult<AuctionRecordsModel> list = auctionService.queryAuctionRecordsByUserModel(userModel.getId(), status, pageModel);
        List<AuctionRecordsModel> auctionRecordsModels = list.getData();
        if (auctionRecordsModels != null && auctionRecordsModels.size() > 0) {
            return JsonTools.jsonSer(auctionRecordsModels);
        } else {
            return "";
        }
    }
}
