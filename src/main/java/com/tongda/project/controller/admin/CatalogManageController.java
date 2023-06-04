package com.tongda.project.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.tongda.project.bean.Catalog;
import com.tongda.project.bean.PageBean;
import com.tongda.project.service.CatalogService;
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
 * @Date 2023-06-02 22:22
 */
@RestController
@RequestMapping("/jsp/admin")
public class CatalogManageController {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String CATALOG_LIST_PATH = "flowManage/catalogList.jsp";
    private static final String CATALOG_ADD_PATH = "flowManage/catalogAdd.jsp";
    private static final String CATALOG_EDIT_PATH = "flowManage/catalogEdit.jsp";
    @Autowired
    private CatalogService catalogService;

    /**
     * 分类管理
     * @param request
     * @param response
     */
    @RequestMapping("/CatalogServlet")
    public void CatalogManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action){
            case "list":
                catalogList(request,response);
                break;
            case "find":
                catalogAjax(request,response);
                break;
            case "add":
                catalogAdd(request,response);
                break;
            case "edit":
                catalogEdit(request,response);
                break;
            case "update":
                catalogUpdate(request,response);
                break;
            case "del":
                catalogDel(request,response);
                break;
            case "batDel":
                catalogBatDel(request,response);
                break;
        }
    }

    /**
     * 批量删除
     * @param request
     * @param response
     */
    private void catalogBatDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取选中的id集合
        String ids = request.getParameter("ids");
        String[] idsArr = ids.split(",");
        int[] idsArrInt = new int[idsArr.length];
        for (int i = 0; i < idsArrInt.length; i++) {
            idsArrInt[i] = Integer.parseInt(idsArr[i]);
        }
        //设置删除状态
        boolean flag = true;
        for (int id : idsArrInt) {
            if (!catalogService.delCatalogById(id)){
                flag = false;
            }
        }
        if (flag){
            //全部删除成功
            //存入信息
            request.setAttribute("catalogMessage","批量删除成功~");
            //刷新分类列表页面
            catalogList(request,response);
        }else{
            //删除失败
            request.setAttribute("catalogMessage","批量删除失败!");
            catalogList(request,response);
        }
    }

    /**
     * 删除单个
     * @param request
     * @param response
     */
    private void catalogDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取选中分类id
        int catalogId = Integer.parseInt(request.getParameter("id"));
        if (catalogService.delCatalogById(catalogId)){
            //删除成功
            //存入信息
            request.setAttribute("catalogMessage","删除成功~");
            //刷新分类列表
            catalogList(request,response);
        }else{
            //删除失败
            request.setAttribute("catalogMessage","删除失败!");
            //刷新分类列表
            catalogList(request,response);
        }
    }

    /**
     * 更新分类消息
     * @param request
     * @param response
     */
    private void catalogUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取信息
        Catalog catalog = new Catalog(
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("catalogName")
        );
        //修改信息
        if (catalogService.updateCatalog(catalog)){
            //修改成功
            //存入域中
            request.setAttribute("catalogMessage","修改成功~");
            //跳转分类列表
            catalogList(request,response);
        }else{
            //修改失败
            request.setAttribute("catalogMessage","修改失败!");
            //返回当前修改页面
            request.getRequestDispatcher(CATALOG_EDIT_PATH).forward(request,response);
        }
    }

    /**
     * 为修改页面传输数据
     * @param request
     * @param response
     */
    private void catalogEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取分类id
        int catalogId = Integer.parseInt(request.getParameter("id"));
        //获取对应分类数据
        Catalog catalog = catalogService.getCatalogByCatalogId(catalogId);
        //存入request域中
        request.setAttribute("catalog",catalog);
        //跳转到修改页面
        request.getRequestDispatcher(CATALOG_EDIT_PATH).forward(request,response);
    }

    /**
     * 添加分类
     * @param request
     * @param response
     */
    private void catalogAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取分类名称
        String catalogName = request.getParameter("catalogName");
        if (catalogService.addCatalog(catalogName)){
            //添加成功
            //存入消息
            request.setAttribute("catalogMessage","添加成功~");
            //跳转到分类列表页面
            catalogList(request,response);
        }else{
            //添加失败
            request.setAttribute("catalogMessage","添加失败!");
            //返回到当前添加页面
            request.getRequestDispatcher(CATALOG_ADD_PATH).forward(request,response);
        }
    }

    /**
     * 分类名称校验
     * @param request
     * @param response
     */
    private void catalogAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取输入的数据值
        String catalogName = request.getParameter("param");
        HashMap<String, Object> map = new HashMap<>();
        //根据输入的商品名称看输入的名称是否可用
        if (catalogService.findCatalogByName(catalogName)){
            //查到，用户名已存在
            map.put("info","用户名已存在!");
            map.put("status","n");
        }else{
            //没查到，用户名可用
            map.put("info","用户名可用~");
            map.put("status","y");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(json);
    }

    /**
     * 分类列表
     * @param request
     * @param response
     */
    private void catalogList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,catalogService.getCatalogCount());
        //存入信息
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("catalogList",catalogService.getCatalogsByPage(pageBean));
        //跳转到分类列表界面
        request.getRequestDispatcher(CATALOG_LIST_PATH).forward(request,response);
    }
}
