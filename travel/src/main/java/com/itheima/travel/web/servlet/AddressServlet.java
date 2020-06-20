package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.Address;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.AddressService;
import com.itheima.travel.util.WebBeanUtils;
import com.itheima.travel.web.factory.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/18 20:09
 */
@WebServlet("/addressServlet")
public class AddressServlet extends BaseServlet {

    private AddressService address = (AddressService) BeanFactory.getBean("addressService");

    // 查询所有的收获地址
    protected void findByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 校验session域对象中是否存在用户信息
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        // 根据uid查询
        List<Address> addressList = address.findByUid(currentUser.getUid());
        // 存储到request域对象中
        request.setAttribute("addressList", addressList);
        // 转发到home_address.jsp页面上
        request.getRequestDispatcher("/home_address.jsp").forward(request, response);

    }

    // 保存新地址
    protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 校验session域对象中是否存在用户信息
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        // 获取请求参数，封装到实体类中
        Address populate = WebBeanUtils.populate(new Address(), request.getParameterMap());
        // 指定非默认地址
        populate.setIsdefault("0");
        // 指定存储用户
        populate.setUser(currentUser);
        // 调用service保存新地址
        address.save(populate);
        // 重定向到页面上
        response.sendRedirect(request.getContextPath() + "/addressServlet?action=findByUid");

    }

    // 删除收获地址
    protected void deleteByAid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String aidStr = request.getParameter("aid");
        // 转换为int类型
        Integer aid = Integer.parseInt(aidStr);
        // 调用service方法删除
        address.deleteByAid(aid);
        // 重定向到页面上
        response.sendRedirect(request.getContextPath() + "/addressServlet?action=findByUid");
    }

    // 修改默认地址
    protected void updateByAidWithIsdefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 先查找到所有的收获信息
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        List<Address> addressList = address.findByUid(currentUser.getUid());
        // 获取到请求参数
        Integer aid = Integer.parseInt(request.getParameter("aid"));
        // 把所有收获地址的isdefault修改为默认0
        for (Address addre : addressList) {
            addre.setIsdefault("0");
            // 查找到需要修改收获地址的数据
            if (addre.getAid().equals(aid)) {
                addre.setIsdefault("1");
            }
        }
        // 调用service修改默认值
        address.updateByIsdefault(addressList);
        // 重定向
        response.sendRedirect(request.getContextPath() + "/addressServlet?action=findByUid");
    }
}
