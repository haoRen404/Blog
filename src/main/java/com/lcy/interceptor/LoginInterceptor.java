package com.lcy.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果没有登录，则重定向到登陆页面，session域中没有用户信息就是没有登录
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");// 重定向到登录界面
            return false;// 拒绝往下执行
        }
        return true;// 允许往下执行
    }
}
