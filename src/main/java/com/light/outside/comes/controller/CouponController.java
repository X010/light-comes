package com.light.outside.comes.controller;

import com.light.outside.comes.model.CouponRecordModel;
import com.light.outside.comes.model.CouponUsedRecord;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.CouponService;
import com.light.outside.comes.service.RaffleService;
import com.light.outside.comes.service.weixin.MD5;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by b3st9u on 16/11/15.
 */
@Controller
@RequestMapping("coupon")
public class CouponController extends BaseController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private RaffleService raffleService;

    @RequestMapping("qrcode_detail.action")
    public String getQRCode(Map<String, Object> data, HttpServletRequest request){
        long id=RequestTools.RequestLong(request, "id", 0);
        UserModel userModel = getAppUserInfo();
        if(userModel!=null) {
            CouponRecordModel couponRecordModel = raffleService.getCouponRecordById(id);
            if (couponRecordModel != null) {
                data.put("coupon", couponRecordModel);
            }
            return "scan";
        }else{
            return "login";
        }
    }

//    @RequestMapping("code.action")
//    public String transfer(Map<String, Object> data, HttpServletRequest request) {
//        UserModel userModel = getAppUserInfo();
//        if(userModel!=null) {
//            String cardno = RequestTools.RequestString(request, "cardno", "");
//            long couponRecordId = RequestTools.RequestLong(request, "id", 0);
////            couponService.transferCoupon(cardno, userModel.getId(), couponRecordId);
//            return "";
//        }else{
//            return "login";
//        }
//    }

    @RequestMapping("transferCoupon.action")
    @ResponseBody
    public String transferCoupon(Map<String, Object> data, HttpServletRequest request){
        UserModel userModel = getAppUserInfo();
        long id=RequestTools.RequestLong(request, "id", 0);
        CouponRecordModel couponRecordModel=raffleService.getCouponRecordById(id);
        int code=couponService.transferCoupon(couponRecordModel.getCardno(), userModel, id);
        String msg="转让成功！";
        if(code<0){
            if(code==-2){
                msg="兑换失败，无法查询到该优惠券!";
            }else if(code==-1){
                msg="兑换失败，该优惠券状态已过期或已使用!";
            }
        }
        data.put("code",code);
        data.put("msg",msg);
        return JsonTools.jsonSer(data);
    }

    @RequestMapping("use_coupon_api.action")
    @ResponseBody
    public String useCoupon(Map<String,Object> data,HttpServletRequest request){
        long id=RequestTools.RequestLong(request, "id", 0);
        String token=RequestTools.RequestString(request, "token", "");
        String signStr=String.format("%d&%s",id,CONST.SIGNATURE_KEY);
        System.out.println(signStr);
        String checkToken = MD5.MD5Encode(signStr);
        int code=-2;
        String msg="转让成功！";
        if(checkToken.equals(token)) {
        CouponRecordModel couponRecordModel=raffleService.getCouponRecordById(id);
        if(couponRecordModel!=null) {
            code = couponService.changeCoupon(couponRecordModel.getCardno(), id);
        }

        if(code<0){
            if(code==-2){
                msg="兑换失败，无法查询到该优惠券!";
            }else if(code==-1){
                msg="兑换失败，该优惠券状态已过期或已使用!";
            }
        }
        }else{
            code=-3;
            msg="签名验证失败";
        }
        data.put("code",code);
        data.put("msg",msg);
        return JsonTools.jsonSer(data);
    }




}
