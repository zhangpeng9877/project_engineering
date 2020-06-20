package com.itheima.travel.dao;

import com.itheima.travel.domain.Order;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author 张鹏
 * @date 2020/5/22 18:48
 */
public interface OrderDao {
    void save(Order order);

    @Update("update tab_order set state = 1 where oid = #{oid} ")
    void updateState(String oid);

    @Select("select * from tab_order where oid = #{oid}")
    Order findByOid(String oid);
}
