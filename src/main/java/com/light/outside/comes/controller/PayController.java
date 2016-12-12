package com.light.outside.comes.controller;

import com.light.outside.comes.controller.pay.TenWeChatGenerator;
import com.light.outside.comes.controller.pay.util.PubUtils;
import com.light.outside.comes.controller.pay.util.XMLUtil;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.PayService;
import com.light.outside.comes.service.WeiXinPayService;
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
@RequestMapping("yeshizuileweixin/Cart")
public class PayController extends BaseController {

    @Autowired
    private WeiXinPayService weiXinPayService;

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
     * @param data
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("weChatPayCallback.action")
    public void weChatPayCallback(Map<String, Object> data, HttpServletRequest request,HttpServletResponse response) throws Exception{
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
            Map<String,String> resuletMap=TenWeChatGenerator.orderQuery(out_trade_no);

            //weChatPayService.updatePayOrder(out_trade_no, transaction_id);
            payService.updateOrderByOrderno(out_trade_no, transaction_id);
            retMap.put("return_code", "SUCCESS");
        } else {
            retMap.put("return_code", "FAIL");
        }
        response.getWriter().write(XMLUtil.toXml(retMap));
    }

}
