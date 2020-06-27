package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

    /**
     * 通过月份来获取预约设置信息
     */
    List<OrderSetting> getOrderSetTingByMonth(@Param("monthBegin") String monthBegin,@Param("monthEnd") String monthEnd);
}
