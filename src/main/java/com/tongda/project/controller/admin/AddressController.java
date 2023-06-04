package com.tongda.project.controller.admin;

import com.tongda.project.bean.Address;
import com.tongda.project.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-01 9:33
 */
@RestController
public class AddressController {
    private static final String EDIT_ADDRESS_PATH = "addressManage/addressEdit.jsp";
    @Autowired
    private AddressService addressService;

    /**
     * 地址管理
     * @param request
     * @param response
     */
    @RequestMapping("/jsp/admin/AddressServlet")
    public void address(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action){
            case "edit":
                editAddress(request,response);
                break;
            case "update":
                updateAddress(request,response);
                break;
        }
    }

    /**
     * 更新配送区域
     * @param request
     * @param response
     */
    private void updateAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取省份和地区
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        //封装成Address对象
        Address address = new Address(province, city);
        if (addressService.updateAddress(address)){
            //修改成功,将提示信息存入request域中
            request.setAttribute("addressMessage","修改成功~");
        }else{
            //修改失败
            request.setAttribute("addressMessage","修改失败!");
        }
        editAddress(request, response);
    }

    /**
     * 获取地址信息并传入地址编辑页面
     * @param request
     * @param response
     */
    private void editAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Address address = addressService.getAddress();
        //将信息存入request域中
        request.setAttribute("address",address);
        //转发到编辑页面
        request.getRequestDispatcher(EDIT_ADDRESS_PATH).forward(request,response);
    }
}
