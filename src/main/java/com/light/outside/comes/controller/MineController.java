package com.light.outside.comes.controller;

import com.light.outside.comes.controller.admin.LoginController;
import com.light.outside.comes.model.*;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.*;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Autowired
    private SigninService signinService;

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
        List<CouponRecordViewModel> couponRecordModels = raffleService.getRaffleCouponByUser(userModel.getId(), status);
        raffleService.transfCouponForView(couponRecordModels);
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

    @RequestMapping("qrcode.action")
    public String getQRCode(Map<String, Object> data, HttpServletRequest request){
        long id=RequestTools.RequestLong(request, "id", 0);
        CouponRecordModel couponRecordModel=raffleService.getCouponRecordById(id);
        if(couponRecordModel!=null) {
            data.put("coupon", couponRecordModel);
            data.put("qrcode_url", CONST.SITE_URL + "coupon/qrcode_detail.action?id=" + couponRecordModel.getId());
        }
        return "qrcode";
    }

    @RequestMapping("signin_d.action")
    public String signin_d(Map<String,Object> data,HttpServletRequest request){
        UserModel userModel=getAppUserInfo();
        PastTotal pastTotalModel=this.signinService.getPastTotalByUid(userModel.getId());
        data.put("pastTotal",pastTotalModel);
        return "signin_d";
    }

    /**
     * 签到干杯
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("sign_in.action")
    public String sign_in(Map<String, Object> data, HttpServletRequest request){
        UserModel userModel=getAppUserInfo();

        //增加XX毫升酒

        return "sign_in";
    }

    /**
     * 邀请朋友干杯
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("share_sign_in.action")
    public String share_sign_in(Map<String, Object> data, HttpServletRequest request){
        //帮朋友增加xx毫升酒
        return "share_sign_in";
    }
}
