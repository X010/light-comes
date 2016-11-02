package com.light.outside.comes.controller;

import com.light.outside.comes.controller.admin.LoginController;
import com.light.outside.comes.model.BanquetModel;
import com.light.outside.comes.model.OrderModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.BanquetService;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.DateUtils;
import com.light.outside.comes.utils.JsonParser;
import com.light.outside.comes.utils.JsonTools;
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
@RequestMapping("banquet")
public class BanquetController {

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
            banquetModel.setTime_second((int) seconds);
            data.put("seconds", seconds);

            //判断是否已经预约了饭局
            boolean isJoin = this.banquetService.isJoinBanquet(userModel, aid);
            data.put("isjoin", isJoin);
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

                OrderModel orderModel = this.banquetService.payBanquet(aid, userModel);
                if (orderModel != null) {
                    res = JsonParser.simpleJson(orderModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
