package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Result findAll() {
        List<CheckItem> list = checkItemDao.findAll();
        if (list == null) {
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
    }
}
