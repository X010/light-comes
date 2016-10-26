package com.light.outside.comes.controller;

import com.alibaba.fastjson.JSONObject;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.service.admin.LoginService;
import com.light.outside.comes.utils.JsonClient;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by b3st9u on 16/10/27.
 */
@Controller
@RequestMapping("qblk")
public class ClientLoginController extends BaseController{
    @Resource
    private LoginService loginService;
    /**
     *前端登录
     * @param request
     * @param response
     */
    @RequestMapping(value = "login.action",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String login(Map<String, Object> data,HttpServletRequest request, HttpServletResponse response){
        String phone= RequestTools.RequestString(request, "phone", "");
        String password= RequestTools.RequestString(request,"password","");
        boolean isSuccess=loginService.clientLogin(phone, password, request);
        if(isSuccess) {
            data.put("code",1);
            data.put("msg","登录成功");
            return JsonTools.jsonSer(data);
        }else{
            data.put("code",0);
            data.put("phone",phone);
            data.put("msg","手机号名或密码错误！");
            return JsonTools.jsonSer(data);
        }
    }

    @RequestMapping(value = "wechatLogin.action",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String wechatLogin(Map<String, Object> data,HttpServletRequest request, HttpServletResponse response){
        String code = RequestTools.RequestString(request,"code","");
        String state = "2";//RequestTools.RequestString(request,"state","/");
        String redirect = RequestTools.RequestString(request,"state","/");

        try{
            //获取openid
            Map<String, String> map = new HashMap<String, String>();

            //todo: 放到配置文件中
            map.put("appid", "wx65db45e85c1be9c4");
            map.put("secret", "3816ac8f220a3e9150fbab7321064d45");
            map.put("code", code);
            map.put("grant_type", "authorization_code");
            JSONObject json = JsonClient.post("https://api.weixin.qq.com/sns/oauth2/access_token", map);
            if(json.containsKey("errcode")){
                throw new Exception("get openid fail");
            }
            String accessToken = json.getString("access_token");
            String openid = json.getString("openid");
            String unionid = json.getString("unionid");


            String urlUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info";
            if(state.equals("1")){
                //获取token
                //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
                map.clear();
                map.put("grant_type", "client_credential");
                map.put("appid", "wx65db45e85c1be9c4");
                map.put("secret", "3816ac8f220a3e9150fbab7321064d45");
                json = JsonClient.post("https://api.weixin.qq.com/cgi-bin/token", map);
                if(json.containsKey("errcode")){
                    throw new Exception("get token fail");
                }
                accessToken = json.getString("access_token");
            }else{
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
            if(json.containsKey("errcode")){
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
            request.getServletContext().setAttribute("accessToken",accessToken);

            response.sendRedirect(redirect);

            return "login success:" + openid;
        }catch(Exception ex){
            ex.printStackTrace();
            return "error:" + ex.getMessage();
        }
    }
}
