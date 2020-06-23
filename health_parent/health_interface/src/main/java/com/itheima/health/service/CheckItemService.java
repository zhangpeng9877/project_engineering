package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/21 18:02
 */
public interface CheckItemService {
    /**
     * 查询所有项
     */
    List<CheckItem> findAll();

    /**
     * 添加数据项
     */
    void add(CheckItem checkItem);

    /**
     * 修改数据项
     */
    void update(CheckItem checkItem);

    /**
     * 删除数据项
     */
    void delete(Integer id) throws HealthException;


    /**
     * 分页查询
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);
}
