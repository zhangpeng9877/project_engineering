package com.itheima.health.dao;

import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/21 18:10
 */
public interface CheckItemDao {
    /**
     * 查询列表项
     * @return
     */
    List<CheckItem> findAll();
}
