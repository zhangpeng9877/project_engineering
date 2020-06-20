package com.itheima.travel.service;

import com.itheima.travel.domain.Page;
import com.itheima.travel.domain.Route;

/**
 * @author 张鹏
 * @date 2020/5/21 15:43
 */
public interface RouteService {
    Page<Route> findByPage(Integer currentPage, Integer cid, String rname);

    Route findDetail(Integer rid);
}
