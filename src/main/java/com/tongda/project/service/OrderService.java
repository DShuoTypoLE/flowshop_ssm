package com.tongda.project.service;

import com.tongda.project.bean.Order;
import com.tongda.project.bean.PageBean;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 19:16
 */
public interface OrderService {
    /**
     * 添加订单
     * @return
     * @param order
     */
    boolean addOrder(Order order);

    /**
     * 根据订单号得到订单编号
     * @param orderNum
     * @return
     */
    int getOrderIdByOrderNum(String orderNum);

    /**
     * 根据用户id得到其订单数量
     * @param userId
     * @return
     */
    long orderCountByUserId(int userId);

    /**
     * 得到订单列表
     * @param pageBean
     * @param userId
     * @return
     */
    List<Order> getOrderList(PageBean pageBean, int userId);

    /**
     * 获取所有订单数量
     * @return
     */
    long allCountOrder();

    /**
     * 根据分页对象获取当前页面需要展示的数据
     * @param pageBean
     * @return
     */
    List<Order> getallOrderByPage(PageBean pageBean);

    /**
     * 根据订单id得到订单信息
     * @param orderId
     * @return
     */
    Order getOrderById(int orderId);

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    boolean delOrderById(int orderId);

    /**
     * 根据订单编号得到数量
     * @param orderNum
     * @return
     */
    long getCountByOrderNum(String orderNum);

    /**
     * 根据分页对象得到当前页面要展示的数据
     * @param pageBean
     * @param orderNum
     * @return
     */
    List<Order> getOrdersByLike(PageBean pageBean, String orderNum);

    /**
     * 设置配送员
     * @param order
     * @return
     */
    boolean updateChargerToOrder(Order order);

    /**
     * 改变订单状态为发货
     * @param order
     * @return
     */
    boolean updateOrderStatus(Order order);

    /**
     * 获取未发货并且根据订单编号得到的订单数量
     * @param orderStatus
     * @param orderNum
     * @return
     */
    long getCountByWaitOrderNum(int orderStatus, String orderNum);

    /**
     * 获取未发货并且根据订单编号和分页对象得到的当前页面要展示订单集合信息
     * @param pageBean
     * @param orderStatus
     * @param orderNum
     * @return
     */
    List<Order> getOrdersByWaitLike(PageBean pageBean, int orderStatus, String orderNum);

    /**
     * 获取待处理页面中已提交的订单数量
     * @param orderStatus
     * @return
     */
    long allCountOrderByStatus(int orderStatus);

    /**
     * 根据分页对象和状态得到当前页面要展示的数据
     * @param pageBean
     * @param orderStatus
     * @return
     */
    List<Order> getallOrderByPageAndStatus(PageBean pageBean, int orderStatus);
}
