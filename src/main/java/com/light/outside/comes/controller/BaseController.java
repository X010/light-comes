package com.light.outside.comes.controller;

import com.google.common.base.Strings;
import com.light.outside.comes.model.admin.UsersModel;
import com.light.outside.comes.qbkl.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * B3ST9U
 */
@SuppressWarnings("unchecked")
public class BaseController {

    @Autowired
    private HttpSession session;

    public static final String SESSION_KEY_USERINFO = "sys_user";
    public static final String SESSION_KEY_APP_USERINFO = "app_user";

    protected void setSession(String key, Object value) {
        session.setAttribute(key, value);
    }

    protected Object getSession(String key) {
        return session.getAttribute(key);
    }


    /**
     * 后端用户
     */
    public UsersModel getUserInfo() {
        Object obj = session.getAttribute(SESSION_KEY_USERINFO);
        if (obj != null) {
            return (UsersModel) obj;
        } else {
            return null;
        }
    }

    /**
     * 前端登录用户
     *
     * @return
     */
    public UserModel getAppUserInfo() {
        Object obj = session.getAttribute(SESSION_KEY_APP_USERINFO);
        if (obj != null) {
            return (UserModel) obj;
        } else {
            return null;
        }
    }

    /**
     * app用户
     *
     * @param userModel
     */
    protected void setAppUserInfo(UserModel userModel) {
        session.setAttribute(SESSION_KEY_USERINFO, userModel);
    }

    /**
     * 后端用户
     *
     * @param userModel
     */
    protected void setUserInfo(UsersModel userModel) {
        session.setAttribute(SESSION_KEY_USERINFO, userModel);
    }

    /**
     * @param response
     * @param strMsg
     */
    protected void responseSendMsg(HttpServletResponse response, String strMsg) {
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(strMsg);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * callback数据处理
     *
     * @param result
     * @param callback
     * @return
     */
    protected String callBackResultJsonP(String result, String callback) {
        if (Strings.isNullOrEmpty(callback))
            return result;
        result = String.format("%s(%s)", callback, result);
        return result;
    }
}
