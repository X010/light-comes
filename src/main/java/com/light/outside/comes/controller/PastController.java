package com.light.outside.comes.controller;

import com.google.common.base.Strings;
import com.light.outside.comes.controller.pay.TenWeChatGenerator;
import com.light.outside.comes.model.JsonResponse;
import com.light.outside.comes.model.PastModel;
import com.light.outside.comes.model.PastTotal;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.qbkl.service.QblkService;
import com.light.outside.comes.service.PastService;
import com.light.outside.comes.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("pt")
public class PastController extends BaseController {

    @Autowired
    private PastService pastService;

    @Autowired
    private QblkService qblkService;

    /**
     * 签到页面
     *
     * @return
     */
    @RequestMapping("past.action")
    public String past(Map<String,Object> data,HttpServletRequest request) {
        UserModel userModel=getAppUserInfo();
        String url="http://www.qubulikou.com/qblk/pt/past.action";
        String queryString=request.getQueryString();
        if(!Strings.isNullOrEmpty(queryString)){
            url=url+"?"+queryString;
        }
        data.putAll(TenWeChatGenerator.getWxConfig(url));
        PastModel pastModel=pastService.getPastModelById();
        data.put("phone",userModel.getPhone());
        data.put("pt",pastModel);
        return "past";
    }

    /**
     * 分享页面
     *
     * @return
     */
    @RequestMapping("share.action")
    public String share(@RequestParam("phone") String phone,Map<String,Object> data,HttpServletRequest request) {
        String url="http://www.qubulikou.com/qblk/pt/share.action";
        String queryString=request.getQueryString();
        if(!Strings.isNullOrEmpty(queryString)){
            url=url+"?"+queryString;
        }
        data.putAll(TenWeChatGenerator.getWxConfig(url));
        return "share";
    }

    /**
     * 获取自己签到的情况
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("info.action")
    public String getMyPastInfo(HttpServletRequest request) {
        JsonResponse<PastTotal> data = new JsonResponse<PastTotal>(200);
        try {
            UserModel user = getAppUserInfo();
            if (user != null) {
                PastTotal pastTotal = this.pastService.getPastTotalByPhone(user);
                data.setData(pastTotal);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.setStatus(300);
        }
        return JsonParser.simpleJson(data);
    }

    /**
     * 自已给自己干怀
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("self_past.action")
    public String selfPast() {
        JsonResponse<PastTotal> data = new JsonResponse<PastTotal>(200);
        try {
            UserModel user = getAppUserInfo();
            PastTotal pastTotal = this.pastService.pastSelf(user);
            data.setData(pastTotal);
        } catch (Exception e) {
            e.printStackTrace();
            data.setStatus(300);
        }
        return JsonParser.simpleJson(data);
    }

    @ResponseBody
    @RequestMapping("other_info.action")
    public String otherInfo(@RequestParam("phone") String phone) {
        JsonResponse<PastTotal> data = new JsonResponse<PastTotal>(200);
        try {
            UserModel user = this.qblkService.getUserByPhone(phone);
            if (user != null) {
                PastTotal pastTotal = this.pastService.getPastTotalByPhone(user);
                data.setData(pastTotal);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.setStatus(300);
        }
        return JsonParser.simpleJson(data);
    }

    @ResponseBody
    @RequestMapping("other_past.action")
    public String otherPast(@RequestParam("phone") String phone) {
        JsonResponse<PastTotal> data = new JsonResponse<PastTotal>(200);
        try {
            UserModel user = getAppUserInfo();
            PastTotal pastTotal = this.pastService.otherPast(user, phone);
            data.setData(pastTotal);
        } catch (Exception e) {
            e.printStackTrace();
            data.setStatus(300);
        }
        return JsonParser.simpleJson(data);
    }
}
