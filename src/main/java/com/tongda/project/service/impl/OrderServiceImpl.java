package com.tongda.project.service.impl;

import com.tongda.project.bean.Order;
import com.tongda.project.bean.PageBean;
import com.tongda.project.dao.OrderDao;
import com.tongda.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 19:16
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    /**
     * 添加订单
     * @return
     * @param order
     */
    @Override
    public boolean addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    /**
     * 根据订单号得到订单编号
     * @param orderNum
     * @return
     */
    @Override
    public int getOrderIdByOrderNum(String orderNum) {
        return orderDao.getOrderIdByOrderNum(orderNum);
    }

    /**
     * 根据用户id得到其订单数量
     * @param userId
     * @return
     */
    @Override
    public long orderCountByUserId(int userId) {
        return orderDao.orderCountByUserId(userId);
    }

    /**
     * 得到订单列表
     * @param pageBean
     * @param userId
     * @return
     */
    @Override
    public List<Order> getOrderList(PageBean pageBean, int userId) {
        return orderDao.getOrderList(pageBean,userId);
    }

    /**
     * 获取所有订单数量
     * @return
     */
    @Override
    public long allCountOrder() {
        return orderDao.allCountOrder();
    }

    /**
     * 根据分页对象获取当前页面需要展示的数据
     * @param pageBean
     * @return
     */
    @Override
    public List<Order> getallOrderByPage(PageBean pageBean) {
        return orderDao.getallOrderByPage(pageBean);
    }

    /**
     * 根据订单id得到订单信息
     * @param orderId
     * @return
     */
    @Override
    public Order getOrderById(int orderId) {
        return orderDao.getOrderById(orderId);
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @Override
    public boolean delOrderById(int orderId) {
        return orderDao.delOrderById(orderId);
    }

    /**
     * 根据订单编号得到数量
     * @param orderNum
     * @return
     */
    @Override
    public long getCountByOrderNum(String orderNum) {
        return orderDao.getCountByOrderNum(orderNum);
    }

    /**
     * 根据分页对象得到当前页面要展示的数据
     * @param pageBean
     * @param orderNum
     * @return
     */
    @Override
    public List<Order> getOrdersByLike(PageBean pageBean, String orderNum) {
        return orderDao.getOrdersByLike(pageBean,orderNum);
    }

    /**
     * 设置配送员
     * @param order
     * @return
     */
    @Override
    public boolean updateChargerToOrder(Order order) {
        return orderDao.updateChargerToOrder(order);
    }

    /**
     * 改变订单状态为发货
     * @param order
     * @return
     */
    @Override
    public boolean updateOrderStatus(Order order) {
        return orderDao.updateOrderStatus(order);
    }

    /**
     * 获取未发货并且根据订单编号得到的订单数量
     * @param orderStatus
     * @param orderNum
     * @return
     */
    @Override
    public long getCountByWaitOrderNum(int orderStatus, String orderNum) {
        return orderDao.getCountByWaitOrderNum(orderStatus,orderNum);
    }

    /**
     * 获取未发货并且根据订单编号和分页对象得到的当前页面要展示订单集合信息
     * @param pageBean
     * @param orderStatus
     * @param orderNum
     * @return
     */
    @Override
    public List<Order> getOrdersByWaitLike(PageBean pageBean, int orderStatus, String orderNum) {
        return orderDao.getOrdersByWaitLike(pageBean,orderStatus,orderNum);
    }

    /**
     * 获取待处理页面中已提交的订单数量
     * @param orderStatus
     * @return
     */
    @Override
    public long allCountOrderByStatus(int orderStatus) {
        return orderDao.allCountOrderByStatus(orderStatus);
    }

    /**
     * 根据分页对象和已提交状态得到当前页面要展示的数据
     * @param pageBean
     * @param orderStatus
     * @return
     */
    @Override
    public List<Order> getallOrderByPageAndStatus(PageBean pageBean, int orderStatus) {
        return orderDao.getallOrderByPageAndStatus(pageBean, orderStatus);
    }
}
