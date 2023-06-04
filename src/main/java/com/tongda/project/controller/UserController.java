package com.tongda.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongda.project.bean.User;
import com.tongda.project.service.UserService;
import com.tongda.project.util.SaltMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 23:13
 */
@Controller
public class UserController {
    private static final String LOGIN_PATH = "jsp/flow/login.jsp";
    private static final String REG_PATH = "jsp/flow/reg.jsp";
    private static final String INDEX_PATH = "jsp/flow/index.jsp";

    //session中用户的标识
    private static final String LANDING = "landing";
    @Autowired
    private UserService userService;

    /**
     * 总的接收器
     * @param request
     */
    @RequestMapping("/UserServlet")
    @ResponseBody
    public void user(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //获取执行动作
        String action = request.getParameter("action");
        switch (action) {
            case "reg":
                userRegister(request,response);
                break;
            case "ajaxFind":
                ajaxFind(request,response);
                break;
            case "login":
                userLogin(request,response);
                break;
            case "off":
                userLogOut(request,response);
                break;
            case "landstatus":
                userLandStatus(request,response);
                break;
            case "ajlogin":
                userAjlogin(request,response);
                break;
        }
    }

    /**
     * 模态框登录
     * @param request
     * @param response
     */
    private void userAjlogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取用户名和密码
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        //封装成对象
        User user = new User(userName, passWord);
        //根据user信息查询对象是否存在
        User user1 = userService.findUserByUser(user);
        HashMap<String, Object> map = new HashMap<>();

        if (user1 != null){
            //用户存在
            //接下来判断密码是否相同
            if (!SaltMD5Util.verifySaltPassword(passWord,user1.getUserPassWord())){
                //密码错误
                map.put("info","密码错误!");
            }else{
                //密码正确
                //判断用户是否被锁定
                if ("y".equals(user1.getEnabled())){
                    //用户可用使用
                    map.put("status", "y");
                    //将用户信息存入LANDING中
                    request.getSession().setAttribute(LANDING,user1);
                }else {
                    //用户被锁定
                    map.put("info","用户被锁定,请联系管理员!");
                }
            }
        }else {
            //用户不存在
            map.put("info","用户名错误!");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(json);
    }

    /**
     * 用户在结算时判断其状态是否为登陆状态
     * @param request
     * @param response
     */
    private void userLandStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取当前登录状态
        User user = (User) request.getSession().getAttribute(LANDING);
        HashMap<String, Object> map = new HashMap<>();
        if (user != null){
            //如果用户不存在,说明处于登陆状态
            map.put("status","y");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(json);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     */
    private void userLogOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //得到session的用户信息
        User user = (User) request.getSession().getAttribute(LANDING);
        if (user != null){
            //用户不为空则删除用户信息
            request.getSession().removeAttribute(LANDING);
            //重定向回首页
            response.sendRedirect(INDEX_PATH);
        }
    }

    /**
     * 注册时验证用户名
     * @param request
     * @param response
     */
    private void ajaxFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //写法二
        //获取参数
        String username = request.getParameter("param");
        HashMap<String, Object> map = new HashMap<>();
        if (userService.findUserByUsername(username)){
            //找到了
            map.put("info","用户名已存在!");
            map.put("status","n");
        }else {
            //没找到
            map.put("info","用户名可用~");
            map.put("status","y");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

//        //获取用户名
//        String username = request.getParameter("username");
//        HashMap<String, Object> map = new HashMap<>();
//        if(username == "" || username==null){
//            map.put("info","status");
//        }else{
//            if (userService.findUserByUsername(username)){
//                //找到了,用户存在
//                map.put("info","true");
//            }else{
//                //没找到，可以使用
//                map.put("info","false");
//            }
//        }
//        String json = new ObjectMapper().writeValueAsString(map);
//        response.getWriter().write(json);
    }


    /**
     * 用户登录
     * @param request
     * @param response
     */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传过来的用户名和密码
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        //封装成对象
        User user = new User(userName, passWord);
        //根据用户名查找用户是否存在
        User user1 = userService.findUserByUser(user);
        if (user1 != null){
            //用户存在
            //然后判断密码是否正确
            if (!SaltMD5Util.verifySaltPassword(passWord,user1.getUserPassWord())){
                //密码不正确
                //存入错误提示信息
                request.setAttribute("infoList","密码错误!");
                //跳转到登录页面
                request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
            }else {
                //密码正确
                //验证用户状态是否可用
                if("y".equals(user1.getEnabled())){
                    //当前状态可用
                    //将当前登录用户信息存入session域中
                    request.getSession().setAttribute(LANDING,user1);
                    //跳转到首页
                    request.getRequestDispatcher(INDEX_PATH).forward(request,response);
                }else {
                    //当前状态不可用
                    //提示当前用户被锁定
                    request.setAttribute("infoList","当前用户被锁定,请联系管理员~");
                    //跳转到登陆界面
                    request.getRequestDispatcher(LOGIN_PATH).forward(request,response);
                }
            }
        }else{
            //用户不存在
            //存入错误提示信息
            request.setAttribute("infoList","用户名错误!");
            //跳转回登录页面
            request.getRequestDispatcher(LOGIN_PATH).forward(request,response);
        }
    }

    /**
     * 用户注册
     */
    private void userRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String passWord = SaltMD5Util.generateSaltPassword(request.getParameter("passWord"));
        User user = new User(
                request.getParameter("userName"),
                passWord,
                request.getParameter("name"),
                request.getParameter("sex"),
                Integer.parseInt(request.getParameter("age")),
                request.getParameter("tell"),
                request.getParameter("address")
        );
        //设置用户状态默认为启用y
        user.setEnabled("y");
        if (userService.userAdd(user)){
            //注册成功,并给出提示信息infoList
            request.setAttribute("infoList","注册成功~");
            //跳转登录界面
            request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
        }else {
            //注册失败,还是跳到注册界面,并给出提示信息infoList
            request.setAttribute("infoList","注册失败");
            request.getRequestDispatcher(REG_PATH).forward(request, response);
        }
    }
}
