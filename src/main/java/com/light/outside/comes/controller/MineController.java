package com.light.outside.comes.controller;

import com.light.outside.comes.controller.admin.LoginController;
import com.light.outside.comes.qbkl.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by b3st9u on 16/10/30.
 */
@Controller
@RequestMapping("my")
public class MineController {
    @RequestMapping("mine.action")
    public String mine(Map<String, Object> data, HttpServletRequest request) {
        UserModel userModel = (UserModel) request.getSession().getAttribute(LoginController.SESSION_KEY_APP_USERINFO);
        return "mine";
    }

    /**
     * 我的拍卖
     *
     * @param data
     * @return
     */
    @RequestMapping("mine_auction.action")
    public String mine_auction(Map<String, Object> data) {
        return "mine_auction";
    }

    /**
     * 我的优惠劵
     *
     * @param data
     * @return
     */
    @RequestMapping("mine_coupon.action")
    public String mine_coupon(Map<String, Object> data) {
        return "mine_coupon";
    }


    /**
     * 我的砍价活动
     *
     * @param data
     * @return
     */
    @RequestMapping("mine_overcharged.action")
    public String mine_overcharged(Map<String, Object> data) {
        return "mine_overcharged";
    }

    /**
     * 我的饭局
     *
     * @param data
     * @return
     */
    @RequestMapping("mine_banquet.action")
    public String mine_banquet(Map<String, Object> data) {
        return "mine_banquet";
    }
}
