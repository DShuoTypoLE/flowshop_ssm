package com.tongda.project.service;

import com.tongda.project.bean.OrderItem;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 19:38
 */
public interface OrderItemService {

    /**
     * 添加订单项
     * @param orderItem
     * @return
     */
    boolean addOrderItem(OrderItem orderItem);

    /**
     * 根据订单id得到订单项集合
     * @param orderId
     * @return
     */
    List<OrderItem> getOrderItemByOrderId(int orderId);
}
