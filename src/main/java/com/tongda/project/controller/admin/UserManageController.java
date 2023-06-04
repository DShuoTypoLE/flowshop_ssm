package com.tongda.project.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongda.project.bean.PageBean;
import com.tongda.project.bean.User;
import com.tongda.project.service.UserService;
import com.tongda.project.util.SaltMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-01 22:47
 */
@RestController
public class UserManageController {

    private static final int MAX_PAGE_SIZE = 8;
    private static final String USER_LIST_PATH = "userManage/userList.jsp";
    private static final String USER_DETAIL_PATH = "userManage/userDetail.jsp";
    private static final String USER_ADD_PATH = "userManage/userAdd.jsp";
    private static final String USER_EDIT_PATH = "userManage/userEdit.jsp";
    @Autowired
    private UserService userService;
    /**
     * 用户管理
     * @param request
     * @param response
     */
    @RequestMapping("/jsp/admin/UserManageServlet")
    public void userManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action){
            case "list":
                userList(request,response);
                break;
            case "detail":
                userDetail(request,response);
                break;
            case "find":
                userAjaxName(request,response);
                break;
            case "add":
                addUser(request,response);
                break;
            case "edit":
                editUser(request,response);
                break;
            case "update":
                updateUser(request,response);
                break;
            case "del":
                delOneUser(request,response);
                break;
            case "batDel":
                batDelUser(request,response);
                break;
            case "seach":
                searchUser(request,response);
                break;
        }
    }

    /**
     * 根据输入用户名模糊查询
     * @param request
     * @param response
     */
    private void searchUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取输入的用户名
        String username = request.getParameter("username");
        if (username != null && !"".equals(username)){
            //username不为空
            //设置分页
            int curPage = 1;
            String page = request.getParameter("page");
            if (page != null){
                curPage = Integer.parseInt(page);
            }

            //创建分页对象
            PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,userService.getUserCountByLike(username));
            //存入信息
            request.setAttribute("pageBean",pageBean);
            request.setAttribute("userList",userService.getUserByLike(pageBean,username));
            //跳转页面
            request.getRequestDispatcher(USER_LIST_PATH).forward(request,response);
        }else{
            //为空则进行全表查询
            userList(request,response);
        }
    }

    /**
     * 批量删除
     * @param request
     * @param response
     */
    private void batDelUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取选中的用户ids
        String ids = request.getParameter("ids");
        String[] idsArr = ids.split(",");
        int[] idsArrInt = new int[idsArr.length];
        for (int i = 0; i < idsArr.length; i++) {
            idsArrInt[i] = Integer.parseInt(idsArr[i]);
        }
        //删除用户
        //设置状态
        boolean flag = true;
        for (int id : idsArrInt) {
            if (!userService.userDel(id)){
                flag = false;
            }
        }
        if (flag){
            //批量删除成功
            //存入消息
            request.setAttribute("userMessage","批量删除成功~");
            //跳转到用户列表
            userList(request,response);
        }else{
            //批量删除失败
            request.setAttribute("userMessage","批量删除失败!");
            //跳转到用户列表
            userList(request,response);
        }
    }

    /**
     * 删除单个用户信息
     * @param request
     * @param response
     */
    private void delOneUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当前选中用户id
        int userId = Integer.parseInt(request.getParameter("id"));
        if (userService.userDel(userId)){
            //删除成功
            //存入提示信息
            request.setAttribute("userMessage","删除成功~");
            //跳转到用户列表
            userList(request,response);
        }else{
            //删除失败
            request.setAttribute("userMessage","删除失败！");
            //跳转到用户列表
            userList(request,response);
        }
    }

    /**
     * 修改用户信息
     * @param request
     * @param response
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取修改信息
        User user = new User(
                Integer.parseInt(request.getParameter("userId")),
                SaltMD5Util.generateSaltPassword(request.getParameter("passWord")),
                request.getParameter("name"),
                request.getParameter("sex"),
                Integer.parseInt(request.getParameter("age")),
                request.getParameter("tell"),
                request.getParameter("address"),
                request.getParameter("enabled")
        );
        //修改信息
        if (userService.updateUser(user)){
            //修改成功
            //存入信息
            request.setAttribute("userMessage","修改成功~");
            //跳转列表
            userList(request,response);
        }else{
            //修改失败
            request.setAttribute("userMessage","修改失败!");
            //将信息带回更新页面
            request.getRequestDispatcher(USER_EDIT_PATH).forward(request,response);
        }
    }

    /**
     * 给修改页面传数据
     * @param request
     * @param response
     */
    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当前用户id
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(userId);
        //将信息存入request域中
        request.setAttribute("userInfo",user);
        //转发到更新页面
        request.getRequestDispatcher(USER_EDIT_PATH).forward(request,response);
    }

    /**
     * 添加用户
     * @param request
     * @param response
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        User user = new User(
                request.getParameter("userName"),
                SaltMD5Util.generateSaltPassword(request.getParameter("passWord")),
                request.getParameter("name"),
                request.getParameter("sex"),
                Integer.parseInt(request.getParameter("age")),
                request.getParameter("tell"),
                request.getParameter("address")
        );
        //设置用户状态
        user.setEnabled("y");
        if (userService.userAdd(user)){
            //添加成功
            //存提示信息到域中
            request.setAttribute("userMessage","添加成功~");
            //跳转到用户列表
            userList(request,response);
        }else{
            //添加失败
            request.setAttribute("userMessage","添加失败!");
            request.getRequestDispatcher(USER_ADD_PATH).forward(request,response);
        }
    }

    /**
     * 验证用户名是否可用
     * @param request
     * @param response
     */
    private void userAjaxName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取用户名
        String userName = request.getParameter("param");
        HashMap<String, Object> map = new HashMap<>();
        if (userService.findUserByUsername(userName)){
            //用户名存在,不可用
            map.put("info","用户名已存在!");
            map.put("status","n");
        }else{
            //用户名不存在，可用使用
            map.put("info","用户名可用~");
            map.put("status","y");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(json);
    }

    /**
     * 用户详情
     * @param request
     * @param response
     */
    private void userDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户id
        int userId = Integer.parseInt(request.getParameter("id"));
        //根据id查询用户信息
        User user = userService.findUserById(userId);
        //存入request域
        request.setAttribute("userInfo",user);
        //跳转到详情页面
        request.getRequestDispatcher(USER_DETAIL_PATH).forward(request,response);
    }

    /**
     * 用户列表
     * @param request
     * @param response
     */
    private void userList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,userService.getUserCount());
        //将信息存入域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("userList",userService.getUserByPage(pageBean));
        //转发到用户列表页面
        request.getRequestDispatcher(USER_LIST_PATH).forward(request,response);
    }
}
