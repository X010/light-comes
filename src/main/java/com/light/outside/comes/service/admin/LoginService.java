package com.light.outside.comes.service.admin;

import com.google.common.base.Preconditions;
import com.light.outside.comes.controller.admin.LoginController;
import com.light.outside.comes.model.admin.UsersModel;
import com.light.outside.comes.mybatis.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by b3st9u on 16/10/16.
 */
@Service
public class LoginService {
    @Autowired
    private UserDao userDao;

    public boolean login(String userName, String pwd, HttpServletRequest request) {
        boolean isSuccess = false;
        UsersModel usersModel = userDao.queryUserByPwd(userName, pwd);
        if (usersModel != null) {
            isSuccess = true;
            HttpSession session = request.getSession();
            session.setAttribute(LoginController.SESSION_KEY_USERINFO, usersModel);
        }
        return isSuccess;
    }


    public void addUser(UsersModel usersModel) {
        Preconditions.checkNotNull(usersModel);
        
    }

}
