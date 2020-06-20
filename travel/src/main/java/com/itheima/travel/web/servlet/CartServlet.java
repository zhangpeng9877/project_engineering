package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.Cart;
import com.itheima.travel.domain.CartItem;
import com.itheima.travel.domain.Route;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.util.CartUtils;
import com.itheima.travel.util.JedisUtils;
import com.itheima.travel.util.StringToIntUtils;
import com.itheima.travel.web.factory.BeanFactory;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author 张鹏
 * @date 2020/5/22 14:04
 */
@WebServlet("/cartServlet")
public class CartServlet extends BaseServlet {
    private RouteService routeService = (RouteService) BeanFactory.getBean("routeService");

    // 添加商品到购物车
    protected void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        Integer rid = StringToIntUtils.parseInt(request.getParameter("rid"));
        Integer num = StringToIntUtils.parseInt(request.getParameter("num"));
        // 从redis中获取购物车数据
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        Cart cart = CartUtils.getRedisToCart(currentUser);
        // 判断当前商品的购物项是否已经存在
        LinkedHashMap<Integer, CartItem> cartItemMap = cart.getCartItemMap();
        CartItem cartItem = cartItemMap.get(rid);
        if (cartItem != null) { // 表示存在
            // 数量的累加
            cartItem.setNum(cartItem.getNum() + num);
        } else {    // 不存在
            // 根据rid查询route对象
            Route route = routeService.findDetail(rid);
            // 将需要添加的商品设置到cartItem对象中
            cartItem = new CartItem();
            cartItem.setRoute(route);
            cartItem.setNum(num);
            // 添加进购物项对象中
            cartItemMap.put(rid, cartItem);
        }
        // 将购物车更新到redis中
        CartUtils.setCartToRedis(currentUser, cart);
        // 将添加的商品信息设置到request域对象中
        cartItem.setNum(num);
        request.setAttribute("cartItem", cartItem);
        // 转发到添加成功的页面
        request.getRequestDispatcher("/cart_success.jsp").forward(request, response);
    }

    // 查看购物车
    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取登录对象的信息
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        // 查询redis中购物车的数据
        Cart cart = CartUtils.getRedisToCart(currentUser);
        // 设置到request域对象中
        request.setAttribute("cart", cart);
        // 转发到cart.jsp页面
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    // 删除购物车中的商品
    protected void delCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer rid = StringToIntUtils.parseInt(request.getParameter("rid"), null);
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        // 获取redis中的购物车数据
        Cart cart = CartUtils.getRedisToCart(currentUser);
        // 判断是否是全部删除还是指定删除
        if (rid == null) {  // 全部删除
            Jedis jedis = JedisUtils.getJedis();
            jedis.del("cart_" + currentUser.getUsername());
            jedis.close();
        } else {
            // 删除指定的购物项
            cart.getCartItemMap().remove(rid);
            // 重新保存到redis中
            CartUtils.setCartToRedis(currentUser, cart);
        }
        // 重定向到查询购物车页面
        response.sendRedirect(request.getContextPath() + "/cartServlet?action=findAll");
    }
}
