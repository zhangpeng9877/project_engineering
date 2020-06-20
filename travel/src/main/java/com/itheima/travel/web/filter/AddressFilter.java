package com.itheima.travel.web.filter;

import com.itheima.travel.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/addressServlet")
public class AddressFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 校验session域对象中是否存在用户信息
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            // 重定向到主页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }

}
