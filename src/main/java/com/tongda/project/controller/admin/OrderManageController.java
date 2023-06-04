package com.tongda.project.controller.admin;

import com.tongda.project.bean.Charger;
import com.tongda.project.bean.Order;
import com.tongda.project.bean.OrderItem;
import com.tongda.project.bean.PageBean;
import com.tongda.project.service.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-03 21:35
 */
@RestController
@RequestMapping("/jsp/admin")
public class OrderManageController {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String ORDER_LIST_PATH = "orderManage/orderlist.jsp";
    private static final String ORDER_DETAIL_PATH = "orderManage/orderDetail.jsp";
    private static final String ORDER_OP_PATH = "orderManage/orderOp.jsp";
    private static final String ORDER_CHARGER_PATH = "orderManage/orderCharger.jsp";
    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private FlowService flowService;
    @Resource
    private UpLoadImgService upLoadImgService;
    @Resource
    private ChargerService chargerService;

    @RequestMapping("/OrderManageServlet")
    public void OrderManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                orderList(request, response);
                break;
            case "detail":
                orderDetail(request, response);
                break;
            case "delete":
                orderDel(request, response);
                break;
            case "seach":
                orderSearch(request, response);
                break;
         /**====================订单处理列表========================*/
            case "processing":
                processList(request,response);
                break;
            case "setCharger":
                setCharger(request,response);
                break;
            case "updateCharger":
                updateCharger(request,response);
                break;
            case "ship":
                orderShip(request,response);
                break;
            case "seach1":
                orderSearch1(request,response);
                break;
        }
    }

    /**
     * 待处理订单页面搜索
     * @param request
     * @param response
     */
    private void orderSearch1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单号
        String orderNum = request.getParameter("ordername");
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,orderService.getCountByWaitOrderNum(1,orderNum));
        //存信息
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("orderList",orderService.getOrdersByWaitLike(pageBean,1,orderNum));
        //跳转页面
        request.getRequestDispatcher(ORDER_OP_PATH).forward(request,response);
    }

    /**
     * 订单发货
     * @param request
     * @param response
     */
    private void orderShip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到要发货的订单id
        int orderId = Integer.parseInt(request.getParameter("id"));
        //根据订单id得到对应的订单对象
        Order order = orderService.getOrderById(orderId);
        //修改订单状态
        order.setOrderStatus(2);
        //修改进数据库
        orderService.updateOrderStatus(order);
        //存入信息
        request.setAttribute("orderMessage","订单已发货~");
        //跳转待处理订单列表
        processList(request,response);
    }

    /**
     * 设置配送员
     * @param request
     * @param response
     */
    private void updateCharger(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当前订单id
        int orderId = Integer.parseInt(request.getParameter("id"));
        //根据订单id得到订单信息
        Order order = orderService.getOrderById(orderId);
        //获取设置的配送员id
        int chargerId = Integer.parseInt(request.getParameter("charger"));
        //根据配送员id得到配送员信息
        Charger charger = chargerService.getChargerById(chargerId);
        //在订单中设置配送员信息
        order.setChargerid(chargerId);
        order.setChargername(charger.getName());
        order.setChargerphone(charger.getPhone());
        //更新到数据库中
        orderService.updateChargerToOrder(order);
        request.setAttribute("orderMessage","配送员已设置~");
        //跳转到待处理订单列表页面
        processList(request,response);
    }

    /**
     * 设置配送员(将信息传入设置配送员页面)
     * @param request
     * @param response
     */
    private void setCharger(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到选中订单id
        int orderId = Integer.parseInt(request.getParameter("id"));
        //查询订单信息
        Order order = orderService.getOrderById(orderId);
        //查询配送员列表信息
        List<Charger> charger = chargerService.getChargers();
        //存入信息
        request.setAttribute("order",order);
        request.setAttribute("charger",charger);
        //跳转到配送员设置页面
        request.getRequestDispatcher(ORDER_CHARGER_PATH).forward(request,response);
    }

    /**
     * 订单处理列表
     * @param request
     * @param response
     */
    private void processList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage, MAX_PAGE_SIZE, orderService.allCountOrderByStatus(1));
        //存入信息
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("orderList", orderService.getallOrderByPageAndStatus(pageBean,1));
        //跳转到列表页面
        request.getRequestDispatcher(ORDER_OP_PATH).forward(request, response);
    }
/**===========================订单处理列表(上面)=======================================*/
    /**
     * 订单列表搜索
     * @param request
     * @param response
     */
    private void orderSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取输入的订单号
        String orderNum;
        orderNum = request.getParameter("ordername");
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,orderService.getCountByOrderNum(orderNum));
        //填入信息
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("orderList",orderService.getOrdersByLike(pageBean,orderNum));
        //跳转页面
        request.getRequestDispatcher(ORDER_LIST_PATH).forward(request,response);
    }

    /**
     * 删除单个订单
     * @param request
     * @param response
     */
    private void orderDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //这里删除数据库订单id所对应的订单信息,不删除订单项(后面有需要再补上)
        int orderId = Integer.parseInt(request.getParameter("id"));
        if (orderService.delOrderById(orderId)){
            //删除成功
            //存入信息
            request.setAttribute("orderMessage","删除成功~");
            //跳转到订单列表
            orderList(request,response);
        }else{
            //删除失败
            request.setAttribute("orderMessage","删除失败!");
            orderList(request,response);
        }
    }

    /**
     * 查看订单详情
     *
     * @param request
     * @param response
     */
    private void orderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台的订单id
        int orderId = Integer.parseInt(request.getParameter("id"));
        //根据订单id得到订单信息
        Order order = orderService.getOrderById(orderId);
        //根据订单里的用户id得到用户信息
        order.setUser(userService.findUserById(order.getUserId()));
        //根据订单id得到orderItem信息
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
        //在orderItem中装载商品对象
        orderItems.forEach((orderItem) -> {
            orderItem.setFlow(flowService.getFlowByFlowId(orderItem.getFlowId()));
            //在商品对象中装载图片信息
            orderItem.getFlow().setUpLoadImg(upLoadImgService.getUpLoadImgById(orderItem.getFlow().getImgId()));
        });
        //装在orderItems
        order.setoItem(orderItems);
        //存入消息
        request.setAttribute("order", order);
        //跳转到详情页面
        request.getRequestDispatcher(ORDER_DETAIL_PATH).forward(request, response);
    }

    /**
     * 订单列表
     *
     * @param request
     * @param response
     */
    private void orderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage, MAX_PAGE_SIZE, orderService.allCountOrder());
        //存入信息
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("orderList", orderService.getallOrderByPage(pageBean));
        //跳转到列表页面
        request.getRequestDispatcher(ORDER_LIST_PATH).forward(request, response);
    }
}
