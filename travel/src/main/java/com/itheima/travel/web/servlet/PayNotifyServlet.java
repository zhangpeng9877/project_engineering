package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.itheima.travel.service.PayNotifyService;
import com.itheima.travel.web.factory.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张鹏
 * @date 2020/5/24 18:01
 */
@WebServlet("/payNotifyServlet")
public class PayNotifyServlet extends HttpServlet {

    private PayNotifyService payNotifyService = (PayNotifyService) BeanFactory.getBean("payNotifyService");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 接收请求参数（xml）
        ServletInputStream inputStream = req.getInputStream();
        // 将xml转换位java对象
        XmlMapper xmlMapper = new XmlMapper();
        Map map = xmlMapper.readValue(inputStream, Map.class);
        // 调用service修改订单的状态
        payNotifyService.updateState(map);
        // 通知微信平台，接收成功..
        HashMap<String, String> result = new HashMap<>();
        result.put("return_code", "SUCCESS");
        result.put("return_msg", "OK");
        // 将map转换为xml
        String xml = xmlMapper.writeValueAsString(result);
        resp.setContentType("application/xml;charset=utf-8");   // 告知是xml类型
        resp.getWriter().write(xml);
    }
}
