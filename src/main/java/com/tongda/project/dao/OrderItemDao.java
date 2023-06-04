package com.tongda.project.dao;

import com.tongda.project.bean.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 19:38
 */
@Repository
public interface OrderItemDao {

    /**
     * 添加订单项
     * @param orderItem
     * @return
     */
    @Insert("insert into s_orderitem values(null,#{flowId},#{orderId},#{quantity})")
    boolean addOrderItem(OrderItem orderItem);

    /**
     * 根据订单id得到订单项集合
     * @param orderId
     * @return
     */
    @Select("select * from s_orderitem where orderId = #{orderId}")
    List<OrderItem> getOrderItemByOrderId(int orderId);
}
