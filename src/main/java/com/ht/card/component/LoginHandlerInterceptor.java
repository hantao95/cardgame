package com.ht.card.component;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截逻辑
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断session中是否存在登录信息
        Object user = request.getSession().getAttribute("userid");
        if(user!=null){
            return true;
        }else{
            request.setAttribute("msg","没有权限，请登录");
            //不存在就返回登录页面
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }
    }
}
