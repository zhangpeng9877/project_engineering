package com.itheima.travel.service.impl;

import com.itheima.travel.dao.OrderDao;
import com.itheima.travel.dao.OrderItemDao;
import com.itheima.travel.service.PayNotifyService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

/**
 * @author 张鹏
 * @date 2020/5/24 18:06
 */
public class PayNotifyServiceImpl implements PayNotifyService {
    @Override
    public void updateState(Map map) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        OrderItemDao orderItemDao = sqlSession.getMapper(OrderItemDao.class);
        // 获取到订单号
        String oid = (String) map.get("out_trade_no");
        orderDao.updateState(oid);  // 修改订单
        orderItemDao.updateState(oid);  // 修改订单项

        MyBatisUtils.close(sqlSession); // 关闭资源
    }
}
