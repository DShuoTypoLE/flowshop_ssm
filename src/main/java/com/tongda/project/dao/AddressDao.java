package com.tongda.project.dao;

import com.tongda.project.bean.Address;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 22:43
 */
@Repository
public interface AddressDao {
    /**
     * 得到地址信息
     * @return
     */
    @Select("select * from s_address")
    Address getAddress();

    /**
     * 修改配送地址
     * @return
     * @param address
     */
    @Update("update s_address set province = #{province},city=#{city} where id=1")
    boolean updateAddress(Address address);
}
