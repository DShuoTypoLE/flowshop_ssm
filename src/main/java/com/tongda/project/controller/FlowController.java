package com.tongda.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongda.project.bean.Address;
import com.tongda.project.bean.Flow;
import com.tongda.project.bean.PageBean;
import com.tongda.project.service.AddressService;
import com.tongda.project.service.CatalogService;
import com.tongda.project.service.FlowService;
import com.tongda.project.service.UpLoadImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 15:29
 */
@Controller
public class FlowController {
    //分页显示最大8条
    private static final int MAX_PAGE_SIZE = 8;
    @Autowired
    private FlowService flowService;
    @Autowired
    private UpLoadImgService upLoadImgService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private AddressService addressService;

    /**
     * 获取首页展示信息
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/ShopIndex")
    @ResponseBody
    public String shopIndex(HttpServletRequest request) throws JsonProcessingException {
        //显示首部地址信息
        Address address = addressService.getAddress();
        request.getSession().setAttribute("address",address);
        /*===========================================================*/
        //获取推荐鲜花列表
        List<Flow> recFlows = flowService.getRecFlows(4);
        //获取新品鲜花列表
        List<Flow> newFlows = flowService.getNewFlows(4);

        //将图片信息插入其中
        for (Flow recFlow : recFlows) {
            recFlow.setUpLoadImg(upLoadImgService.getUpLoadImgById(recFlow.getImgId()));
        }
        //将图片信息插入其中
        for (Flow newFlow : newFlows) {
            //得到每个鲜花对应的图片信息并加入相应的鲜花商品中
            newFlow.setUpLoadImg(upLoadImgService.getUpLoadImgById(newFlow.getImgId()));
        }


        HashMap<String, Object> map = new HashMap<>();
        map.put("recFlows",recFlows);
        map.put("newFlows",newFlows);

        return new ObjectMapper().writeValueAsString(map);
    }

    /**
     * 点击分类得到对应商品列表
     * @param request
     * @return
     */
    @RequestMapping("/FlowList")
    public String flowList(HttpServletRequest request){
        //设置当前页为第一页
        int curPage = 1;
        //获取页码
        String page = request.getParameter("page");
        //如果页码不为空就跳转相应页码
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //获取携带的分类id
        String catalogIdStr = request.getParameter("catalogId");
        //创建PageBean对象
        PageBean pageBean = null;
        List<Flow> flowList = new ArrayList<>();
        if (catalogIdStr != null) {
            //如果有分类id传过来
            int catalogId = Integer.parseInt(catalogIdStr);
            pageBean = new PageBean(curPage,MAX_PAGE_SIZE,flowService.getFlowCountByCatalogId(catalogId));
            //根据分页对象以及分类id得到对应商品集合
            flowList = flowService.getFlowsByPage(pageBean,catalogId);

            for (Flow flow : flowList) {
                //将Catalog信息加入对应商品中
                flow.setCatalog(catalogService.getCatalogByCatalogId(flow.getCatalogId()));
                //将UpLoadImg信息加入对应商品中
                flow.setUpLoadImg(upLoadImgService.getUpLoadImgById(flow.getImgId()));
            }
            //将对应的分类标题存入request域中
            if(flowList.size() > 0){
                request.setAttribute("title",flowList.get(0).getCatalog().getCatalogName());
            }
        }else {
            //没有传过来分类id就查询所有
            pageBean = new PageBean(curPage,MAX_PAGE_SIZE,flowService.getFlowListCount());
            //根据分页对象查询商品数据
            flowList = flowService.flowList(pageBean);
            for (Flow flow : flowList) {
                //将Catalog信息加入对应商品中
                flow.setCatalog(catalogService.getCatalogByCatalogId(flow.getCatalogId()));
                //将UpLoadImg信息加入对应商品中
                flow.setUpLoadImg(upLoadImgService.getUpLoadImgById(flow.getImgId()));
            }
            request.setAttribute("title","所有鲜花");
        }
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("flowList",flowList);
        return "flow/flowlist";
    }

    /**
     * 查看鲜花详情
     * @param request
     * @return
     */
    @RequestMapping("/flowdetail")
    public String flowdetail(HttpServletRequest request){
        //获取参数flowId
        int flowId = Integer.parseInt(request.getParameter("flowId"));
        //根据鲜花id查询对应商品信息
        Flow flowInfo = flowService.getFlowByFlowId(flowId);
        //将图片信息和分类信息传进去
        flowInfo.setUpLoadImg(upLoadImgService.getUpLoadImgById(flowInfo.getImgId()));
        flowInfo.setCatalog(catalogService.getCatalogByCatalogId(flowInfo.getCatalogId()));
        //将信息存入request域中
        request.setAttribute("flowInfo",flowInfo);
        return "flow/flowdetails";
    }

    /**
     * 搜索鲜花
     * @return
     */
    @RequestMapping("/FlowList2")
    public String searchFlow(HttpServletRequest request){
        //获取输入内容
        String seachname = request.getParameter("seachname");
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,flowService.getFlowCountByLike(seachname));
        //根据分页对象查询
        List<Flow> flowList = flowService.getFlowsByLike(pageBean,seachname);
        //将图片信息放在商品里
        for (Flow flow : flowList) {
            flow.setUpLoadImg(upLoadImgService.getUpLoadImgById(flow.getImgId()));
        }
        //将信息存入request域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("flowList",flowList);
        request.setAttribute("title","搜索鲜花");
        return "flow/flowlist";
    }
}
