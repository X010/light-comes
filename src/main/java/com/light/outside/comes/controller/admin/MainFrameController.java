package com.light.outside.comes.controller.admin;

import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.service.admin.MainFrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("admin/")
public class MainFrameController {

    @Autowired
    private MainFrameService mainFrameService;

    /**
     * 登陆
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("login.action")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "admin/login";
    }


    /**
     * 主体
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("mainframe.action")
    public String mainframe(HttpServletRequest request, HttpServletResponse response) {
        return "admin/mainframe";
    }

    /**
     * 创建 奖 动
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("create_raffle.action")
    public String create_raffle(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        return "admin/create_raffle";
    }


    /**
     * 奖 动列表
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("raffle_list.action")
    public String raffle_list(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response, PageModel pageModel) {
        return "admin/raffle_list";
    }

    /**
     * 奖 动 表
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("raffle_report.action")
    public String reffle_report(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response, PageModel pageModel) {
        return "admin/raffle_report";
    }


    /**
     * 创建砍价商品
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("create_overcharge.action")
    public String create_overcharge(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        return "admin/create_overcharge";
    }

    /**
     * 确价商品列表
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("overcharge_list.action")
    public String overcharge_list(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response, PageModel pageModel) {
        return "admin/overcharge_list";
    }


    /**
     * 创建 卖
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("create_auction.action")
    public String create_auction(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        return "admin/create_auction";
    }


    /**
     * 卖列表
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("auction_list.action")
    public String auction_list(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response, PageModel pageModel) {
        return "admin/auction_list";
    }


    /**
     * 创建饭局
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("create_banquet.action")
    public String create_banquet(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        return "admin/create_banquet";
    }


    /**
     * 饭局列表
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("banquet_list.action")
    public String banquet_list(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response, PageModel pageModel) {
        return "admin/banquet_list";
    }


    /**
     * 创建劵
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("create_coupon.action")
    public String create_coupon(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        return "admin/create_coupon";
    }


    /**
     * 劵列表
     * @param data
     * @param request
     * @param response
     * @param pageModel
     * @return
     */
    @RequestMapping("coupon_list.action")
    public String coupon_list(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response, PageModel pageModel) {
        return "admin/coupon_list";
    }
}
