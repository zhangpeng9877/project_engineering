package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/22 19:44
 */
public interface CheckGroupService {

    /**
     * 添加检查组
     */
    void add(CheckGroup checkGroup,Integer[] checkitemIds);

    /**
     * 分页查询
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 根据检查组id查询检查项的指定所有id
     */
    List<Integer> findByIdWhitCheckItem(Integer id);

    /**
     * 修改检查组和检查项
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 删除检查组，并且需要删除中间表关系
     */
    void delete(Integer id) throws HealthException;
}
