package com.tongda.project.controller.admin;

import com.tongda.project.bean.Admin;
import com.tongda.project.service.AdminService;
import com.tongda.project.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 21:34
 */
@RestController
public class LoginController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/jsp/admin/LoginServlet")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户名、密码
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        //封装成admin对象
        Admin admin = new Admin(userName, passWord);
        ArrayList<String> list = new ArrayList<>();
        //查询是否登录
        Admin admin1 = adminService.adminLogin(admin);
        if (admin1 != null){
            //登录成功
            //更新最后登陆时间
            //将最后登录时间更新
            //得到新的时间
            Timestamp newTime = DateUtil.getTimestamp();
            adminService.updateLastTime(newTime,admin1.getId());
            //将登录信息存入session中
            request.getSession().setAttribute("adminUser",admin1);
            //重定向到首页
            response.sendRedirect("index.jsp");
            return;
        }else{
            //登陆失败
            list.add("用户名或密码错误!");
        }
        request.setAttribute("infoList",list);
        //跳转到登录界面
        request.getRequestDispatcher("login.jsp").forward(request,response);
    }
}
