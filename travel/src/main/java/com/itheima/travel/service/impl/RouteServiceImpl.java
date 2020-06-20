package com.itheima.travel.service.impl;

import com.itheima.travel.dao.RouteDao;
import com.itheima.travel.domain.Page;
import com.itheima.travel.domain.Route;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/21 15:43
 */
public class RouteServiceImpl implements RouteService {

    // 分页查询所有景点
    @Override
    public Page<Route> findByPage(Integer currentPage, Integer cid, String rname) {
        // 创建代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        RouteDao routeDao = sqlSession.getMapper(RouteDao.class);

        // 创建分页对象
        Page<Route> page = new Page<>();
        // 获取总记录数
        Integer totalCount = routeDao.findCount(cid, rname);
        // 计算出总页数
        Integer totalPage = (int) Math.ceil(totalCount * 1.0 / Page.PAGE_SIZE);

        // 判断当前页是否合格currentPage
        if (currentPage < 1)
            currentPage = 1;
        if (currentPage > totalPage)
            currentPage = totalPage;


        // 计算开始查询的索引
        Integer index = (currentPage - 1) * Page.PAGE_SIZE;
        // 调用dao层方法查询
        List<Route> list = null;
        try {
            list = routeDao.findList(cid, index, Page.PAGE_SIZE, rname);
        } catch (Exception e) {
            e.getMessage();
        }

        // 把所有查询到的数据封装到Page对象中
        page.setCurrentPage(currentPage);
        page.setTotalCount(totalCount);
        page.setTotalPage(totalPage);
        page.setList(list);

        // 释放资源
        MyBatisUtils.close(sqlSession);
        return page;
    }

    // 指定查询线路详情
    @Override
    public Route findDetail(Integer rid) {
        // 创建代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        RouteDao routeDao = sqlSession.getMapper(RouteDao.class);
        // 调用dao查询.4张表嵌套查询
        Route route = routeDao.findByRidWithAll(rid);
        // 释放资源
        MyBatisUtils.close(sqlSession);
        return route;
    }
}
