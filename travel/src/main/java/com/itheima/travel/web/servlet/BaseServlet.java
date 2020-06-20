package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        String action = request.getParameter("action");
        try {
            // 反射获取指定方法
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            // 暴力访问
            method.setAccessible(true);
            // 调用方法
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // throw new RuntimeException("服务器繁忙...");
            response.sendRedirect(request.getContextPath() + "/index_404.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void stringToJsonWrite(Object object, HttpServletResponse response) throws IOException {
        // 转换位json数据
        String json = new ObjectMapper().writeValueAsString(object);
        // 响应
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
