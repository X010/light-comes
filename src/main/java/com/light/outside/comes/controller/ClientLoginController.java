package com.light.outside.comes.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.light.outside.comes.service.admin.LoginService;
import com.light.outside.comes.service.weixin.MD5;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.JsonClient;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by b3st9u on 16/10/27.
 */
@Controller
@RequestMapping("qblk")
public class ClientLoginController extends BaseController {

    private final static Logger LOG = LoggerFactory.getLogger(ClientLoginController.class);

    @Resource
    private LoginService loginService;

    @Value("${baseUrl}")
    private String baseUrl;

    /**
     * 前端登录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "login.action", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String login(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        String phone = RequestTools.RequestString(request, "username", "");
        String password = RequestTools.RequestString(request, "password", "");
        String redirect = RequestTools.RequestString(request, "redirect", "");
        boolean isSuccess = loginService.clientLogin(phone, password, request);
        if (isSuccess) {
            data.put("code", 1);
            data.put("msg", "登录成功");
            data.put("redirect", redirect);
            //model.setViewName("redirect:"+redirect);
            Cookie usernameCookie = new Cookie("username", URLEncoder.encode(phone));
            Cookie passwordCookie = new Cookie("password", URLEncoder.encode(password));
            usernameCookie.setMaxAge(864000);
            passwordCookie.setMaxAge(864000);//设置最大生存期限为10天
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
            return JsonTools.jsonSer(data);
        } else {
            data.put("code", 0);
            data.put("phone", phone);
            data.put("msg", "手机号名或密码错误！");
            return JsonTools.jsonSer(data);
        }
    }

    @RequestMapping(value = "logout.action", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        loginout(request, response);
//        request.getSession().invalidate();
//        Cookie usernameCookie = new Cookie("username", null);
//        Cookie passwordCookie = new Cookie("password", null);
//        usernameCookie.setMaxAge(0);
//        passwordCookie.setMaxAge(0);//设置最大生存期限为10天
//        response.addCookie(usernameCookie);
//        response.addCookie(passwordCookie);
        return "login";
    }

    /**
     * 跳转登录页面
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "to_login.action", method = {RequestMethod.POST, RequestMethod.GET})
    public String toLogin(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        String username = "";
        String password = "";
        String redirect = RequestTools.RequestString(request, "redirect", "");
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            try {
                for (Cookie c : cookies) {
                    if (c.getName().equals("username")) {
                        username = URLDecoder.decode(c.getValue(), "utf-8");
                    }
                    if (c.getName().equals("password")) {
                        password = URLDecoder.decode(c.getValue(), "utf-8");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        boolean isSuccess = loginService.clientLogin(username, password, request);
        if (isSuccess) {
            LOG.info("login and redirect url:" + redirect);
            if (Strings.isNullOrEmpty(redirect))
                return "redirect:/raffle/lottery.action";
            else
                return "redirect:" + redirect;
        }
        data.put("redirect", redirect);
        return "login";
    }

    /**
     * 登录API
     * @param data
     * @param request
     * @return
     */
    @RequestMapping("login_api.action")
    @ResponseBody
    public String loginForAPI(Map<String, Object> data, HttpServletRequest request,HttpServletResponse response){
        String phone = RequestTools.RequestString(request, "username", "");
        String password = RequestTools.RequestString(request, "password", "");
        String token=RequestTools.RequestString(request,"token","");
        String callback=RequestTools.RequestString(request,"callback","");
        //String signStr=phone+"&"+password+"&"+ CONST.SIGNATURE_KEY;
        String signStr=String.format("%s&%s&%s",password,phone,CONST.SIGNATURE_KEY);
//        String checkToken = MD5.MD5Encode(signStr);
        LOG.info("login:"+phone +" password:" +password +" token:"+token);
        //if(checkToken.equals(token)) {
            boolean isSuccess = loginService.clientLogin(phone, password, request);
            LOG.info("username:"+phone +" status:"+isSuccess);
            if (isSuccess) {
                Cookie usernameCookie = new Cookie("username", URLEncoder.encode(phone));
                Cookie passwordCookie = new Cookie("password", URLEncoder.encode(password));
                usernameCookie.setMaxAge(864000);
                passwordCookie.setMaxAge(864000);//设置最大生存期限为10天
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
                data.put("code", 200);
                data.put("msg", "登录成功");
            } else {
                data.put("code", 500);
                data.put("msg", "登录失败");
            }
//        }else{
//            data.put("code",404);
//            data.put("msg","签名验证失败");
//        }
        return CallBackResultJsonP(JsonTools.jsonSer(data), callback);
    }

    /**
     * 退出登录API
     * @param data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("loginout_api.action")
    @ResponseBody
    public String loginoutForAPI(Map<String, Object> data, HttpServletRequest request,HttpServletResponse response){
        String callback=RequestTools.RequestString(request,"callback","");
        loginout(request,response);
        data.put("code", 200);
        data.put("msg","登出成功");
        return CallBackResultJsonP(JsonTools.jsonSer(data), callback);
    }

    private void loginout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().invalidate();
        Cookie usernameCookie = new Cookie("username", null);
        Cookie passwordCookie = new Cookie("password", null);
        usernameCookie.setMaxAge(0);
        passwordCookie.setMaxAge(0);
        response.addCookie(usernameCookie);
        response.addCookie(passwordCookie);
    }

    @RequestMapping(value = "wechatLogin.action", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String wechatLogin(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        String code = RequestTools.RequestString(request, "code", "");
        String state = "2";//RequestTools.RequestString(request,"state","/");
        String redirect = RequestTools.RequestString(request, "state", "/");

        try {
            //获取openid
            Map<String, String> map = new HashMap<String, String>();

            //todo: 放到配置文件中
            map.put("appid", "wx65db45e85c1be9c4");
            map.put("secret", "3816ac8f220a3e9150fbab7321064d45");
            map.put("code", code);
            map.put("grant_type", "authorization_code");
            JSONObject json = JsonClient.post("https://api.weixin.qq.com/sns/oauth2/access_token", map);
            if (json.containsKey("errcode")) {
                throw new Exception("get openid fail");
            }
            String accessToken = json.getString("access_token");
            String openid = json.getString("openid");
            String unionid = json.getString("unionid");


            String urlUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info";
            if (state.equals("1")) {
                //获取token
                //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
                map.clear();
                map.put("grant_type", "client_credential");
                map.put("appid", "wx65db45e85c1be9c4");
                map.put("secret", "3816ac8f220a3e9150fbab7321064d45");
                json = JsonClient.post("https://api.weixin.qq.com/cgi-bin/token", map);
                if (json.containsKey("errcode")) {
                    throw new Exception("get token fail");
                }
                accessToken = json.getString("access_token");
            } else {
                urlUserInfo = "https://api.weixin.qq.com/sns/userinfo";
            }

            //获取用户信息
            //https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
            //https://api.weixin.qq.com/sns/userinfo
            map.clear();
            map.put("access_token", accessToken);
            map.put("openid", openid);
            map.put("lang", "zh_CN");
            json = JsonClient.post(urlUserInfo, map);
            if (json.containsKey("errcode")) {
                throw new Exception("get userinfo fail");
            }
            String nickname = json.getString("nickname");
            String headimgurl = json.getString("headimgurl");
            String sex = json.getString("sex");

            //是否需要保存用户？？
//            UserModel userModel = loginService.wechatLogin(openid, nickname, headimgurl, sex);
//            if(userModel != null){
//                this.setAppUserInfo(userModel);
//            }
            // 全局缓存 accessToken
            request.getServletContext().setAttribute("accessToken", accessToken);

            response.sendRedirect(redirect);

            return "login success:" + openid;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error:" + ex.getMessage();
        }
    }
}
