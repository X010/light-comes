package com.light.outside.comes.controller;

import com.light.outside.comes.model.JsonResponse;
import com.light.outside.comes.model.PastTotal;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.PastService;
import com.light.outside.comes.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 签到页面
     *
     * @return
     */
    @RequestMapping("past.action")
    public String past() {
        return "past";
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
