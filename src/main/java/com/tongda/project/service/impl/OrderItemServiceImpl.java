package com.tongda.project.service.impl;

import com.tongda.project.bean.OrderItem;
import com.tongda.project.dao.OrderItemDao;
import com.tongda.project.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 19:38
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemDao orderItemDao;
    /**
     * 添加订单项
     * @param orderItem
     * @return
     */
    @Override
    public boolean addOrderItem(OrderItem orderItem) {
        return orderItemDao.addOrderItem(orderItem);
    }

    /**
     * 根据订单id得到订单项集合
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItem> getOrderItemByOrderId(int orderId) {
        return orderItemDao.getOrderItemByOrderId(orderId);
    }
}
