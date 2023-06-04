package com.tongda.project.controller;

import com.tongda.project.bean.Msg;
import com.tongda.project.bean.User;
import com.tongda.project.service.MsgService;
import com.tongda.project.util.DateUtil;
import com.tongda.project.util.RanUtil;
import org.apache.taglibs.standard.lang.jstl.GreaterThanOrEqualsOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 21:03
 */
@RestController
public class MsgController {
    private static final String LANDING = "landing";
    @Autowired
    private MsgService msgService;

    /**
     * 留言
     */
    @RequestMapping("/MsgServlet")
    public void Msg(HttpServletRequest request, HttpServletResponse response){
        String action = request.getParameter("action");
        switch (action){
            case "add":
                addMsg(request,response);
                break;
        }
    }

    /**
     * 添加留言
     * @param request
     * @param response
     */
    private void addMsg(HttpServletRequest request, HttpServletResponse response) {
        //得到留言内容
        String msg = request.getParameter("contain");
        //获取输入时间
        String inputTime = DateUtil.show();
        //获取输入人员信息
        User user = (User) request.getSession().getAttribute(LANDING);
        String name = user.getName();
        Msg msg1 = new Msg(msg, inputTime, name);
        msgService.addMsg(msg1);
    }
}
