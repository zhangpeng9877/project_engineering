package com.itheima.travel.service;

import com.itheima.travel.domain.Address;

import java.util.List;

/**
 * @author 张鹏
 * @date 2020/5/18 20:09
 */
public interface AddressService {
    List<Address> findByUid(Integer uid);

    void save(Address populate);

    void deleteByAid(Integer aid);

    void updateByIsdefault(List<Address> addressList);
}
