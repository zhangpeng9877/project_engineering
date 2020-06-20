package com.itheima.travel.service;

import com.itheima.travel.domain.Order;
import com.itheima.travel.domain.ResultInfo;

/**
 * @author 张鹏
 * @date 2020/5/22 18:48
 */
public interface OrderService {
    void save(Order order);

    ResultInfo findState(String oid);
}
