package com.light.outside.comes.controller;

import com.light.outside.comes.model.CouponUsedRecord;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.CouponService;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("code.action")
    public String banquet(Map<String, Object> data, HttpServletRequest request) {
        UserModel userModel = getAppUserInfo();
        String cardno = RequestTools.RequestString(request, "cardno", "");
        long couponRecordId = RequestTools.RequestLong(request, "id", 0);
        couponService.transferCoupon(cardno, userModel.getId(), couponRecordId);
        return "";
    }


}
