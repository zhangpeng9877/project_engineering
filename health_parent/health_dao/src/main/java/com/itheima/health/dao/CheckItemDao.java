package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/21 18:10
 */
public interface CheckItemDao {
    /**
     * 查询列表项
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
    void delete(Integer id);

    /**
     * 查询删除该数据是否存在表关联
     */
    Integer findCountByCheckItemId(Integer checkitem_id);

    /**
     * 分页查询
     */
    Page<CheckItem> findPage(String queryString);
}
