package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/22 19:43
 */
public interface CheckGroupDao {

    /**
     * 添加检查组
     */
    void add(CheckGroup checkGroup);

    /**
     * 把检查组表和检查项中的id关联在t_checkgroup_checkitem表中
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    /**
     * 分页查询
     */
    Page<CheckGroup> findPage(String queryString);

    /**
     * 根据检查组id查询检查项的指定所有id
     */
    List<Integer> findByIdWhitCheckItem(Integer id);

    /**
     * 修改检查组信息
     */
    void update(CheckGroup checkGroup);

    /**
     * 根据检查组id删除中间表数据（清理原有关联关系）
     */
    void deleteCheckGroupCheckItemById(Integer id);

    /**
     * 删除检查组信息
     */
    void delete(Integer id);


    /**
     * 判断检查组和套餐(主)表是否有关联关系
     */
    Integer findCheckGroupCountBySetMeal(Integer id);
}
