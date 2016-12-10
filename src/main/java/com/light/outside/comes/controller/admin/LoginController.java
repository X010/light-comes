package com.light.outside.comes.controller.admin;

import com.light.outside.comes.controller.BaseController;
import com.light.outside.comes.service.admin.LoginService;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * b3st9u
 */
@Controller
@RequestMapping("admin")
public class LoginController extends BaseController {

    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private LoginService loginService;

    /**
     * 跳转到后台主页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "to_index.action", method = {RequestMethod.POST, RequestMethod.GET})
    public String to_index(HttpServletRequest request, HttpServletResponse response) {
        return "admin/mainframe";
    }

    /**
     * 跳转到登录页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "to_login.action", method = {RequestMethod.POST, RequestMethod.GET})
    public String to_login(HttpServletRequest request, HttpServletResponse response) {
        return "admin/login";
    }

    /**
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "login.action", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        String userName = RequestTools.RequestString(request, "userName", "");
        String password = RequestTools.RequestString(request, "password", "");
        LOG.info("LOGIN:" + userName);
        boolean isSuccess = loginService.login(userName, password, request);
        if (isSuccess) {
            data.put("code", 1);
            data.put("msg", "登录成功");
            return JsonTools.jsonSer(data);
        } else {
            data.put("code", 0);
            data.put("userName", userName);
            data.put("msg", "用户名或密码错误！");
            return JsonTools.jsonSer(data);
        }
    }


    /**
     * 注销
     *
     * @param request
     */
    @RequestMapping(value = "logout.action", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void logout(HttpServletRequest request) {
        try {
            request.getSession().invalidate();
        } catch (Exception e) {
        }
    }

}
