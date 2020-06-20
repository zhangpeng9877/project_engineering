package com.itheima.travel.service.impl;

import com.itheima.travel.dao.AddressDao;
import com.itheima.travel.domain.Address;
import com.itheima.travel.service.AddressService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/18 20:09
 */
public class AddressServiceImpl implements AddressService {
    // 根据uid查询收获地址
    @Override
    public List<Address> findByUid(Integer uid) {
        // 获取代理对象，创建工厂会话对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao mapper = sqlSession.getMapper(AddressDao.class);
        // 调用dao查询
        List<Address> addressList = mapper.findByUid(uid);
        // 关闭工厂会话
        MyBatisUtils.close(sqlSession);
        return addressList;
    }

    // 保存新的收获地址
    @Override
    public void save(Address populate) {
        // 获取代理对象，创建工厂会话对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao mapper = sqlSession.getMapper(AddressDao.class);
        // 调用dao保存
        mapper.save(populate);
        // 关闭工厂会话
        MyBatisUtils.close(sqlSession);
    }

    // 根据aid删除收获地址
    @Override
    public void deleteByAid(Integer aid) {
        // 获取代理对象，创建工厂会话对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao mapper = sqlSession.getMapper(AddressDao.class);
        // 调用dao保存
        mapper.deleteByAid(aid);
        // 关闭工厂会话
        MyBatisUtils.close(sqlSession);
    }

    // 修改默认收获地址
    @Override
    public void updateByIsdefault(List<Address> addressList) {
        // 获取代理对象，创建工厂会话对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao mapper = sqlSession.getMapper(AddressDao.class);
        // 调用dao修改
        mapper.updateByIsdefault(addressList);
        // 关闭工厂会话
        MyBatisUtils.close(sqlSession);
    }
}
