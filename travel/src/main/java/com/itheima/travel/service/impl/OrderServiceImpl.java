package com.itheima.travel.service.impl;

import com.itheima.travel.dao.OrderDao;
import com.itheima.travel.dao.OrderItemDao;
import com.itheima.travel.domain.Order;
import com.itheima.travel.domain.OrderItem;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.service.OrderService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/22 18:48
 */
public class OrderServiceImpl implements OrderService {

    // 保存信息到订单
    @Override
    public void save(Order order) {
        // 创建代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        OrderItemDao orderItemDao = sqlSession.getMapper(OrderItemDao.class);

        // 保存订单表
        orderDao.save(order);
        // 保存订单项
        List<OrderItem> orderItemList = order.getOrderItemList();
        for (OrderItem orderItem : orderItemList) {
            orderItemDao.save(orderItem);
        }
        // 关闭工厂会话
        MyBatisUtils.close(sqlSession);
    }

    // 查询订单状态是否修改
    @Override
    public ResultInfo findState(String oid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        Order order = orderDao.findByOid(oid);
        ResultInfo resultInfo = new ResultInfo(false);
        if (order.getState() == 1) {  // 修改了，表示付款成功
            resultInfo.setSuccess(true);
        }
        // 关闭会话
        MyBatisUtils.close(sqlSession);
        return resultInfo;
    }
}
