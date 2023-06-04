package com.tongda.project.service.impl;

import com.tongda.project.bean.Address;
import com.tongda.project.dao.AddressDao;
import com.tongda.project.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 22:42
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;

    /**
     * 获取地址
     * @return
     */
    @Override
    public Address getAddress() {
        return addressDao.getAddress();
    }

    /**
     * 修改配送地址
     * @return
     * @param address
     */
    @Override
    public boolean updateAddress(Address address) {
        return addressDao.updateAddress(address);
    }
}
