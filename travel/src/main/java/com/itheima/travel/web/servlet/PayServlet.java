package com.itheima.travel.web.servlet;

import com.itheima.travel.util.PayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 张鹏
 * @date 2020/5/24 16:27
 */
@WebServlet("/payServlet")
public class PayServlet extends BaseServlet {

    protected void createUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收请求参数
        String oid = request.getParameter("oid");
        String price = request.getParameter("price");
        // 调用微信平台，生成预交易链接
        String pay_url = PayUtils.createOrder(oid, 1);  // 目前写死1分钱

        request.setAttribute("oid", oid);
        request.setAttribute("pay_url", pay_url);
        request.setAttribute("price", price);
        request.getRequestDispatcher("/pay.jsp").forward(request, response);
    }
}
