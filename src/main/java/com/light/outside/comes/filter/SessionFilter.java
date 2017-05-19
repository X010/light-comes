package com.light.outside.comes.filter;

import com.light.outside.comes.controller.admin.LoginController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SessionFilter implements javax.servlet.Filter {
    private final static String LOGIN_URL="http://www.qubulikou.com/yeshizuileweixin/Mine/login.html";
    private final static String TEST_LOGIN_URL="http://120.55.241.127/Mine/login.html";
    private final static Logger LOG = LoggerFactory.getLogger(SessionFilter.class);

    @Value("${baseUrl}")
    private String baseUrl;

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
//        LOG.info("req :" + url);
        boolean isLogin = false;
        if (url.contains("admin/to_login.action") || url.contains("admin/login.action")
                || url.contains(".css") || url.contains(".js") || url.contains(".png") || url.contains(".jpg")) {
//            LOG.info("req no filter:" + url);
            chain.doFilter(request, response);
        } else if (url.contains("admin/")) {
            LOG.info("check session status");
            if (session.getAttribute(LoginController.SESSION_KEY_USERINFO) == null) {
                session.invalidate();
                loginOut(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }
        //客户端登录验证url.contains("auction/query_auction.action")||url.contains("oc/query_overcharged.action")||
        else if (url.contains("qblk/to_login.action") || url.contains("qblk/login.action") ||url.contains("qblk/loginout_api.acton")||url.contains("qblk/login_api.action")||url.contains("coupon/use_coupon_api.action")
                || url.contains(".css") || url.contains(".js") || url.contains(".png") || url.contains(".jpg")) {
            chain.doFilter(request, response);
        } else if (url.contains("/auction/") || url.contains("/banquet/") || url.contains("/raffle/") || url.contains("/oc/") || url.contains("/my/") || url.contains("/pt/")) {
            if (session.getAttribute(LoginController.SESSION_KEY_APP_USERINFO) == null) {
                session.invalidate();
                clientLoginOut(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
//            response.setContentType("text/html;charset=UTF-8");
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "0");
//            response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.setHeader("XDomainRequestAllowed", "1");
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
            out.println("alert('由于长时间没有操作,导致Session失效,请重新登录!');window.top.location.href='" + request.getContextPath() + baseUrl + "admin/to_login.action'");
            out.println("</script>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端
     * param request
     *
     * @param response
     */
    private void clientLoginOut(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out;
        try {
            String servletPath = request.getServletPath().replace("//", "");
            String contextPath = request.getContextPath();
            String forwardUrl = contextPath + baseUrl + "qblk/to_login.action";
            //System.out.println("forwardUrl:" + forwardUrl + "  servletPath:" + servletPath);
//            String forwardUrl=TEST_LOGIN_URL;
            if (StringUtils.isNotBlank(servletPath)) {
                String redirect = "";
                if (StringUtils.isNotBlank(request.getQueryString())) {
                    redirect = servletPath + "?" + StringUtils.defaultString(request.getQueryString());
                } else {
                    redirect = servletPath;
                }
//                System.out.println("redirect:"+contextPath + StringUtils.defaultIfEmpty(forwardUrl, "/")
//                        + "?redirect=" + URLEncoder.encode(baseUrl+redirect, "UTF-8"));
//                response.sendRedirect(contextPath + StringUtils.defaultIfEmpty(forwardUrl, "/")
//                        + "?redirect=" + URLEncoder.encode(baseUrl+redirect, "UTF-8"));
                out = response.getWriter();
                out.println("<script language='javascript' type='text/javascript'>");
                System.out.println("url:"+StringUtils.defaultIfEmpty(forwardUrl, "/") + "?redirect=" + URLEncoder.encode(redirect, "UTF-8") + "'");
                //out.println("window.top.location.href='" + StringUtils.defaultIfEmpty(forwardUrl, "/") + "?redirect=" + URLEncoder.encode(redirect, "UTF-8") + "'");
                out.println("window.top.location.href='"+forwardUrl+"'");
                out.println("</script>");
            }
//            out = response.getWriter();
//            out.println("<script language='javascript' type='text/javascript'>");
//            out.println("window.top.location.href='" + request.getContextPath() + "/qblk/to_login.action'");
//            out.println("</script>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
