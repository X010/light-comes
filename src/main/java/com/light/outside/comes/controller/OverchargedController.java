package com.light.outside.comes.controller;

import com.light.outside.comes.controller.admin.LoginController;
import com.light.outside.comes.model.OverchargedModel;
import com.light.outside.comes.model.OverchargedRecordModel;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.model.admin.FocusImageModel;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.OverchargedService;
import com.light.outside.comes.service.admin.FocusImageService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.DateUtils;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
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
@RequestMapping("oc")
public class OverchargedController extends BaseController{

    @Autowired
    private OverchargedService overchargedService;

    @Autowired
    private FocusImageService focusImageService;


    /**
     * 砍价List
     *
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("overcharged.action")
    public String overcharged(Map<String, Object> data, HttpServletRequest request) {
        //输出焦点图
        List<FocusImageModel> focusImageModelList = this.focusImageService.queryFocusImageByColumn(CONST.FOCUS_OVERCHARGER);
        if (focusImageModelList != null) {
            data.put("focus", focusImageModelList);
        }
        PageModel pageModel = new PageModel();
        pageModel.setPage(1);
        PageResult<OverchargedModel> auctionModelPageResult = overchargedService.getOverchargeds(pageModel);
        List<OverchargedModel> overchargedModels = auctionModelPageResult.getData();
        if (overchargedModels != null) {
            data.put("oc", overchargedModels);
        }
        return "overcharged";
    }

    /**
     * 前端分页获取接口
     *
     * @param data
     * @param pageModel
     * @return
     */
    @ResponseBody
    @RequestMapping("overcharged_list.action")
    public String overcharged_list(Map<String, Object> data, PageModel pageModel) {
        PageResult<OverchargedModel> overchargedModelPageResult = this.overchargedService.getOverchargeds(pageModel);
        List<OverchargedModel> overchargedModels = overchargedModelPageResult.getData();
        if (overchargedModels != null && overchargedModels.size() > 0) {
            return JsonTools.jsonSer(overchargedModels);
        } else {
            return "";
        }
    }

    /**
     * 砍价说情页面
     *
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("overcharged_d.action")
    public String overcharged_d(Map<String, Object> data, HttpServletRequest request, @RequestParam("aid") long aid) {
        try {
            if (aid > 0) {
                //输出基本信息
                UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
                OverchargedModel overchargedModel = this.overchargedService.getOverchargedModel(aid);
                if (overchargedModel != null) {
                    long seconds = DateUtils.endSeconds(overchargedModel.getEnd_time());

                    overchargedModel.setTime_second((int) seconds);
                    data.put("seconds", seconds);
                    data.put("oc", overchargedModel);


                    //获取该用户是否已经砍过价
                    boolean isJoin = this.overchargedService.isJoinOvercharged(aid, userModel.getPhone());
                    data.put("join", isJoin);

                    //获取砍价清单
                    List<OverchargedRecordModel> orms = this.overchargedService.getOverchargedRecords(aid);
                    if (orms != null) {
                        data.put("orms", orms);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "overcharged_d";
    }


    @ResponseBody
    @RequestMapping("send_overcharged.action")
    public String send_overcharged(Map<String, Object> data, HttpServletRequest request, @RequestParam("aid") long aid) {
        String res = "";
        if (aid > 0) {
            try {
                UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
                if (userModel != null) {
                    OverchargedRecordModel orm = this.overchargedService.overchargedRecordModel(aid, userModel);
                    res = JsonTools.jsonSer(orm);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 我的砍价记录
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("mine_overcharged_list.action")
    @ResponseBody
    public String mine_overcharged_list(Map<String, Object> data, HttpServletRequest request) {
        int status = RequestTools.RequestInt(request, "status", 0);
        int page = RequestTools.RequestInt(request, "page", 1);
        int size = RequestTools.RequestInt(request, "size", Integer.MAX_VALUE);
        PageModel pageModel = new PageModel();
        pageModel.setPage(page);
        pageModel.setSize(size);
        UserModel userModel = getAppUserInfo();
        PageResult<OverchargedRecordModel> banquetRecordModelPageResult = this.overchargedService.getOverchargedRecordPage(userModel.getId(), status, pageModel);
        return JsonTools.jsonSer(banquetRecordModelPageResult.getData());
    }
}
