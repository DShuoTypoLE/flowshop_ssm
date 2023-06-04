package com.tongda.project.controller.admin;

import com.tongda.project.bean.Msg;
import com.tongda.project.bean.PageBean;
import com.tongda.project.service.MsgService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author DingSH
 * @version 1.0
 * @Date 2023-06-04 15:27
 */
@RestController
@RequestMapping("/jsp/admin")
public class MsgMangerController {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String MSG_LIST_PATH = "msgManage/msgList.jsp";
    @Resource
    private MsgService msgService;

    @RequestMapping("/MsgServlet")
    public void msgManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action){
            case "list":
                msgList(request,response);
                break;
            case "delete":
                msgDel(request,response);
                break;
        }
    }

    /**
     * 删除留言
     * @param request
     * @param response
     */
    private void msgDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取留言id
        int msgId = Integer.parseInt(request.getParameter("id"));
        if (msgService.delMsg(msgId)){
            //删除成功
            request.setAttribute("msgMessage","删除成功~");
            msgList(request,response);
        }else{
            //删除失败
            request.setAttribute("msgMessage","删除失败!");
            msgList(request,response);
        }
    }

    /**
     * 留言列表
     * @param request
     * @param response
     */
    private void msgList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,msgService.allCount());
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("msgList",msgService.getMsgsByPage(pageBean));
        //跳转到留言列表页面
        request.getRequestDispatcher(MSG_LIST_PATH).forward(request,response);
    }
}
