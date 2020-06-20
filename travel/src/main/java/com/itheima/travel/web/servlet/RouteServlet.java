package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.Category;
import com.itheima.travel.domain.Page;
import com.itheima.travel.domain.Route;
import com.itheima.travel.service.CategoryService;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.util.StringToIntUtils;
import com.itheima.travel.web.factory.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 张鹏
 * @date 2020/5/21 15:42
 */

@WebServlet("/routeServlet")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = (RouteService) BeanFactory.getBean("routeService");
    private CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");

    // 分页查询
    protected void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前页的请求参数
        Integer currentPage = StringToIntUtils.parseInt(request.getParameter("currentPage"), 1);
        // 获取分类景点的cid参数
        Integer cid = StringToIntUtils.parseInt(request.getParameter("cid"), null);
        // 获取模糊查询关键字
        String rname = request.getParameter("rname");

        // 调用service分页查询
        Page<Route> page = routeService.findByPage(currentPage, cid, rname);

        // 查询分类对象
        Category category = categoryService.findByCid(cid);

        // 将分页对象保存到request域对象中
        request.setAttribute("page", page);
        request.setAttribute("rname", rname); // 回显搜索关键字
        request.setAttribute("category", category); // 回显分类和id

        // 转发到route_list.jsp
        request.getRequestDispatcher("/route_list.jsp").forward(request, response);
    }

    // 线路详情
    protected void routeDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        Integer rid = StringToIntUtils.parseInt(request.getParameter("rid"), null);
        // 调用service查询
        Route route = routeService.findDetail(rid);
        // 储存到request域对象中
        request.setAttribute("route", route);
        // 转发到 route_detail.jsp
        request.getRequestDispatcher("/route_detail.jsp").forward(request, response);

    }
}
