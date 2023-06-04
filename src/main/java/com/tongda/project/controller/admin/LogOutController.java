package com.tongda.project.controller.admin;

import com.tongda.project.bean.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-01 8:54
 */
@Controller
public class LogOutController {

    /**
     * 管理员退出登录
     * @param request
     * @return
     */
    @RequestMapping("/LoginOutServlet")
    public String logOut(HttpServletRequest request){
        //获取当前登录信息
        Admin adminUser = (Admin) request.getSession().getAttribute("adminUser");
        if (adminUser != null){
            request.getSession().removeAttribute("adminUser");
        }
        //这里要重定向，并且是到index页面
        return "redirect:/jsp/admin/index.jsp";
    }
}
