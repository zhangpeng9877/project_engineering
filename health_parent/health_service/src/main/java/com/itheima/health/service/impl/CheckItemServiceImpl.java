package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/6/21 18:09
 */

@Service
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有项
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 添加数据项
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 修改数据项
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 删除检查项
     */
    @Override
    public void delete(Integer id) {
        //先判断这个检查项是否被检查组使用了
        //调用dao查询检查项的id是否在t_checkgroup_checkitem表中存在记录
        Integer countByCheckItemId = checkItemDao.findCountByCheckItemId(id);
        //被使用了则不能删除
        if (countByCheckItemId > 0) {
            // 需要在这个接口的方法中抛出HealthException异常才能被全局捕获，因为没有指定异常抛出，在web层会被运行时异常捕获
            throw new HealthException(MessageConstant.CHECKITEM_IN_USE);
        }
        // 没使用就可以调用dao删除
        checkItemDao.delete(id);
    }


    /**
     * 分页查询
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {

        // 使用PageHelper.startPage开启分页查询模式
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        // 判断是否有条件查询的处理，模糊查询
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            // 拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 紧接着调用dao层方法查询，会被分页
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());

        // 对查询的结果进行封装
        return new PageResult<CheckItem>(page.getTotal(), page.getResult());

    }

}
