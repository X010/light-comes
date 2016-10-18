package com.light.outside.comes.filter;

import com.light.outside.comes.controller.admin.LoginController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class SessionFilter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        String url = request.getServletPath();
        boolean isLogin = false;
        if (url.contains("admin/to_login.action") || url.contains("admin/login.action")
                || url.contains(".css") || url.contains(".js") || url.contains(".png") || url.contains(".jpg")) {
            chain.doFilter(request, response);
        } else if (url.contains("admin/")){
            if (session.getAttribute(LoginController.SESSION_KEY_USERINFO) == null) {
                session.invalidate();
                loginOut(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    private void loginOut(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println("<script language='javascript' type='text/javascript'>");
            out.println("alert('由于长时间没有操作,导致Session失效,请重新登录!');window.top.location.href='" + request.getContextPath() + "/admin/to_login.action'");
            out.println("</script>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}