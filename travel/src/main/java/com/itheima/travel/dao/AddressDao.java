package com.itheima.travel.dao;

import com.itheima.travel.domain.Address;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/18 20:08
 */
public interface AddressDao {

    List<Address> findByUid(Integer uid);

    void save(Address populate);

    void deleteByAid(Integer aid);

    void updateByIsdefault(List<Address> addressList);
}
