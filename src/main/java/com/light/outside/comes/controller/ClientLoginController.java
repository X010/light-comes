package com.light.outside.comes.controller;

import com.light.outside.comes.service.admin.LoginService;
import com.light.outside.comes.utils.JsonTools;
import com.light.outside.comes.utils.RequestTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by b3st9u on 16/10/27.
 */
@Controller
@RequestMapping("qblk")
public class ClientLoginController {
    @Resource
    private LoginService loginService;
    /**
     *
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
}
