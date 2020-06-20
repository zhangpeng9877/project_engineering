package com.itheima.travel.web.servlet;

import cn.hutool.core.util.IdUtil;
import com.itheima.travel.domain.*;
import com.itheima.travel.service.AddressService;
import com.itheima.travel.service.OrderService;
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
import java.util.*;

/**
 * @author 张鹏
 * @date 2020/5/22 18:46
 */
@WebServlet("/orderServlet")
public class OrderServlet extends BaseServlet {

    private AddressService addressService = (AddressService) BeanFactory.getBean("addressService");
    private OrderService orderService = (OrderService) BeanFactory.getBean("orderService");

    // 结算购物车
    protected void orderInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取到用户信息
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        Integer rid = StringToIntUtils.parseInt(request.getParameter("rid"), null);
        // 获取到redis中购物车的信息
        Cart cart = CartUtils.getRedisToCart(currentUser);
        // 查询到该用户的收获地址
        List<Address> addressList = addressService.findByUid(currentUser.getUid());
        // 指定商品下单购买
        getCartItemMap(rid, cart);
        // 设置到request域对象中
        request.setAttribute("cart", cart);
        request.setAttribute("addressList", addressList);
        // 转发到order_info.jsp
        request.getRequestDispatcher("/order_info.jsp").forward(request, response);
    }

    // 生成订单
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date date = new Date();
        // 获取请求参数
        String addressId = request.getParameter("addressId");
        String[] addressArray = addressId.split(",");   // 收件人，收件地址，收件人号码
        Integer rid = StringToIntUtils.parseInt(request.getParameter("rid"), null);
        // 获取用户信息
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        // 获取购物车数据
        Cart cart = CartUtils.getRedisToCart(currentUser);
        // 创建订单，设置基础数据
        Order order = new Order();
        order.setOid(IdUtil.simpleUUID());  // 订单号
        order.setOrdertime(date);   // 下单时间
        order.setState(0);      // 订单的支付状态，0表示未支付
        order.setAddress(addressArray[1]);  // 订单的收获地址
        order.setContact(addressArray[0]);  // 订单的收获联系人
        order.setTelephone(addressArray[2]);    // 订单的收获手机号码
        order.setUid(currentUser.getUid()); // 下单用户的uid
        // 遍历购物项，创建订单项
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = null;
        LinkedHashMap<Integer, CartItem> cartItemMap = getCartItemMap(rid, cart);
        for (CartItem cartItem : cartItemMap.values()) {
            // 创建订单项
            orderItem = new OrderItem();
            orderItem.setItemtime(date);    // 下单时间
            orderItem.setState(0);  // 支付状态
            orderItem.setNum(cartItem.getNum());    // 每个商品总购买数量
            orderItem.setSubtotal(cartItem.getSubTotal());  // 每个商品的总金额
            orderItem.setRid(cartItem.getRoute().getRid()); // 商品的rid
            orderItem.setOid(order.getOid());   // 订单oid，简化数据操作
            // 添加到订单项集合中
            orderItemList.add(orderItem);
        }

        order.setTotal(cart.getCartTotal());    // 订单总金额

        // 将订单项集合关联到订单实体中
        order.setOrderItemList(orderItemList);
        // 调用service，保存到数据库
        orderService.save(order);
        // 清空购物车数据
        Jedis jedis = JedisUtils.getJedis();
        // 判断是否是清空购物车还是指定购买
        if (rid == null) {      // 是清空购物车
            jedis.del("cart_" + currentUser.getUsername());
        } else {    // 只删除下单的商品
            LinkedHashMap<Integer, CartItem> cartCartItemMap = CartUtils.getRedisToCart(currentUser).getCartItemMap(); // 重新获取到购物车项
            cartCartItemMap.remove(rid);    // 删除
            cart.setCartItemMap(cartCartItemMap);   // 重新设置
            CartUtils.setCartToRedis(currentUser, cart);    // 重新保存到redis
        }
        jedis.close();
        // 重定向到pay.jsp.....未完待续
        response.sendRedirect(request.getContextPath() + "/payServlet?action=createUrl&oid=" + order.getOid() + "&price=" + order.getTotal());
    }

    // 查询订单状态是否修改
    protected void findState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");

        ResultInfo resultInfo = orderService.findState(oid);
        // 转换为json数据，并响应给浏览器
        stringToJsonWrite(resultInfo, response);

    }

    private LinkedHashMap<Integer, CartItem> getCartItemMap(Integer rid, Cart cart) {
        LinkedHashMap<Integer, CartItem> cartItemMap = cart.getCartItemMap();
        if (rid != null) {
            for (CartItem cartItem : cartItemMap.values()) {
                if (cartItem.getRoute().getRid() == rid) {
                    cartItemMap = new LinkedHashMap<>();
                    cartItemMap.put(rid, cartItem);
                    cart.setCartItemMap(cartItemMap);
                    break;
                }
            }
        }
        return cartItemMap;
    }
}
