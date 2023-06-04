package com.tongda.project.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongda.project.bean.Admin;
import com.tongda.project.bean.PageBean;
import com.tongda.project.service.AdminService;
import com.tongda.project.util.SaltMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-01 10:12
 */
@RestController
public class AdminManageController {
    private static final int MAX_PAGE_SIZE = 5;
    private static final String ADMIN_LIST_PATH = "adminManage/adminList.jsp";
    private static final String ADMIN_ADD_PATH = "adminManage/adminAdd.jsp";
    private static final String ADMIN_EDIT_PATH = "adminManage/adminEdit.jsp";
    @Autowired
    private AdminService adminService;

    /**
     * 管理员管理
     */
    @RequestMapping("/jsp/admin/AdminManageServlet")
    public void adminManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action){
            case "list":
                adminList(request,response);
                break;
            case "add":
                adminAdd(request,response);
                break;
            case "find":
                adminAddAjaxUsername(request,response);
                break;
            case "edit":
                adminEdit(request,response);
                break;
            case "update":
                adminUpdate(request,response);
                break;
            case "del":
                adminDel(request,response);
                break;
            case "batDel":
                adminBatDel(request,response);
                break;
        }
    }

    /**
     * 批量删除
     * @param request
     * @param response
     */
    private void adminBatDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取ids
        String ids = request.getParameter("ids");
        String[] idsArr = ids.split(",");
        int[] idsArrInt = new int[idsArr.length];
        for (int i = 0; i < idsArrInt.length; i++) {
            idsArrInt[i] = Integer.parseInt(idsArr[i]);
        }
        //设置批量删除状态标志，默认为正确的
        boolean flag = true;
        for (int id : idsArrInt) {
            if (!adminService.delAdmin(id)){
                flag = false;
            }
        }
        if (flag){
            //批量删除成功
            request.setAttribute("adminMessage","批量删除成功~");
            adminList(request,response);
        }else {
            //批量删除失败
            request.setAttribute("adminMessage","批量删除失败!");
            adminList(request,response);
        }
    }

    /**
     * 删除单个管理员信息
     * @param request
     * @param response
     */
    private void adminDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取管理员id
        int adminId = Integer.parseInt(request.getParameter("id"));
        if (adminService.delAdmin(adminId)){
            //删除成功
            request.setAttribute("adminMessage","删除成功~");
            adminList(request,response);
        }else{
            //删除失败
            request.setAttribute("adminMessage","删除失败!");
            adminList(request,response);
        }
    }

    /**
     * 更新管理员
     * @param request
     * @param response
     */
    private void adminUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取参数
        String passWord = request.getParameter("passWord");
        String name = request.getParameter("name");
        int adminId = Integer.parseInt(request.getParameter("id"));
        //封装成Admin对象
        Admin admin = new Admin(adminId, passWord, name);
        //这里要考虑管理员可能修改自己的信息，那么此时修改完应该重新登陆
        //首先得到session中的用户信息
        Admin adminUser = (Admin) request.getSession().getAttribute("adminUser");
        if (adminUser.getId() == adminId){
            //选中当前登录用户信息修改
            if (adminService.updateAdmin(admin)){
                //修改成功
                //重新登陆
                //原谅我开始写shi山了,这个东西根据目前尝试的方法来讲写法最简单
                response.setContentType("text/html");
                response.getWriter().write(
                        "<script>" +
                                "alert('修改成功,请重新登录');" +
                                "window.location.href = '../../LoginOutServlet';" +
                                "</script>");

            }else{
                //修改失败
                request.setAttribute("adminMessage","修改失败!");
                //跳转到修改页面
                request.getRequestDispatcher(ADMIN_EDIT_PATH).forward(request,response);
            }
        }else{
            //选中其他管理员用户修改
            if (adminService.updateAdmin(admin)){
                //修改成功
                //存消息，跳转页面
                request.setAttribute("adminMessage","修改成功~");
                adminList(request,response);
            }else{
                //修改失败
                request.setAttribute("adminMessage","修改失败!");
                //跳转到修改页面
                request.getRequestDispatcher(ADMIN_EDIT_PATH).forward(request,response);
            }
        }
    }

    /**
     * 跳转编辑页面,并提供信息
     * @param request
     * @param response
     */
    private void adminEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取管理员id
        int adminId = Integer.parseInt(request.getParameter("id"));
        Admin admin = adminService.findAdminById(adminId);
        request.setAttribute("adminInfo",admin);
        //跳转修改页面
        request.getRequestDispatcher(ADMIN_EDIT_PATH).forward(request,response);
    }

    /**
     * 添加时校验用户名
     * @param request
     * @param response
     */
    private void adminAddAjaxUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String userName = request.getParameter("param");
        HashMap<String, Object> map = new HashMap<>();
        if (adminService.findAdminByUserName(userName)){
            //找到了，用户名不能使用
            map.put("info","用户名已存在!");
            map.put("status","n");
        }else{
            //没找到，可以使用
            map.put("info","用户名可以使用~");
            map.put("status","y");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(json);
    }

    /**
     * 添加管理员
     * @param request
     * @param response
     */
    private void adminAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求体信息
        Admin admin = new Admin(
                request.getParameter("userName"),
                request.getParameter("passWord"),
                request.getParameter("name")
        );
        //添加管理员
        if(adminService.addAdmin(admin)){
            //添加成功
            //将信息存入request域中
            request.setAttribute("adminMessage","添加成功~");
            //调用列表函数
            adminList(request,response);
        }else{
            //添加失败
            request.setAttribute("adminMessage","添加失败!");
            request.getRequestDispatcher(ADMIN_ADD_PATH).forward(request,response);
        }
    }

    /**
     * 管理员列表
     * @param request
     * @param response
     */
    private void adminList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到管理员信息集合
        //首先设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,adminService.allAdminCount());
        //将得到的信息放入域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("adminList",adminService.getAdminsByPage(pageBean));
        //跳转列表页面
        request.getRequestDispatcher(ADMIN_LIST_PATH).forward(request,response);
    }
}
