package com.tongda.project.controller.admin;

import com.tongda.project.bean.Charger;
import com.tongda.project.bean.PageBean;
import com.tongda.project.service.ChargerService;
import com.tongda.project.util.RanUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-03 21:46
 */
@RestController
@RequestMapping("/jsp/admin")
public class ChargerController {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String CHARGER_LIST_PATH = "chargerManage/chargerList.jsp";
    private static final String CHARGER_ADD_PATH = "chargerManage/chargerAdd.jsp";
    private static final String CHARGER_EDIT_PATH = "chargerManage/chargerEdit.jsp";
    @Resource
    private ChargerService chargerService;

    /**
     * 配送员管理
     */
    @RequestMapping("/ChargerServlet")
    public void Charger(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action){
            case "list":
                chargerList(request,response);
                break;
            case "add":
                chargerAdd(request,response);
                break;
            case "edit":
                chargerEdit(request,response);
                break;
            case "update":
                chargerUpdate(request,response);
                break;
            case "del":
                chargerDel(request,response);
                break;
        }
    }

    /**
     * 删除配送员信息
     * @param request
     * @param response
     */
    private void chargerDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取选中的id
        int chargerId = Integer.parseInt(request.getParameter("id"));
        if (chargerService.delCharger(chargerId)){
            //删除成功
            request.setAttribute("chargerMessage","删除成功~");
            //刷新列表信息
            chargerList(request,response);
        }else{
            //删除失败
            request.setAttribute("chargerMessage","删除失败!");
            chargerList(request,response);
        }
    }

    /**
     * 修改配送员信息
     * @param request
     * @param response
     */
    private void chargerUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        Charger charger = new Charger(
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"),
                request.getParameter("phone")
        );
        if (chargerService.updateCharger(charger)){
            //修改成功
            //存入信息
            request.setAttribute("chargerMessage","修改成功~");
            //跳转到配送员列表
            chargerList(request,response);
        }else{
            //修改失败
            request.setAttribute("chargerMessage","修改失败!");
            //回到修改页面
            request.getRequestDispatcher(CHARGER_EDIT_PATH).forward(request,response);
        }
    }

    /**
     * 为修改页面传输数据
     * @param request
     * @param response
     */
    private void chargerEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取选中的配送员id
        int chargerId = Integer.parseInt(request.getParameter("id"));
        Charger charger = chargerService.getChargerById(chargerId);
        //存入信息
        request.setAttribute("charger",charger);
        //跳转修改页面
        request.getRequestDispatcher(CHARGER_EDIT_PATH).forward(request,response);
    }

    /**
     * 添加配送员
     * @param request
     * @param response
     */
    private void chargerAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取信息
        Charger charger = new Charger(
                request.getParameter("name"),
                request.getParameter("phone")
        );
        //设置配送员编号
        charger.setNo(RanUtil.getChargerNo());
        if (chargerService.addCharger(charger)){
            //添加成功
            //存入信息
            request.setAttribute("chargerMessage","添加成功~");
            //跳转配送员列表页面
            chargerList(request,response);
        }else{
            //添加失败
            request.setAttribute("chargerMessage","添加失败!");
            //返回添加页面
            request.getRequestDispatcher(CHARGER_ADD_PATH).forward(request,response);
        }
    }

    /**
     * 配送员列表
     * @param request
     * @param response
     */
    private void chargerList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取配送员信息集合
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,chargerService.getAllCount());
        //存入信息
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("chargerList",chargerService.getChargersByPage(pageBean));
        //跳转配送员列表页面
        request.getRequestDispatcher(CHARGER_LIST_PATH).forward(request,response);
    }
}
