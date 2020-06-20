package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.Category;
import com.itheima.travel.service.CategoryService;
import com.itheima.travel.web.factory.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/18 22:35
 */
@WebServlet("/categoryServlet")
public class CategoryServlet extends BaseServlet {

    private CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");

    // ajax查询导航条所有的内容
    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 调用service中的方法查询所有导航条内容
        List<Category> categoryList = categoryService.findAll();
        // 转换为json数据，并响应到浏览器中
        stringToJsonWrite(categoryList, response);
    }
}
