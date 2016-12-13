package com.light.outside.comes.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.light.outside.comes.controller.pay.TenWeChatGenerator;
import com.light.outside.comes.controller.pay.util.PubUtils;
import com.light.outside.comes.controller.pay.util.XMLUtil;
import com.light.outside.comes.model.AuctionModel;
import com.light.outside.comes.model.BanquetModel;
import com.light.outside.comes.model.BanquetRecordModel;
import com.light.outside.comes.model.OrderModel;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.AuctionService;
import com.light.outside.comes.service.BanquetService;
import com.light.outside.comes.service.PayService;
import com.light.outside.comes.service.WeiXinPayService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.OrderUtil;
import com.light.outside.comes.utils.RequestTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
@RequestMapping("yeshizuileweixin/pay")
public class PayController extends BaseController {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private BanquetService banquetService;

    @Autowired
    private PayService payService;

    private static final Logger LOG = LoggerFactory.getLogger(PayController.class);


    /**
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("h5weixin.action")
    public String h5WeixinPay(Map<String, Object> data, HttpServletRequest request) {

        UserModel userModel = getAppUserInfo();
        String title = RequestTools.RequestString(request, "title", "未知商品");
        String ip = request.getRemoteHost();
        String payPrice = RequestTools.RequestString(request, "price", "0");
        String tradeNo = PubUtils.getUniqueSn() + "";
        String openid = userModel.getPhone();
        try {
            //生成预支付订单
//            Map<String, Object> payMap = TenWeChatGenerator.genPayOrder(title, tradeNo, payPrice, openid, ip);
//            data.putAll(payMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "H5Weixin";
    }

    /**
     * 支付成功回调函数
     *
     * @param data
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("weChatPayCallback.action")
    public void weChatPayCallback(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        BufferedReader buff = null;
        StringBuffer str = null;
        str = new StringBuffer();
        String s = null;
        buff = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        while ((s = buff.readLine()) != null) {
            str.append(s);
        }
        String xmlBody = str.toString();
        Map map = XMLUtil.doXMLParse(xmlBody);
        SortedMap<String, String> retMap = new TreeMap<String, String>();
        if ("SUCCESS".equals(map.get("return_code"))) {
            String out_trade_no = (String) map.get("out_trade_no");
            String transaction_id = (String) map.get("transaction_id");
            //查询订单状态
            Map<String, String> resuletMap = TenWeChatGenerator.orderQuery(transaction_id);
            LOG.info("order status:" + JsonTools.jsonSer(resuletMap));
            OrderModel orderModel = payService.getOrderByTradeno(out_trade_no);
            if (orderModel != null) {
                //更新支付状态
                if (orderModel.getStatus() != CONST.ORDER_PAY) {
                    orderModel=payService.updateOrderByOrderno(orderModel.getOrderNo(), out_trade_no, transaction_id, CONST.ORDER_PAY);
                    LOG.info("update order status to payed by order number:" + out_trade_no);
                    if (orderModel.getAtype() == CONST.FOCUS_BANQUET) {
                        banquetService.upateBanquetRecordStatusByOrder(orderModel);
                        LOG.info("update banquet record status by order :" + orderModel.getId());
                    }
                }
            } else {
                LOG.info("order is null");
            }
            retMap.put("return_code", "SUCCESS");
        } else {
            retMap.put("return_code", "FAIL");

        }
        response.getWriter().write(XMLUtil.toXml(retMap));
    }

    /**
     * 拍卖保证金支付
     *
     * @param data
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("auction_margin_pay.action")
    public String weChatPayAuction(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = "http://www.qubulikou.com/yeshizuileweixin/pay/auction_margin_pay.action";
        String queryString = request.getQueryString();
        if (!Strings.isNullOrEmpty(queryString)) {
            url = url + "?" + queryString;
        }
        UserModel userModel = getAppUserInfo();
        //String title = RequestTools.RequestString(request, "title", "曲不离口-拍卖保证金");
        String ip = getRemoteHost(request);
        //TODO
        String payPrice = "0.01";
        //String payPrice = RequestTools.RequestString(request, "price", "0");
        String tradeNo = PubUtils.getUniqueSn() + "";
        String code = RequestTools.RequestString(request, "code", "");
        //System.out.println("code:" + code + " ip:" + ip);
        long aid = RequestTools.RequestLong(request, "aid", 0);
        //查询拍卖详情
        AuctionModel auctionModel = auctionService.getAuctionById(aid);
        //TODO 测试完后放开
        //if (auctionModel.getDeposit() == Float.parseFloat(payPrice)) {
        JSONObject jsonObject = TenWeChatGenerator.getOpenIdStepOne(code);
        String openid = jsonObject.getString("openid");
        String title = "曲不离口-保证金-" + auctionModel.getTitle();
        try {
            //生成预支付订单
            OrderModel orderModel = payService.getOrderByUidAndAid(userModel.getId(), aid, CONST.FOCUS_AUCTION);
            if (orderModel != null) {
                tradeNo = orderModel.getTradeno();
            }
            Map<String, Object> payMap = TenWeChatGenerator.genPayOrder(url, title, tradeNo, payPrice, openid, ip);
            LOG.info("appId:" + payMap.get("appId"));
            if (orderModel == null) {
                LOG.info("order is null careate order ");
                orderModel = new OrderModel();
                float deposit = auctionModel.getDeposit();
                orderModel.setAmount(deposit);
                orderModel.setAtype(CONST.FOCUS_AUCTION);
                orderModel.setAid(aid);
                orderModel.setUid(userModel.getId());
                orderModel.setPhone(userModel.getPhone());
                orderModel.setAname(title);
                orderModel.setStatus(CONST.ORDER_CREATE);
                orderModel.setCreatetime(new Date());
                orderModel.setPtype(CONST.PAY_WEIXIN);
                orderModel.setOrderNo(OrderUtil.getOrderNo());
                orderModel.setTradeno(tradeNo);
                payService.createOrder(orderModel);//创建订单
            }
            data.putAll(payMap);
            data.put("redirectUrl", "http://www.qubulikou.com/qblk/auction/auction_d.action?aid=" + aid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
        return "H5Weixin";
    }


    /**
     * 约饭支付
     *
     * @param data
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("banquet_pay.action")
    public String weChatPayBanquet(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = "http://www.qubulikou.com/yeshizuileweixin/pay/banquet_pay.action";
        String queryString = request.getQueryString();
        if (!Strings.isNullOrEmpty(queryString)) {
            url = url + "?" + queryString;
        }
        UserModel userModel = getAppUserInfo();
        String ip = getRemoteHost(request);
        //TODO
        String payPrice = "0.01";
        //String payPrice = RequestTools.RequestString(request, "price", "0");
        String tradeNo = PubUtils.getUniqueSn() + "";
        String code = RequestTools.RequestString(request, "code", "");
        System.out.println("code:" + code + " ip:" + ip);
        long aid = RequestTools.RequestLong(request, "aid", 0);
        //查询约饭详情
        BanquetModel banquetModel = banquetService.getBanquetById(aid);
        //TODO 测试完后放开
        //if (auctionModel.getDeposit() == Float.parseFloat(payPrice)) {
        JSONObject jsonObject = TenWeChatGenerator.getOpenIdStepOne(code);
        String openid = jsonObject.getString("openid");
        try {
            String wechatTitle = "曲不离口-约饭-" + banquetModel.getTitle();
            OrderModel orderModel = payService.getOrderByUidAndAid(userModel.getId(), aid, CONST.FOCUS_BANQUET);
            if (orderModel != null) {
                tradeNo = orderModel.getTradeno();
            }
            //生成预支付订单
            Map<String, Object> payMap = TenWeChatGenerator.genPayOrder(url, wechatTitle, tradeNo, payPrice, openid, ip);
            if (orderModel == null) {
                banquetService.payBanquet(aid, userModel, tradeNo);
//                orderModel = new OrderModel();
//                float amount = banquetModel.getAmount();
//                orderModel.setAmount(amount);
//                orderModel.setAname(wechatTitle);
//                orderModel.setAtype(CONST.FOCUS_BANQUET);
//                orderModel.setCreatetime(new Date());
//                orderModel.setPhone(userModel.getPhone());
//                orderModel.setUid(userModel.getId());
//                orderModel.setStatus(CONST.ORDER_CREATE);
//                orderModel.setPtype(CONST.PAY_WEIXIN);
//                orderModel.setOrderNo(OrderUtil.getOrderNo());
//                orderModel.setTradeno(tradeNo);
//                orderModel.setPaytime(new Date());
//                orderModel.setAid(aid);
//                payService.createOrder(orderModel);//创建订单
//
            }
            data.putAll(payMap);
            data.put("redirectUrl", "http://www.qubulikou.com/qblk/banquet/banquet_d.action?aid=" + aid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
        return "H5Weixin";
    }


}
