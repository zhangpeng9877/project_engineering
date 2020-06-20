package com.itheima.travel.dao;

import com.itheima.travel.domain.RouteImg;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/21 20:06
 */
public interface RouteImgDao {

    // 指定查询
    List<RouteImg> findByRid(Integer rid);
}
