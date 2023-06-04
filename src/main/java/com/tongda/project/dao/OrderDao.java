package com.tongda.project.dao;

import com.tongda.project.bean.Order;
import com.tongda.project.bean.PageBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 19:17
 */
@Repository
public interface OrderDao {

    /**
     * 添加订单
     * @return
     * @param order
     */
    @Insert("insert into s_order(orderNum," +
            "userId,orderDate,money,orderStatus," +
            "shpeople,shphone,shaddress) values(" +
            "#{orderNum},#{userId},#{orderDate}," +
            "#{money},#{orderStatus},#{shpeople}," +
            "#{shphone},#{shaddress})")
    boolean addOrder(Order order);

    /**
     * 根据订单号得到订单编号
     * @param orderNum
     * @return
     */
    @Select("select orderId from s_order where orderNum = #{orderNum}")
    int getOrderIdByOrderNum(String orderNum);

    /**
     * 根据用户id得到其订单数量
     * @param userId
     * @return
     */
    @Select("select count(1) as count from s_order where userId = #{userId}")
    long orderCountByUserId(int userId);

    /**
     * 得到订单列表
     * @param pageBean
     * @param userId
     * @return
     */
    @Select("select * from s_order where userId = #{userId} " +
            "limit ${(pageBean.curPage-1)*pageBean.maxSize},#{pageBean.maxSize}")
    List<Order> getOrderList(@Param("pageBean") PageBean pageBean, @Param("userId") int userId);

    /**
     * 获取所有订单数量
     * @return
     */
    @Select("select count(1) as count from s_order")
    long allCountOrder();

    /**
     * 根据分页对象获取当前页面需要展示的数据
     * @param pageBean
     * @return
     */
    @Select("select * from s_order limit ${(curPage-1)*maxSize},#{maxSize}")
    List<Order> getallOrderByPage(PageBean pageBean);

    /**
     * 获取待处理页面中已提交的订单数量
     * @param orderStatus
     * @return
     */
    @Select("select count(1) as count from s_order where orderStatus=#{orderStatus}")
    long allCountOrderByStatus(int orderStatus);

    /**
     * 根据分页对象和已提交状态得到当前页面要展示的数据
     * @param pageBean
     * @param orderStatus
     * @return
     */
    @Select("select * from s_order where orderStatus=#{orderStatus} " +
            "limit ${(pageBean.curPage-1)*pageBean.maxSize},#{pageBean.maxSize}")
    List<Order> getallOrderByPageAndStatus(@Param("pageBean") PageBean pageBean, @Param("orderStatus") int orderStatus);

    /**
     * 根据订单id得到订单信息
     * @param orderId
     * @return
     */
    @Select("select * from s_order where orderId=#{orderId}")
    Order getOrderById(int orderId);

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @Delete("delete from s_order where orderId=#{orderId}")
    boolean delOrderById(int orderId);

    /**
     * 根据订单编号得到数量
     * @param orderNum
     * @return
     */
    @Select("select count(1) as count from s_order where orderNum like concat('%',#{orderNum},'%')")
    long getCountByOrderNum(String orderNum);

    /**
     * 根据分页对象得到当前页面要展示的数据
     * @param pageBean
     * @param orderNum
     * @return
     */
    @Select("select * from s_order where orderNum like concat('%',#{orderNum},'%') limit " +
            "${(pageBean.curPage-1)*pageBean.maxSize},#{pageBean.maxSize}")
    List<Order> getOrdersByLike(@Param("pageBean") PageBean pageBean, @Param("orderNum") String orderNum);

    /**
     * 设置配送员
     * @param order
     * @return
     */
    @Update("update s_order set chargerid=#{chargerid}," +
            "chargername=#{chargername}," +
            "chargerphone=#{chargerphone} " +
            "where orderId=#{orderId}")
    boolean updateChargerToOrder(Order order);

    /**
     * 改变订单状态为发货
     * @param order
     * @return
     */
    @Update("update s_order set orderStatus=#{orderStatus} where orderId=#{orderId}")
    boolean updateOrderStatus(Order order);

    /**
     * 获取未发货并且根据订单编号得到的订单数量
     * @param orderStatus
     * @param orderNum
     * @return
     */
    @Select("select count(1) as count from s_order where orderStatus=#{orderStatus} and orderNum like concat('%',#{orderNum},'%')")
    long getCountByWaitOrderNum(@Param("orderStatus") int orderStatus, @Param("orderNum") String orderNum);

    /**
     * 获取未发货并且根据订单编号和分页对象得到的当前页面要展示订单集合信息
     * @param pageBean
     * @param orderStatus
     * @param orderNum
     * @return
     */
    @Select("select * from s_order where orderStatus=#{orderStatus} and orderNum like concat('%',#{orderNum},'%') " +
            "limit ${(pageBean.curPage-1)*pageBean.maxSize},#{pageBean.maxSize}")
    List<Order> getOrdersByWaitLike(@Param("pageBean") PageBean pageBean,@Param("orderStatus") int orderStatus,@Param("orderNum")String orderNum);
}
