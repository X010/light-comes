package com.light.outside.comes.controller;

import com.light.outside.comes.controller.admin.LoginController;
import com.light.outside.comes.controller.pay.config.TenWeChatConfig;
import com.light.outside.comes.model.*;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.BanquetService;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.utils.*;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("banquet")
public class BanquetController extends BaseController {

    @Autowired
    private BanquetService banquetService;

    @Autowired
    private FocusImageService focusImageService;

    @RequestMapping("banquet.action")
    public String banquet(Map<String, Object> data, HttpServletRequest request) {
        //输出焦点图
        List<FocusImageModel> focusImageModelList = this.focusImageService.queryFocusImageByColumn(CONST.FOCUS_BANQUET);
        if (focusImageModelList != null) {
            data.put("focus", focusImageModelList);
        }
        PageModel pageModel = new PageModel();
        pageModel.setPage(1);
        PageResult<BanquetModel> banquets = banquetService.getBanquets(pageModel);
        List<BanquetModel> banquetModels = banquets.getData();
        if (banquetModels != null) {
            data.put("bs", banquetModels);
        }
        return "banquet";
    }

    /**
     * 约饭分页接口
     *
     * @param data
     * @param pageModel
     * @return
     */
    @ResponseBody
    @RequestMapping("banquet_list.action")
    public String banquet_list(Map<String, Object> data, PageModel pageModel) {
        PageResult<BanquetModel> banquetModelPageResult = this.banquetService.getBanquets(pageModel);
        List<BanquetModel> banquetModels = banquetModelPageResult.getData();
        if (banquetModels != null && banquetModels.size() > 0) {
            return JsonTools.jsonSer(banquetModels);
        } else {
            return "";
        }
    }


    @RequestMapping("banquet_d.action")
    public String banquet_d(Map<String, Object> data, HttpServletRequest request, @RequestParam("aid") long aid) {
        BanquetModel banquetModel = null;
        if (aid > 0) {
            banquetModel = this.banquetService.getBanquetById(aid);
        }
        if (banquetModel != null) {
            UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
            data.put("banquet", banquetModel);
            //秒数
            long seconds = DateUtils.endSeconds(banquetModel.getEnd_time());
            long start_seconds = DateUtils.endSeconds(banquetModel.getStart_time());
            banquetModel.setTime_second((int) seconds);
            data.put("seconds", seconds);
            data.put("start_seconds",start_seconds);
            //剩余坐席
            data.put("gapNum",banquetModel.getTotal_number()-banquetModel.getEnroll_num());

            //判断是否已经预约了饭局
            //boolean isJoin = this.banquetService.isJoinBanquet(userModel, aid);
             BanquetRecordModel banquetRecordModel=this.banquetService.getJoinBanquet(userModel,aid);
            boolean isJoin=false;
            if(banquetRecordModel!=null){
                isJoin=true;
            }
            data.put("isjoin", isJoin);
            data.put("record",banquetRecordModel);
            return "banquet_d";
        } else {
            return "redirect:/banquet/banquet.action";
        }
    }

    /**
     * 进行支付
     *
     * @param data
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("dopaybanquet.action")
    public String doPayBanquet(Map<String, Object> data, HttpServletRequest request) {
        String res = "";
        try {
            UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
            if (userModel != null) {
                long aid = Long.valueOf(request.getParameter("aid"));

                OrderModel orderModel = this.banquetService.payBanquet(aid, userModel,"");
                if (orderModel != null) {
                    res = JsonParser.simpleJson(orderModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("wechart_redirect.action")
    public String pay(Map<String, Object> data, HttpServletRequest request) {
        String payPrice = RequestTools.RequestString(request, "price", "0");
        long aid = RequestTools.RequestLong(request, "aid", 0);
//        String tourl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + TenWeChatConfig.app_id + "&redirect_uri=" +
//                "http%3A%2F%2Fwww.qubulikou.com%2Fqblk%2Fyeshizuileweixin%2Fpay%2Fbanquet_pay.action%3Fprice%3D" + payPrice + "%26aid%3D" + aid +
//                "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        String tourl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + TenWeChatConfig.app_id + "&redirect_uri=" +
                "http%3A%2F%2Fwww.qubulikou.com%2Fyeshizuileweixin%2FCart%2Fbanquet_pay.action%3Fprice%3D" + payPrice + "%26aid%3D" + aid +
                "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        return "redirect:" + tourl;
    }
    /**
     * 我的约饭数据
     */
    @RequestMapping("mine_banquest_list.action")
    @ResponseBody
    public String mine_banquert_list(Map<String, Object> data, HttpServletRequest request) {
        int status = RequestTools.RequestInt(request, "status", 0);
        int page = RequestTools.RequestInt(request, "page", 1);
        int size = RequestTools.RequestInt(request, "size", Integer.MAX_VALUE);
        PageModel pageModel = new PageModel();
        pageModel.setPage(page);
        pageModel.setSize(size);
        UserModel userModel = getAppUserInfo();
        PageResult<BanquetRecordViewModel> banquetRecordModelPageResult = banquetService.getBanquetRecordPage(userModel.getId(), status, pageModel);
        List<BanquetRecordViewModel> banquetRecordModels = banquetRecordModelPageResult.getData();
        if (banquetRecordModels != null && banquetRecordModels.size() > 0)
            return JsonTools.jsonSer(banquetRecordModelPageResult.getData());
        else
            return "";
    }

}
