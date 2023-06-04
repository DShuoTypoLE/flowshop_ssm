package com.tongda.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongda.project.bean.Cart;
import com.tongda.project.bean.CartItem;
import com.tongda.project.bean.Flow;
import com.tongda.project.service.CatalogService;
import com.tongda.project.service.FlowService;
import com.tongda.project.service.UpLoadImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 16:35
 */
@RestController
public class CartController {
    private static final String CART_PATH = "jsp/flow/cart.jsp";
    @Autowired
    private FlowService flowService;
    @Autowired
    private UpLoadImgService uploadImgService;
    @Autowired
    private CatalogService catalogService;

    /**
     * 购物车
     */
    @RequestMapping("/CartServlet")
    public void cart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                addCart(request,response);
                break;
            case "delItem":
                delItemCart(request,response);
                break;
            case "delAll":
                delAllCart(request,response);
                break;
            case "changeIn":
                changeInCart(request,response);
                break;
        }
    }

    /**
     * 页面数量逻辑的处理
     * @param request
     * @param response
     */
    private void changeInCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取flowId和quantity
        int flowId = Integer.parseInt(request.getParameter("flowId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        //获取购物车对象
        Cart shopCart = (Cart) request.getSession().getAttribute("shopCart");
        //获取CartItem对象
        CartItem cartItem = shopCart.getMap().get(flowId);
        //更新数量
        cartItem.setQuantity(quantity);

        HashMap<String, Object> map = new HashMap<>();
        map.put("subtotal",cartItem.getSubtotal());
        map.put("quantity",cartItem.getQuantity());
        map.put("totPrice",shopCart.getTotPrice());
        map.put("totQuan",shopCart.getTotQuan());
        String json = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(json);
    }

    /**
     * 清空购物车
     * @param request
     * @param response
     */
    private void delAllCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("shopCart");
        //重定向到购物车页面
        response.sendRedirect(CART_PATH);
    }

    /**
     * 删除一项商品
     * @param request
     * @param response
     */
    private void delItemCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取鲜花id
        int flowId = Integer.parseInt(request.getParameter("id"));
        //获取购物车对象
        Cart shopCart = (Cart) request.getSession().getAttribute("shopCart");

        if (shopCart.getMap().containsKey(flowId)){
            shopCart.getMap().remove(flowId);
        }
        //重定向到Cart页面
        response.sendRedirect(CART_PATH);
    }

    /**
     * 添加购物车
     * @param request
     * @param response
     */
    private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取鲜花id
        int flowId = Integer.parseInt(request.getParameter("flowId"));
        //通过鲜花id查询鲜花信息
        Flow flow = flowService.getFlowByFlowId(flowId);
        //这里将图片和分类信息给商品
        flow.setUpLoadImg(uploadImgService.getUpLoadImgById(flow.getImgId()));
        flow.setCatalog(catalogService.getCatalogByCatalogId(flow.getCatalogId()));
        //从session中拿购物车对象
        HttpSession session = request.getSession();
        Cart shopCart = (Cart) session.getAttribute("shopCart");

        //购物车为空则创建一个
        if (shopCart == null){
            shopCart = new Cart();
            //将这个东西放入session中
            session.setAttribute("shopCart",shopCart);
        }

        //将商品加入购物车中
        shopCart.addFlow(flow);
        //将页面头部数量更新
        response.getWriter().write(shopCart.getTotQuan());
    }
}
