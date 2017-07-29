package com.light.outside.comes.controller.admin;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.light.outside.comes.model.*;
import com.light.outside.comes.model.admin.UsersModel;
import com.light.outside.comes.qbkl.model.Commodity;
import com.light.outside.comes.qbkl.model.CommodityCategory;
import com.light.outside.comes.qbkl.service.QblkService;
import com.light.outside.comes.service.*;
import com.light.outside.comes.service.admin.LoginService;
import com.light.outside.comes.service.admin.MainFrameService;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.FileUtil;
import com.light.outside.comes.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    @Value("${baseUrl}")
    private String baseUrl;

    @Autowired
    private MainFrameService mainFrameService;

    @Autowired
    private RaffleService raffleService;

    @Autowired
    private BanquetService banquetService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private OverchargedService overchargedService;

    @Autowired
    private BackListService backListService;

    @Autowired
    private QblkService qblkService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private PastService pastService;

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
     * 焦点图管理
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("focus_manage.action")
    public String focus_manage(HttpServletRequest request, HttpServletResponse response) {
        return "admin/focus_manage";
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
     * 优惠劵详情
     *
     * @param data
     * @param pageModel
     * @param id
     * @return
     */
    @RequestMapping("coupon_list_detail.action")
    public String coupon_list_detail(Map<String, Object> data, PageModel pageModel, @RequestParam("id") Long id) {
        if (id != null && id > 0) {
            PageResult<CouponRecordModel> couponRecordModelPageResult = this.raffleService.getCouponRecordModelByAid(id, pageModel);
            if (couponRecordModelPageResult != null) {
                data.put("crs", couponRecordModelPageResult);
            }
            data.put("aid", id);
        }
        return "admin/coupon_list_detail";
    }


    /**
     * 拍卖活动详情
     *
     * @param data
     * @param pageModel
     * @return
     */
    @RequestMapping("auction_list_detail.action")
    public String auction_list_detail(Map<String, Object> data, PageModel pageModel, @RequestParam("id") Long id) {
        if (id != null && id > 0) {
            PageResult<AuctionRecordsModel> auctionRecordsModelPageResult = this.auctionService.getAuctionRecordsByAid(id, pageModel);
            if (auctionRecordsModelPageResult != null) {
                data.put("ars", auctionRecordsModelPageResult);
            }
            data.put("aid", id);
        }
        return "admin/auction_list_detail";
    }

    /**
     * 抽奖活动详情
     *
     * @param data
     * @param pageModel
     * @param id
     * @return
     */
    @RequestMapping("raffle_list_detail.action")
    public String raffle_list_detail(Map<String, Object> data, PageModel pageModel, @RequestParam("id") Long id) {

        return "admin/raffle_list_detail";
    }

    /**
     * 约饭详情列表
     *
     * @param data
     * @param pageModel
     * @param id
     * @return
     */
    @RequestMapping("banquet_list_detail.action")
    public String banquet_list_detail(Map<String, Object> data, PageModel pageModel, @RequestParam("id") Long id) {
        if (id != null && id > 0) {
            PageResult<BanquetRecordModel> banquetRecordModelPageResult = this.banquetService.getBanquetRecordPageByAid(id, pageModel);

            if (banquetRecordModelPageResult != null) {
                data.put("brs", banquetRecordModelPageResult);
            }
            data.put("aid", id);
        }
        return "admin/banquet_list_detail";
    }

    /**
     * 砍价活动详情
     *
     * @param data
     * @param pageModel
     * @param id
     * @return
     */
    @RequestMapping("overcharged_list_detail.action")
    public String overcharged_list_detail(Map<String, Object> data, PageModel pageModel, @RequestParam("id") Long id) {
        if (id != null && id > 0) {
            PageResult<OverchargedRecordModel> overchargedRecordModelPageResult = this.overchargedService.getOverchargedRecordByAid(id, pageModel);

            if (overchargedRecordModelPageResult != null) {
                data.put("ors", overchargedRecordModelPageResult);
            }
            data.put("aid", id);
        }
        return "admin/overcharged_list_detail";
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
    public String create_raffle(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("action") String action, @RequestParam("id") long id) {
        List<CouponModel> couponModels = this.raffleService.getCouponsByStatus(CONST.RAFFLE_STATUS_NORMAL);

        if (!Strings.isNullOrEmpty(action) && CONST.EDIT.equalsIgnoreCase(action)) {
            //修改状态
            data.put("action", action);
            data.put("editid", id);
            //加载数据
            RaffleModel raffleModel = this.raffleService.getRaffleById(id);
            if (raffleModel != null) {
                data.put("raffle", raffleModel);
                data.put("raffle_str", JsonParser.simpleJson(raffleModel.getRaffleCouponModels()));
            }
        }

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
        return "redirect:raffle_list.action";
    }

    /**
     * 保存抽奖
     *
     * @param raffleModel
     * @return
     */
    @RequestMapping(value = "save_raffle.action", method = RequestMethod.POST)
    public String save_raffle(RaffleModel raffleModel, HttpServletRequest request, @RequestParam("photo_up") MultipartFile file) {
        String editid = request.getParameter("editid");
        if (!Strings.isNullOrEmpty(editid)) {
            raffleModel.setId(Long.valueOf(editid));
        }
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
        return "redirect:raffle_list.action";
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
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (!Strings.isNullOrEmpty(action) && CONST.EDIT.equalsIgnoreCase(action) && !Strings.isNullOrEmpty(id)) {
            OverchargedModel overchargedModel = this.overchargedService.getOverchargedModel(Long.valueOf(id));
            if (overchargedModel != null) {
                data.put("overcharged", overchargedModel);
            }
        }
        return "admin/create_overcharge";
    }


    /**
     * 保存数据
     *
     * @param request
     * @param response
     * @param overchargedModel
     * @return
     */
    @RequestMapping("save_overcharge.action")
    public String save_overchage(HttpServletRequest request, HttpServletResponse response, OverchargedModel overchargedModel,@RequestParam(value="share_photo_file",required = false) MultipartFile share_file) {
        if (overchargedModel != null) {
            overchargedModel.rangle_time();
            overchargedModel.setCreate_time(new Date());
            overchargedModel.setStatus(CONST.RAFFLE_STATUS_NORMAL);
            overchargedModel.setRemain_count(overchargedModel.getInventory());//剩余库存初始化为总库存
            String share_file_path=FileUtil.saveFile(share_file);
            String oldSharePhoto=request.getParameter("old_share_photo");
            if(!Strings.isNullOrEmpty(share_file_path)){
                overchargedModel.setShare_photo(share_file_path);
            }else if(!Strings.isNullOrEmpty(oldSharePhoto)){
                overchargedModel.setShare_photo(oldSharePhoto);
            }
            String editid = request.getParameter("editid");
            if (!Strings.isNullOrEmpty(editid)) {
                overchargedModel.setId(Long.valueOf(editid));
                this.overchargedService.updateOvercharged(overchargedModel);
            } else {
                this.overchargedService.addOverChage(overchargedModel);
            }
        }
        return "redirect:overcharge_list.action";
    }


    /**
     * 删除Overcharged
     *
     * @param id
     * @return
     */
    @RequestMapping("delete_overcharged.action")
    public String delete_overcharged(@RequestParam("id") long id) {
        if (id > 0) {
            try {
                this.overchargedService.deleteOvercharged(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:overcharge_list.action";
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
        PageResult<OverchargedModel> overchargedModelPageResult = this.overchargedService.getOverchargedsMoreInfo(pageModel);
        if (overchargedModelPageResult != null) {
            data.put("overchargeds", overchargedModelPageResult);
        }
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
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (!Strings.isNullOrEmpty(action) && CONST.EDIT.equalsIgnoreCase(action) && !Strings.isNullOrEmpty(id)) {
            AuctionModel auctionModel = this.auctionService.getAuctionById(Long.valueOf(id));
            if (auctionModel != null) {
                data.put("auction", auctionModel);
            }
        }
        return "admin/create_auction";
    }


    /**
     * 保存拍卖
     *
     * @param auctionModel
     * @return
     */
    @RequestMapping("save_auction.action")
    public String save_auction(AuctionModel auctionModel, HttpServletRequest request) {
        String editId = request.getParameter("editid");
        auctionModel.rangle_time();
        auctionModel.setCreate_time(new Date());
        auctionModel.setStatus(CONST.RAFFLE_STATUS_NORMAL);
        if (!Strings.isNullOrEmpty(editId)) {
            //修改
            auctionModel.setId(Long.valueOf(editId));
            this.auctionService.updateAuction(auctionModel);
        } else {
            //新建
            if (auctionModel != null) {
                this.auctionService.addAuction(auctionModel);
            }
        }
        return "redirect:auction_list.action";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("delete_auction.action")
    public String delete_auction(@RequestParam("id") long id) {
        try {
            this.auctionService.deleteAuction(Long.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:auction_list.action";
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
        PageResult<AuctionModel> auctionModelPageResult = this.auctionService.getAuctionsMoreInfo(pageModel);
        if (auctionModelPageResult != null) {
            data.put("auctions", auctionModelPageResult);
        }
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
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (!Strings.isNullOrEmpty(action) && CONST.EDIT.equalsIgnoreCase(action) && !Strings.isNullOrEmpty(id)) {
            BanquetModel banquetModel = this.banquetService.getBanquetById(Long.valueOf(id));
            if (banquetModel != null) {
                data.put("banquet", banquetModel);
            }
        }
        return "admin/create_banquet";
    }

    @RequestMapping("t.action")
    public String test(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        return "admin/t";
    }
    @RequestMapping("tt.action")
    public String testRedirect(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        return test(data,request,response);
    }

    /**
     * 保存饭局数据
     *
     * @return
     */
    @RequestMapping("save_banquet.action")
    public String save_banquet(BanquetModel banquetModel, HttpServletRequest request, HttpServletResponse response, @RequestParam("photo_up") MultipartFile file) {
        if (banquetModel != null) {
            banquetModel.rangle_time();
            banquetModel.setCreate_time(new Date());
            banquetModel.setStatus(CONST.RAFFLE_STATUS_NORMAL);

            String file_path = null;
            if (file != null) {
                file_path = FileUtil.saveFile(file);
            }

            if (!Strings.isNullOrEmpty(file_path)) {
                banquetModel.setPhoto(file_path);
            }

            String id = request.getParameter("editid");

            if (!Strings.isNullOrEmpty(id)) {
                banquetModel.setId(Long.valueOf(id));
                this.banquetService.updateBanquet(banquetModel);
            } else {
                this.banquetService.addBanquet(banquetModel);
            }
        }
        return "redirect:banquet_list.action";
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
        PageResult<BanquetModel> banquetModelPageResult = this.banquetService.getBanquetsMoreInfo(pageModel);

        if (banquetModelPageResult != null) {
            data.put("banquets", banquetModelPageResult);
        }

        return "admin/banquet_list";
    }

    /**
     * 退款
     * @param data
     * @param request
     * @param response
     * @param pageModel
     * @return
     */
    @RequestMapping("banquet_refund.action")
    public String baquet_refund(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response, PageModel pageModel){
        String id = request.getParameter("id");
        String aid=request.getParameter("aid");
        boolean isSucces=this.banquetService.banquetRefund(Long.parseLong(id));
        data.put("isSuccess",isSucces);
        return "redirect:banquet_list_detail.action?id="+aid;
    }

    /**
     * 删除饭局
     *
     * @param id
     * @return
     */
    @RequestMapping("delete_banquet.action")
    public String delete_banquet(@RequestParam("id") long id) {
        if (id > 0) {
            try {
                this.banquetService.deleteBanquet(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:banquet_list.action";
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
        List<CommodityCategory> categories = this.qblkService.getParentCommodityCategory();

        if (categories != null && categories.size() > 0) {
            List<CommodityCategory> subCategories = this.qblkService.getCommodityCategoryByCategoryName(categories.get(0).getCategory1());
            data.put("categories", categories);
            if (subCategories != null) {
                data.put("subCategories", subCategories);
            }
        }
        return "admin/create_coupon";
    }


    /**
     * 生成优惠劵
     *
     * @param id
     * @return
     */
    @RequestMapping("produce_coupon.action")
    public String produce_coupon(@RequestParam("id") long id) {
        if (id > 0) {
            this.raffleService.generateCoupon(id);
        }
        return "redirect:coupon_list.action";
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

            //根据劵的类型获取数据
            switch (couponModel.getCtype()) {
                case 2:
                    String mid = request.getParameter("cate2");
                    if (!Strings.isNullOrEmpty(mid)) {
                        couponModel.setMid(Long.valueOf(mid));
                    }
                    break;

                case 3:
                    String goodsid = request.getParameter("goodsid");
                    if (!Strings.isNullOrEmpty(goodsid)) {
                        couponModel.setMid(Long.valueOf(goodsid));
                    }
                    break;
            }

            this.raffleService.addCoupon(couponModel);
        }
        return "redirect:coupon_list.action";
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
        return "redirect:coupon_list.action";
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
        PageResult<CouponModel> couponModelPageResult = this.raffleService.getCouponsMoreInfo(pageModel);
        if (couponModelPageResult != null) {
            data.put("coupons", couponModelPageResult);
        }
        return "admin/coupon_list";
    }

    /**
     * 优惠劵结算
     *
     * @param data
     * @param request
     * @param response
     * @param pageModel
     * @return
     */
    @RequestMapping("coupon_balance.action")
    public String coupon_balance(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response, PageModel pageModel) {
        return "admin/coupon_balance";
    }

    /**
     * 结算单列表
     *
     * @param data
     * @return
     */
    @RequestMapping("coupon_balance_list.action")
    public String coupon_balance_list(Map<String, Object> data, PageModel pageModel) {
        PageResult<CouponBill> billPageResult = this.couponService.getCouponBill(pageModel);

        if (billPageResult != null) {
            data.put("brs", billPageResult);
        }

        return "admin/coupon_balance_list";
    }

    /**
     * 结算详单
     *
     * @param data
     * @return
     */
    @RequestMapping("coupon_balance_list_detail.action")
    public String coupon_balance_list_detail(Map<String, Object> data, @RequestParam("bill_id") long id) {
        if (id > 0) {
            List<CouponUsedRecord> couponUsedRecords = this.couponService.getCouponBalanceDetailByBillid(id);
            if (couponUsedRecords != null) {
                data.put("curs", couponUsedRecords);
            }
        }
        return "admin/coupon_balance_list_detail";
    }

    /**
     * 对优惠劵进行结算
     *
     * @param ids
     * @return
     */
    @RequestMapping("coupon_balance_submit.action")
    public String coupon_balance_submit(@RequestParam(value = "ids", required = false) String ids, @RequestParam("phone") String phone) {
        List<Long> idList = null;
        if (!Strings.isNullOrEmpty(ids)) {
            String[] idStr = ids.split(",");

            if (idStr.length > 0) {
                idList = Lists.newArrayList();
                for (String id : idStr) {
                    if (!Strings.isNullOrEmpty(id)) {
                        try {
                            idList.add(Long.valueOf(id));
                        } catch (Exception e) {

                        }
                    }
                }
            }
            this.couponService.balanceCoupon(idList, phone);
        }

        return "admin/coupon_balance_list";
    }

    /**
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping("coupon_balance_search.action")
    public String coupon_balance_search(Map<String, Object> data, @RequestParam(value = "phone", required = false) String phone) {
        String res = "";
        if (!Strings.isNullOrEmpty(phone)) {
            List<CouponUsedRecord> couponUsedRecords = this.couponService.getCouponUsedRecordByPhone(phone, CONST.COUPON_B_INIT);
            if (couponUsedRecords != null) {
                res = JsonParser.simpleJson(couponUsedRecords);
            }
        }
        return res;
    }


    /**
     * 创建管理员
     *
     * @return
     */
    @RequestMapping("create_user.action")
    public String create_user(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (!Strings.isNullOrEmpty(action) && CONST.EDIT.equalsIgnoreCase(action) && !Strings.isNullOrEmpty(id)) {
            UsersModel usersModel = this.loginService.getUsersById(Long.valueOf(id));
            if (usersModel != null) {
                data.put("users", usersModel);
            }
        }
        return "admin/create_user";
    }

    /**
     * 保存用户
     *
     * @param userModel
     * @return
     */
    @RequestMapping("save_user.action")
    public String save_user(UsersModel userModel, HttpServletRequest request) {

        String editid = request.getParameter("editid");
        if (!Strings.isNullOrEmpty(editid)) {
            userModel.setId(Long.valueOf(editid));
            this.loginService.editUsers(userModel);
        } else {
            if (userModel != null) {
                userModel.setStatus(CONST.RAFFLE_STATUS_INIT);
                userModel.setCreate_time(new Date());
                this.loginService.addUsers(userModel);
            }
        }
        return "redirect:user_list.action";
    }

    /**
     * 管理员管理
     *
     * @param pageModel
     * @return
     */
    @RequestMapping("user_list.action")
    public String user_list(PageModel pageModel, Map<String, Object> data) {
        PageResult<UsersModel> usersModelPageResult = this.loginService.getUsers(pageModel);

        if (usersModelPageResult != null) {
            data.put("users", usersModelPageResult);
        }
        return "admin/user_list";
    }

    /**
     * 删除某用户
     *
     * @param id
     * @return
     */
    @RequestMapping("delete_user.action")
    public String delete_user(@RequestParam("id") long id) {
        if (id > 0) {
            try {
                this.loginService.deleteUsers(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:user_list.action";
    }

    /**
     * 黑名单管理
     *
     * @param pageModel
     * @return
     */
    @RequestMapping("backlist_list.action")
    public String backlist(PageModel pageModel, Map<String, Object> data) {
        PageResult<BackList> backListPageResult = this.backListService.getBackLists(pageModel);
        if (backListPageResult != null) {
            data.put("backLists", backListPageResult);
        }
        return "admin/backlist";
    }

    /**
     * 保存BackList数据
     *
     * @param backList
     * @return
     */
    @RequestMapping("save_backlist.action")
    public String save_backlist(BackList backList) {
        if (backList != null) {
            backList.setCreatetime(new Date());
            backList.setStatus(CONST.RAFFLE_STATUS_NORMAL);
            this.backListService.addBackList(backList);
        }
        return "redirect:backlist_list.action";
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("delete_backlist.action")
    public String delete_backlist(@RequestParam("id") long id) {
        if (id > 0) {
            try {
                this.backListService.deleteBackList(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:backlist_list.action";
    }


    /**
     * 查询商品
     *
     * @param keyword
     * @return
     */
    @ResponseBody
    @RequestMapping("search_commodity.action")
    public String search_commodity(@RequestParam("keyword") String keyword) {
        List<Commodity> commodities = null;
        String result = "";
        if (!Strings.isNullOrEmpty(keyword)) {
            commodities = this.qblkService.getCommodityByKeyword(keyword);
        }

        if (commodities != null) {
            result = JsonParser.simpleJson(commodities);
        }
        return result;
    }

    /**
     * 获取商品字级分类
     *
     * @param category
     * @return
     */
    @ResponseBody
    @RequestMapping("search_commodity_category.action")
    public String search_commodity_category(@RequestParam("category") String category) {
        String result = "";
        if (!Strings.isNullOrEmpty(category)) {
            List<CommodityCategory> commodityCategories = this.qblkService.getCommodityCategoryByCategoryName(category);
            if (commodityCategories != null) {
                result = JsonParser.simpleJson(commodityCategories);
            }
        }
        return result;
    }


    /**
     * 签到设置
     *
     * @return
     */
    @RequestMapping(value = "past_setting.action")
    public String past_setting(Map<String, Object> data, PastModel pastModel, @RequestParam("photo_up") MultipartFile file,  @RequestParam("share_photo_file") MultipartFile share_file,HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("POST")) {
            String oldPhoto=request.getParameter("old_photo");
            String oldSharePhoto=request.getParameter("old_share_photo");
            if (pastModel != null && pastModel.getTotal_drunk() > 0) {
                String file_path = FileUtil.saveFile(file);
                if (!Strings.isNullOrEmpty(file_path)) {
                    pastModel.setPhoto(file_path);
                }else{
                    pastModel.setPhoto(oldPhoto);
                }
                String share_file_path=FileUtil.saveFile(share_file);
                if(!Strings.isNullOrEmpty(file_path)){
                    pastModel.setShare_photo(share_file_path);
                }else{
                    pastModel.setShare_photo(oldSharePhoto);
                }
                pastModel = this.pastService.svePastModel(pastModel);
                return "redirect:past_redirect.action";
            }
        }

        if (pastModel == null || pastModel.getTotal_drunk() <= 0) {
            pastModel = this.pastService.getPastModelById();
        }

        if (pastModel != null) {
            data.put("pr", pastModel);
        }
        return "/admin/past_setting";
    }

    /**
     * 签到设置
     *
     * @return
     */
    @RequestMapping(value = "past_redirect.action")
    public String past_redirect(Map<String, Object> data, PastModel pastModel,HttpServletRequest httpServletRequest) {

        if (pastModel == null || pastModel.getTotal_drunk() <= 0) {
            pastModel = this.pastService.getPastModelById();
        }

        if (pastModel != null) {
            data.put("pr", pastModel);
        }
        List<CouponModel> couponModels = this.raffleService.getCouponsByStatus(CONST.RAFFLE_STATUS_NORMAL);
        if(couponModels!=null){
            data.put("coupons",couponModels);
        }
        return "/admin/past_setting";
    }

    /**
     * 签到详情
     *
     * @return
     */
    @RequestMapping("past_detail.action")
    public String past_detail(Map<String, Object> data, PageModel pageModel) {
        PageResult<PastTotal> pastTotalPageResult = this.pastService.getPastTotalByPage(pageModel);
        if (pastTotalPageResult != null) {
            data.put("prs", pastTotalPageResult);
        }
        return "admin/past_detail";
    }


    /**
     * 清除用户的签到信息
     *
     * @param phone
     * @param status
     * @return
     */
    @RequestMapping("clear_user_past.action")
    public String clearUserPast(@RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "status", required = false) int status) {
        if (!Strings.isNullOrEmpty(phone) && status > 0) {
            this.pastService.clearPastInfo(phone, status);
        }
        return "redirect:past_detail.action";
    }


    private List<RaffleCouponModel> getRaffleCoupon(HttpServletRequest request) {
        List<RaffleCouponModel> raffleCouponModels = new ArrayList<RaffleCouponModel>();

        //获取对应的设置列表
        if (!Strings.isNullOrEmpty(request.getParameter("cid_1"))) {
            int cid1 = Integer.valueOf(request.getParameter("cid_1"));
            int cid_rate1 = Integer.valueOf(request.getParameter("cid_rate_1"));
            RaffleCouponModel raffleCouponModel1 = new RaffleCouponModel();
            raffleCouponModel1.setCid(cid1);
            raffleCouponModel1.setWinrate(cid_rate1);
            raffleCouponModel1.setCindex(1);
            raffleCouponModels.add(raffleCouponModel1);
        }

        if (!Strings.isNullOrEmpty(request.getParameter("cid_2"))) {
            int cid2 = Integer.valueOf(request.getParameter("cid_2"));
            int cid_rate2 = Integer.valueOf(request.getParameter("cid_rate_2"));
            RaffleCouponModel raffleCouponModel2 = new RaffleCouponModel();
            raffleCouponModel2.setCid(cid2);
            raffleCouponModel2.setWinrate(cid_rate2);
            raffleCouponModel2.setCindex(2);
            raffleCouponModels.add(raffleCouponModel2);
        }

        if (!Strings.isNullOrEmpty(request.getParameter("cid_3"))) {
            int cid3 = Integer.valueOf(request.getParameter("cid_3"));
            int cid_rate3 = Integer.valueOf(request.getParameter("cid_rate_3"));
            RaffleCouponModel raffleCouponModel3 = new RaffleCouponModel();
            raffleCouponModel3.setCid(cid3);
            raffleCouponModel3.setWinrate(cid_rate3);
            raffleCouponModel3.setCindex(3);
            raffleCouponModels.add(raffleCouponModel3);
        }

        if (!Strings.isNullOrEmpty(request.getParameter("cid_4"))) {
            int cid4 = Integer.valueOf(request.getParameter("cid_4"));
            int cid_rate4 = Integer.valueOf(request.getParameter("cid_rate_4"));
            RaffleCouponModel raffleCouponModel4 = new RaffleCouponModel();
            raffleCouponModel4.setCid(cid4);
            raffleCouponModel4.setWinrate(cid_rate4);
            raffleCouponModel4.setCindex(4);
            raffleCouponModels.add(raffleCouponModel4);
        }

        if (!Strings.isNullOrEmpty(request.getParameter("cid_5"))) {
            int cid5 = Integer.valueOf(request.getParameter("cid_5"));
            int cid_rate5 = Integer.valueOf(request.getParameter("cid_rate_5"));
            RaffleCouponModel raffleCouponModel5 = new RaffleCouponModel();
            raffleCouponModel5.setCid(cid5);
            raffleCouponModel5.setWinrate(cid_rate5);
            raffleCouponModel5.setCindex(5);
            raffleCouponModels.add(raffleCouponModel5);
        }

        if (!Strings.isNullOrEmpty(request.getParameter("cid_6"))) {
            int cid6 = Integer.valueOf(request.getParameter("cid_6"));
            int cid_rate6 = Integer.valueOf(request.getParameter("cid_rate_6"));
            RaffleCouponModel raffleCouponModel6 = new RaffleCouponModel();
            raffleCouponModel6.setCid(cid6);
            raffleCouponModel6.setWinrate(cid_rate6);
            raffleCouponModel6.setCindex(6);
            raffleCouponModels.add(raffleCouponModel6);
        }


        if (!Strings.isNullOrEmpty(request.getParameter("cid_7"))) {
            int cid7 = Integer.valueOf(request.getParameter("cid_7"));
            int cid_rate7 = Integer.valueOf(request.getParameter("cid_rate_7"));
            RaffleCouponModel raffleCouponModel7 = new RaffleCouponModel();
            raffleCouponModel7.setCid(cid7);
            raffleCouponModel7.setWinrate(cid_rate7);
            raffleCouponModel7.setCindex(7);
            raffleCouponModels.add(raffleCouponModel7);
        }

        if (!Strings.isNullOrEmpty(request.getParameter("cid_8"))) {

            int cid8 = Integer.valueOf(request.getParameter("cid_8"));
            int cid_rate8 = Integer.valueOf(request.getParameter("cid_rate_8"));
            RaffleCouponModel raffleCouponModel8 = new RaffleCouponModel();
            raffleCouponModel8.setCid(cid8);
            raffleCouponModel8.setWinrate(cid_rate8);
            raffleCouponModel8.setCindex(8);
            raffleCouponModels.add(raffleCouponModel8);
        }

        if (!Strings.isNullOrEmpty(request.getParameter("cid_9"))) {
            int cid9 = Integer.valueOf(request.getParameter("cid_9"));
            int cid_rate9 = Integer.valueOf(request.getParameter("cid_rate_9"));
            RaffleCouponModel raffleCouponModel9 = new RaffleCouponModel();
            raffleCouponModel9.setCid(cid9);
            raffleCouponModel9.setWinrate(cid_rate9);
            raffleCouponModel9.setCindex(9);
            raffleCouponModels.add(raffleCouponModel9);
        }

        return raffleCouponModels;
    }
}
