package com.itheima.travel.dao;

import com.itheima.travel.domain.Seller;

/**
 * @author 张鹏
 * @date 2020/5/21 20:02
 */
public interface SellerDao {

    // 指定查询
    Seller findBySid(Integer sid);

}
