package com.light.outside.comes.controller.admin;

import com.google.common.base.Strings;
import com.light.outside.comes.model.*;
import com.light.outside.comes.service.RaffleService;
import com.light.outside.comes.service.admin.MainFrameService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
@RequestMapping("admin/")
public class MainFrameController {

    @Autowired
    private MainFrameService mainFrameService;

    @Autowired
    private RaffleService raffleService;

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
        List<CouponModel> couponModels = this.raffleService.getCouponsByStatus(CONST.RAFFLE_STATUS_NORMAL);
        if (couponModels != null) {
            data.put("coupons", couponModels);
        }
        return "admin/create_raffle";
    }

    /**
     * 删除抽奖活动
     *
     * @param id
     * @return
     */
    @RequestMapping("delete_raffle.action")
    public String delete_raffle(@RequestParam("id") long id) {
        if (id > 0) {
            this.raffleService.deleteRaffle(id);
        }
        return "redirect:/admin/raffle_list.action";
    }


    /**
     * 保存抽奖
     *
     * @param raffleModel
     * @return
     */
    @RequestMapping(value = "save_raffle.action", method = RequestMethod.POST)
    public String save_raffle(RaffleModel raffleModel, HttpServletRequest request, @RequestParam("photo_up") MultipartFile file) {
        if (raffleModel != null && file != null) {
            String file_path = FileUtil.saveFile(file);

            List<RaffleCouponModel> raffleCouponModels = this.getRaffleCoupon(request);

            if (!Strings.isNullOrEmpty(file_path)) {
                raffleModel.setPhoto(file_path);
            }
            raffleModel.rangle_time();
            raffleModel.setStatus(CONST.RAFFLE_STATUS_INIT);
            raffleModel.setCreatetime(new Date());
            this.raffleService.save_raffle(raffleModel, raffleCouponModels);
        }
        return "redirect:/admin/raffle_list.action";
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
        PageResult<RaffleModel> raffleModelPageResult = this.raffleService.getRaffles(pageModel);
        if (raffleModelPageResult != null) {
            data.put("raffles", raffleModelPageResult);
        }
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
     * 保存劵
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("save_coupon.action")
    public String save_coupon(Map<String, Object> data, CouponModel couponModel, HttpServletRequest request, HttpServletResponse response) {
        if (couponModel != null) {
            couponModel.rangle_time();
            couponModel.setCreatetime(new Date());
            couponModel.setStatus(1);
            this.raffleService.addCoupon(couponModel);
        }
        return "redirect:/admin/coupon_list.action";
    }

    /**
     * 删除优惠劵
     *
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("delete_coupon.action")
    public String delete_coupon(@RequestParam("id") long id, HttpServletRequest request, HttpServletResponse response) {
        if (id > 0) {
            this.raffleService.deleteCoupon(id);
        }
        return "redirect:/admin/coupon_list.action";
    }

    /**
     * 劵列表
     *
     * @param data
     * @param request
     * @param response
     * @param pageModel
     * @return
     */
    @RequestMapping("coupon_list.action")
    public String coupon_list(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response, PageModel pageModel) {
        PageResult<CouponModel> couponModelPageResult = this.raffleService.getCoupons(pageModel);
        if (couponModelPageResult != null) {
            data.put("coupons", couponModelPageResult);
        }
        return "admin/coupon_list";
    }


    private List<RaffleCouponModel> getRaffleCoupon(HttpServletRequest request) {
        //获取对应的设置列表
        int cid1 = Integer.valueOf(request.getParameter("cid_1"));
        int cid_rate1 = Integer.valueOf(request.getParameter("cid_rate_1"));

        int cid2 = Integer.valueOf(request.getParameter("cid_2"));
        int cid_rate2 = Integer.valueOf(request.getParameter("cid_rate_2"));

        int cid3 = Integer.valueOf(request.getParameter("cid_3"));
        int cid_rate3 = Integer.valueOf(request.getParameter("cid_rate_3"));

        int cid4 = Integer.valueOf(request.getParameter("cid_4"));
        int cid_rate4 = Integer.valueOf(request.getParameter("cid_rate_4"));

        int cid5 = Integer.valueOf(request.getParameter("cid_5"));
        int cid_rate5 = Integer.valueOf(request.getParameter("cid_rate_5"));

        int cid6 = Integer.valueOf(request.getParameter("cid_6"));
        int cid_rate6 = Integer.valueOf(request.getParameter("cid_rate_6"));

        int cid7 = Integer.valueOf(request.getParameter("cid_7"));
        int cid_rate7 = Integer.valueOf(request.getParameter("cid_rate_7"));

        int cid8 = Integer.valueOf(request.getParameter("cid_8"));
        int cid_rate8 = Integer.valueOf(request.getParameter("cid_rate_8"));

        int cid9 = Integer.valueOf(request.getParameter("cid_9"));
        int cid_rate9 = Integer.valueOf(request.getParameter("cid_rate_9"));

        List<RaffleCouponModel> raffleCouponModels = new ArrayList<RaffleCouponModel>();

        RaffleCouponModel raffleCouponModel1 = new RaffleCouponModel();
        raffleCouponModel1.setCid(cid1);
        raffleCouponModel1.setWinrate(cid_rate1);
        raffleCouponModel1.setCindex(1);
        raffleCouponModels.add(raffleCouponModel1);

        RaffleCouponModel raffleCouponModel2 = new RaffleCouponModel();
        raffleCouponModel2.setCid(cid2);
        raffleCouponModel2.setWinrate(cid_rate2);
        raffleCouponModel2.setCindex(2);
        raffleCouponModels.add(raffleCouponModel2);

        RaffleCouponModel raffleCouponModel3 = new RaffleCouponModel();
        raffleCouponModel3.setCid(cid3);
        raffleCouponModel3.setWinrate(cid_rate3);
        raffleCouponModel3.setCindex(3);
        raffleCouponModels.add(raffleCouponModel3);

        RaffleCouponModel raffleCouponModel4 = new RaffleCouponModel();
        raffleCouponModel4.setCid(cid4);
        raffleCouponModel4.setWinrate(cid_rate4);
        raffleCouponModel4.setCindex(4);
        raffleCouponModels.add(raffleCouponModel4);

        RaffleCouponModel raffleCouponModel5 = new RaffleCouponModel();
        raffleCouponModel5.setCid(cid5);
        raffleCouponModel5.setWinrate(cid_rate5);
        raffleCouponModel5.setCindex(5);
        raffleCouponModels.add(raffleCouponModel5);

        RaffleCouponModel raffleCouponModel6 = new RaffleCouponModel();
        raffleCouponModel6.setCid(cid6);
        raffleCouponModel6.setWinrate(cid_rate6);
        raffleCouponModel6.setCindex(6);
        raffleCouponModels.add(raffleCouponModel6);

        RaffleCouponModel raffleCouponModel7 = new RaffleCouponModel();
        raffleCouponModel7.setCid(cid7);
        raffleCouponModel7.setWinrate(cid_rate7);
        raffleCouponModel7.setCindex(7);
        raffleCouponModels.add(raffleCouponModel7);

        RaffleCouponModel raffleCouponModel8 = new RaffleCouponModel();
        raffleCouponModel8.setCid(cid8);
        raffleCouponModel8.setWinrate(cid_rate8);
        raffleCouponModel8.setCindex(8);
        raffleCouponModels.add(raffleCouponModel8);

        RaffleCouponModel raffleCouponModel9 = new RaffleCouponModel();
        raffleCouponModel9.setCid(cid9);
        raffleCouponModel9.setWinrate(cid_rate9);
        raffleCouponModel9.setCindex(9);
        raffleCouponModels.add(raffleCouponModel9);

        return raffleCouponModels;
    }
}
