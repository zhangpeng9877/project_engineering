package com.itheima.travel.dao;

import com.itheima.travel.domain.OrderItem;
import org.apache.ibatis.annotations.Update;

/**
 * @author 张鹏
 * @date 2020/5/22 20:37
 */
public interface OrderItemDao {
    void save(OrderItem orderItem);

    @Update("update tab_orderitem set state = 1 where oid = #{oid}")
    void updateState(String oid);
}
