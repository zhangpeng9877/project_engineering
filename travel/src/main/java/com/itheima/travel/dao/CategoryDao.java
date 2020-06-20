package com.itheima.travel.dao;

import com.itheima.travel.domain.Category;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/18 22:36
 */
public interface CategoryDao {
    // 查询导航条所有内容
    List<Category> findAll();

    // 指定查询
    Category findByCid(Integer cid);
}
