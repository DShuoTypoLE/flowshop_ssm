package com.tongda.project.controller;

import com.tongda.project.bean.*;
import com.tongda.project.service.FlowService;
import com.tongda.project.service.OrderItemService;
import com.tongda.project.service.OrderService;
import com.tongda.project.service.UpLoadImgService;
import com.tongda.project.util.DateUtil;
import com.tongda.project.util.RanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 18:14
 */
@RestController
public class OrderController {

    private static final String LANDING = "landing";
    private static final String CART_PATH = "jsp/flow/cart.jsp";
    private static final String ORDER_SUCCESS_PATH = "jsp/flow/ordersuccess.jsp";
    private static final String LOGIN_PATH = "jsp/flow/login.jsp";
    private static final String MY_ORDER_LIST_PATH = "jsp/flow/myorderlist.jsp";
    private static final int MAX_PAGE_SIZE = 5;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private UpLoadImgService upLoadImgService;

    /**
     * 订单
     */
    @RequestMapping("/OrderServlet")
    public void order(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action){
            case "subOrder":
                subOrder(request,response);
                break;
            case "list":
                myOrderList(request,response);
                break;
            case "ship":
                orderShip(request,response);
                break;
        }
    }

    /**
     * 确认收货
     * @param request
     * @param response
     */
    private void orderShip(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取订单id
        int orderId = Integer.parseInt(request.getParameter("id"));
        Order order = orderService.getOrderById(orderId);
        order.setOrderStatus(3);
        orderService.updateOrderStatus(order);
        //跳转到我的订单列表页面
        myOrderList(request,response);
    }

    /**
     * 查看我的订单列表
     * @param request
     * @param response
     */
    private void myOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //首先从session中得到登录信息
        User user = (User) request.getSession().getAttribute(LANDING);
        if(user == null){
            //用户未登录则跳转登录页面
            response.sendRedirect(LOGIN_PATH);
        }else{
            //用户已登录
            //设置分页
            int curPage = 1;
            String page = request.getParameter("page");
            if (page != null){
                curPage = Integer.parseInt(page);
            }
            //创建PageBean对象
            PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,orderService.orderCountByUserId(user.getUserId()));
            //根据分页对象得到订单列表
            List<Order> orderList = orderService.getOrderList(pageBean,user.getUserId());
            //将订单项放入订单列表
            for (Order order : orderList) {
                order.setoItem(orderItemService.getOrderItemByOrderId(order.getOrderId()));
                //每个订单项里面要放入商品
                List<OrderItem> orderItems;
                orderItems = order.getoItem();
                for (OrderItem orderItem : orderItems) {
                    orderItem.setFlow(flowService.getFlowByFlowId(orderItem.getFlowId()));
                    orderItem.getFlow().setUpLoadImg(upLoadImgService.getUpLoadImgById(orderItem.getFlow().getImgId()));
                }
            }
            //将信息存入request域中
            request.setAttribute("pageBean",pageBean);
            request.setAttribute("orderList",orderList);
            //将信息转发到我的订单页面
            request.getRequestDispatcher(MY_ORDER_LIST_PATH).forward(request,response);
        }
    }


    /**
     * 提交订单
     * @param request
     * @param response
     */
    private void subOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //提交订单主要就是封装成Order对象
        //收货人信息，商品信息，当前用户信息
        //获取当前用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(LANDING);
        //获取商品信息,首先获取购物车
        Cart shopCart = (Cart) session.getAttribute("shopCart");
        //获取收货人信息
        String shpeople = request.getParameter("shpeople");
        String shphone = request.getParameter("shphone");
        String shaddress = request.getParameter("shaddress");

        //封装成Order
        Order order = new Order();
        //生成订单号和订单日期
        String orderNum = RanUtil.getOrderNum();
        String orderDate = DateUtil.show();
        //开始属性添加
        order.setOrderNum(orderNum);
        order.setUserId(user.getUserId());
        order.setOrderDate(orderDate);
        order.setMoney(shopCart.getTotPrice());
        //'1 订单提交   2 已发货  3确认收货'
        order.setOrderStatus(1);
        order.setShpeople(shpeople);
        order.setShphone(shphone);
        order.setShaddress(shaddress);

        //添加订单
        if (orderService.addOrder(order)){
            //添加成功
            //获取订单编号
            order.setOrderId(orderService.getOrderIdByOrderNum(orderNum));
            //添加订单项
            shopCart.getMap().forEach((flowId,orderItem)->{
                //创建订单项对象
                OrderItem orderItem1 = new OrderItem();
                orderItem1.setFlowId(flowId);
                orderItem1.setOrderId(order.getOrderId());
                orderItem1.setQuantity(orderItem.getQuantity());
                //添加到数据库
                orderItemService.addOrderItem(orderItem1);
            });
            //添加后清空购物车
            session.removeAttribute("shopCart");
            request.setAttribute("orderNum",orderNum);
            request.setAttribute("money",order.getMoney());
            //跳转到成功界面
            request.getRequestDispatcher(ORDER_SUCCESS_PATH).forward(request,response);
        }else{
            //添加失败
            request.setAttribute("suberr","订单提交失败,请重新提交~");
            request.getRequestDispatcher(CART_PATH).forward(request, response);
        }
    }
}
