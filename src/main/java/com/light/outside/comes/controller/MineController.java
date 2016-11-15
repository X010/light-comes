package com.light.outside.comes.controller;

import com.light.outside.comes.controller.admin.LoginController;
import com.light.outside.comes.model.*;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.AuctionService;
import com.light.outside.comes.service.RaffleService;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import com.light.outside.comes.service.BanquetService;
import com.light.outside.comes.service.OverchargedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by b3st9u on 16/10/30.
 */
@Controller
@RequestMapping("my")
public class MineController extends BaseController {

    @Autowired
    private OverchargedService overchargedService;

    @Autowired
    private BanquetService banquetService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private RaffleService raffleService;

    @RequestMapping("mine.action")
    public String mine(Map<String, Object> data, HttpServletRequest request) {
        UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
        if (userModel != null) {
            data.put("um", userModel);
        }
        return "mine";
    }

    /**
     * 我的拍卖
     *
     * @param data
     * @return
     */
    @RequestMapping("mine_auction.action")
    public String mine_auction(Map<String, Object> data, HttpServletRequest request) {
        int status = RequestTools.RequestInt(request, "status", 0);
        UserModel userModel = getAppUserInfo();
        List<AuctionRecordsModel> list = auctionService.queryAuctionRecordsByUser(userModel.getId(), status);
        data.put("status", status);
        data.put("records", list);
        return "mine_auction";
    }


    /**
     * 我的优惠劵
     *
     * @param data
     * @return
     */
    @RequestMapping("mine_coupon.action")
    public String mine_coupon(Map<String, Object> data, HttpServletRequest request) {
        int status = RequestTools.RequestInt(request, "status", 0);
        UserModel userModel = getAppUserInfo();
        List<CouponRecordModel> couponRecordModels = raffleService.getRaffleCouponByUser(userModel.getId(), status);
        data.put("records", couponRecordModels);
        data.put("status", status);
        return "mine_coupon";
    }


    /**
     * 我的砍价活动
     *
     * @param data
     * @return
     */
    @RequestMapping("mine_overcharged.action")
    public String mine_overcharged(Map<String, Object> data, HttpServletRequest request) {
        int status = RequestTools.RequestInt(request, "status", 0);
        data.put("status", status);
        return "mine_overcharged";
    }

    /**
     * 我的饭局
     *
     * @param data
     * @return
     */
    @RequestMapping("mine_banquet.action")
    public String mine_banquet(Map<String, Object> data, HttpServletRequest request) {
        int status = RequestTools.RequestInt(request, "status", 0);
        data.put("status", status);
        return "mine_banquet";
    }

    @RequestMapping("sign_in.action")
    public String sign_in(Map<String, Object> data, HttpServletRequest request){
        //增加XX毫升酒
        return "sign_in";
    }

    @RequestMapping("share_sign_in.action")
    public String share_sign_in(Map<String, Object> data, HttpServletRequest request){
        //帮朋友增加xx毫升酒
        return "share_sign_in";
    }
}
