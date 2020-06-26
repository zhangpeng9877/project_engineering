package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;

/**
 * @author 张鹏
 * @date 2020/6/26 20:27
 */
public interface OrderSetTingDao {

    /**
     * 根据日期查找出ordertseting
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 修改
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     * 添加
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);
}
