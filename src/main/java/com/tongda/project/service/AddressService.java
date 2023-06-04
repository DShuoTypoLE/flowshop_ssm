package com.tongda.project.service;

import com.tongda.project.bean.Address;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 22:41
 */
public interface AddressService {
    /**
     * 得到地址信息
     * @return
     */
    Address getAddress();

    /**
     * 修改配送地址
     * @return
     * @param address
     */
    boolean updateAddress(Address address);
}
