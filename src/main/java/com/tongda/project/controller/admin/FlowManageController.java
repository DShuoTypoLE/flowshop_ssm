package com.tongda.project.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongda.project.bean.Catalog;
import com.tongda.project.bean.Flow;
import com.tongda.project.bean.PageBean;
import com.tongda.project.bean.UpLoadImg;
import com.tongda.project.service.CatalogService;
import com.tongda.project.service.FlowService;
import com.tongda.project.service.UpLoadImgService;
import com.tongda.project.util.DateUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-02 16:17
 */
@RestController
@RequestMapping("/jsp/admin")
public class FlowManageController {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String FLOW_LIST_PATH = "flowManage/flowList.jsp";
    private static final String FLOW_DETAIL_PATH = "flowManage/flowDetail.jsp";
    private static final String FLOW_ADD_PATH = "flowManage/flowAdd.jsp";
    private static final String FLOW_EDIT_PATH = "flowManage/flowEdit.jsp";
    @Autowired
    private FlowService flowService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private UpLoadImgService upLoadImgService;

    /**
     * 商品管理
     */
    @RequestMapping("/FlowManageServlet")
    public void flowManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action){
            case "list":
                flowList(request,response);
                break;
            case "detail":
                flowDetail(request,response);
                break;
            case "addReq":
                flowAddReq(request,response);
                break;
            case "find":
                flowAddAjaxFind(request,response);
                break;
            case "add":
                flowAdd(request,response);
                break;
            case "edit":
                flowEdit(request,response);
                break;
            case "updateImg":
                flowUpdateImg(request,response);
                break;
            case "update":
                flowUpdate(request,response);
                break;
            case "del":
                flowDelOne(request,response);
                break;
            case "batDel":
                flowBatDel(request,response);
                break;
            case "seach":
                flowSearchByLike(request,response);
                break;
        }
    }

    /**
     * 商品管理模糊查询
     * @param request
     * @param response
     */
    private void flowSearchByLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取输入的用户名
        String flowname = request.getParameter("flowname");
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,flowService.getFlowCountByLike(flowname));
        //根据分页对象得到当前页面要展示的数据
        List<Flow> flowList = flowService.getFlowsByLike(pageBean, flowname);
        flowList.forEach((flow)->{
            flow.setCatalog(catalogService.getCatalogByCatalogId(flow.getCatalogId()));
            flow.setUpLoadImg(upLoadImgService.getUpLoadImgById(flow.getImgId()));
        });
        //存入数据
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("flowList",flowList);
        //跳转到商品列表页面
        request.getRequestDispatcher(FLOW_LIST_PATH).forward(request,response);
    }

    /**
     * 批量删除
     * @param request
     * @param response
     */
    private void flowBatDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取选中商品id集合
        String ids = request.getParameter("ids");
        String[] idsArr = ids.split(",");
        int[] idsArrInt = new int[idsArr.length];
        for (int i = 0; i < idsArr.length; i++) {
            idsArrInt[i] = Integer.parseInt(idsArr[i]);
        }
        //调用单个删除函数去删除
        for (int id : idsArrInt) {
            //分三步,数据库中删除商品信息,删除图片信息
            int imgId = flowService.getImgIdByFlowId(id);//得到图片id
            flowService.delFlowById(id);//删除商品信息
            //得到图片名称
            String imgName = upLoadImgService.getImgNameById(imgId);
            upLoadImgService.delImgById(imgId);
            //本地图片也删掉
            File file = new File("D:\\airui\\studydocuments\\ssm_flowshop\\src\\main\\webapp\\images\\flow\\flowimg\\" + imgName);
            //判断文件存不存在
            if (!file.exists()){
                //不存在
                System.out.println("删除文件失败："+imgName+"不存在！");
            }else {
                //存在
                file.delete();
                System.out.println("文件删除成功~");
            }
        }
        //存入提示信息
        request.setAttribute("flowMessage","批量删除成功~");
        //跳转商品列表页面
        flowList(request,response);
    }

    /**
     * 删除单个
     * @param request
     * @param response
     */
    private void flowDelOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取选中商品id
        int flowId = Integer.parseInt(request.getParameter("id"));
        //分三步,数据库中删除商品信息,删除图片信息
        int imgId = flowService.getImgIdByFlowId(flowId);//得到图片id
        flowService.delFlowById(flowId);//删除商品信息
        //得到图片名称
        String imgName = upLoadImgService.getImgNameById(imgId);
        upLoadImgService.delImgById(imgId);
        //本地图片也删掉
        File file = new File("D:\\airui\\studydocuments\\ssm_flowshop\\src\\main\\webapp\\images\\flow\\flowimg\\" + imgName);
        //判断文件存不存在
        if (!file.exists()){
            //不存在
            System.out.println("删除文件失败："+imgName+"不存在！");
        }else{
            //存在
            file.delete();
            System.out.println("文件删除成功~");
            //存入提示信息
            request.setAttribute("flowMessage","删除成功~");
            //跳转商品列表
            flowList(request,response);
        }
    }

    /**
     * 更新商品信息
     * @param request
     * @param response
     */
    private void flowUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Flow flow = new Flow(
                Integer.parseInt(request.getParameter("flowId")),
                Integer.parseInt(request.getParameter("catalog")),
                Double.parseDouble(request.getParameter("price")),
                request.getParameter("keywords"),
                request.getParameter("desc")
        );
        //修改
        if (flowService.updateFlow(flow)){
            //修改成功
            //存入信息
            request.setAttribute("flowMessage","修改成功~");
            //跳转到商品列表
            flowList(request,response);
        }else{
            //修改失败
            request.setAttribute("flowMessage","修改失败!");
            //返回到修改页面
            request.getRequestDispatcher(FLOW_EDIT_PATH).forward(request,response);
        }
    }

    /**
     * 修改图片
     * @param request
     * @param response
     */
    private void flowUpdateImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取商品id
        int flowId = Integer.parseInt(request.getParameter("id"));
        //首先得到图片对象，添加进入数据库，修改商品的图片id
        getUploadData(request,response);
        UpLoadImg upLoadImg = (UpLoadImg) request.getAttribute("upLoadImg");
        upLoadImgService.addUpLoadImg(upLoadImg);
        if (flowService.updateFlowImgById(upLoadImg.getImgId(),flowId)){
            //修改成功
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品图片修改成功~");
            //转发到列表页面，因为图片要刷新
            flowList(request, response);
        }else {
            //修改失败
            //将提示信息存入request域中
            request.setAttribute("flowMessage","商品图片修改失败！");
            //转发到修改页面
            flowList(request, response);
        }
    }

    /**
     * 传递数据到修改页面
     * @param request
     * @param response
     */
    private void flowEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当前选中商品id
        int flowId = Integer.parseInt(request.getParameter("id"));
        //获取商品信息
        Flow flow = flowService.getFlowByFlowId(flowId);
        flow.setCatalog(catalogService.getCatalogByCatalogId(flow.getCatalogId()));
        flow.setUpLoadImg(upLoadImgService.getUpLoadImgById(flow.getImgId()));

        //获取分类列表信息
        List<Catalog> catalogs = catalogService.getCatalogs();
        //存入数据
        request.setAttribute("flowInfo",flow);
        request.setAttribute("catalog",catalogs);
        //跳转到修改页面
        request.getRequestDispatcher(FLOW_EDIT_PATH).forward(request,response);
    }

    /**
     * 添加商品
     * @param request
     * @param response
     */
    private void flowAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台表单提交的数据
        //处理上传的图片和参数
        getUploadData(request, response);
        //从域中取出uploadImg
        UpLoadImg upLoadImg = (UpLoadImg) request.getAttribute("upLoadImg");
        //从域中取出鲜花参数集合
        List<String> flowParams = (List<String>) request.getAttribute("flowParams");
        //从集合中拿到鲜花的值并装进Flow对象里
        String flowName = flowParams.get(0);
        int catalog = Integer.parseInt(flowParams.get(1));
        double price = Double.parseDouble(flowParams.get(2));
        String keywords = flowParams.get(3);
        String desc = flowParams.get(4);

        //将图片对象添加到数据库并生成id得到//获取图片id
        int status = upLoadImgService.addUpLoadImg(upLoadImg);
        System.out.println("status====="+status);//status=====1
        int imgId = upLoadImg.getImgId();
        System.out.println("imgId======="+ imgId);//imgId=======47
        Flow flow = new Flow(
                catalog,
                flowName,
                price,
                desc,
                imgId,
                DateUtil.getTimestamp(),
                keywords
        );
        //添加商品
        if (flowService.addFlow(flow)){
            //添加成功
            //存消息
            request.setAttribute("flowMessage","添加成功~");
            //跳转商品列表
            flowList(request,response);
        }else{
            //添加失败
            request.setAttribute("flowMessage","添加失败!");
            //返回添加页面
            request.getRequestDispatcher(FLOW_ADD_PATH).forward(request,response);
        }
    }
    /**
     * 获取商品添加时的数据
     * @param request
     * @param response
     */
    private void getUploadData(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 先获取到要上传的文件目录
            String path = request.getSession().getServletContext().getRealPath("/images/flow/flowimg");
            System.out.println(path);
            // 创建File对象，一会向该路径下上传文件
            File file = new File(path);
            // 判断路径是否存在，如果不存在，创建该路径
            if(!file.exists()) {
                file.mkdirs();
            }
            // 创建磁盘文件项工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //创建UpLoadImg对象
            UpLoadImg upLoadImg = new UpLoadImg();
            List<String> flowParams = new ArrayList<>();
            List<FileItem> list = null;

            // 解析request对象
            list = fileUpload.parseRequest(request);
            // 遍历
            for (FileItem fileItem : list) {
                // 判断文件项是普通字段，还是上传的文件
                if(fileItem.isFormField()) {
                    // 普通表单项, 当 enctype="multipart/form-data"时, request的getParameter()方法 无法获取参数
                    String fieldName = fileItem.getFieldName(); // 获取表单文本框中name的属性值
                    String value = fileItem.getString("utf-8"); // 获取utf-8编码之后表单文本框中的内容
                    System.out.println(fieldName + " = " + value);
                    flowParams.add(value);
                }else {
                    // 上传文件项
                    // 获取到上传文件的名称
                    String filename = fileItem.getName();
                    System.out.println(filename);
                    // 上传文件
                    fileItem.write(new File(file, filename));
                    // 删除临时文件
                    fileItem.delete();
                    //传到webapp下
                    FileInputStream fileInputStream = new FileInputStream(path+"/" +filename);
                    String absolutePath = "D:\\airui\\studydocuments\\ssm_flowshop\\src\\main\\webapp\\images\\flow\\flowimg";
                    FileOutputStream fileOutputStream = new FileOutputStream(absolutePath+"/" + filename);
                    byte[] bytes = new byte[1024];
                    int data;
                    while ((data = fileInputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes,0,data);
                        System.out.println("上传完毕,已经到webapp下了~");
                    }
                    //关闭流,否则后面删不掉
                    fileOutputStream.close();
                    fileInputStream.close();
                    //为uploadImg赋值
                    upLoadImg.setImgName(filename);
                    upLoadImg.setImgSrc("images/flow/flowimg/"+filename);
                    upLoadImg.setImgType(request.getServletContext().getMimeType(filename));
                }
                //将upLoadImg存到request域中并转发到添加servlet
                request.setAttribute("upLoadImg",upLoadImg);
                //将flowParams存入request域中
                request.setAttribute("flowParams",flowParams);
            }
        } catch (Exception e) {
            throw new RuntimeException("上传SSM这边有问题!!");
        }
    }

    /**
     * 商品添加时名称校验
     * @param request
     * @param response
     */
    private void flowAddAjaxFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取商品名称
        String flowName = request.getParameter("param");
        HashMap<String, Object> map = new HashMap<>();
        //通过鲜花名称查找用户名是否被使用
        if (flowService.findFlowByName(flowName)){
            //存在，即已经被使用
            map.put("info","用户名已存在!");
            map.put("status","n");
        }else{
            //商品名可用
            map.put("info","用户名可用~");
            map.put("status","y");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(json);
    }

    /**
     * 给添加商品页面传输数据
     * @param request
     * @param response
     */
    private void flowAddReq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //给商品添加页面放入分类信息
        List<Catalog> catalogs = catalogService.getCatalogs();
        //存入数据
        request.setAttribute("catalog",catalogs);
        //跳转到商品添加页面
        request.getRequestDispatcher(FLOW_ADD_PATH).forward(request,response);
    }

    /**
     * 商品详情
     * @param request
     * @param response
     */
    private void flowDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取选中鲜花id
        int flowId = Integer.parseInt(request.getParameter("id"));
        //得到鲜花信息
        Flow flow = flowService.getFlowByFlowId(flowId);
        flow.setCatalog(catalogService.getCatalogByCatalogId(flow.getCatalogId()));
        flow.setUpLoadImg(upLoadImgService.getUpLoadImgById(flow.getImgId()));
        //存入消息
        request.setAttribute("flowInfo",flow);
        //跳转商品详情页面
        request.getRequestDispatcher(FLOW_DETAIL_PATH).forward(request,response);
    }

    /**
     * 商品列表展示
     * @param request
     * @param response
     */
    private void flowList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,flowService.getFlowListCount());
        //获取商品信息
        List<Flow> flowList = flowService.flowList(pageBean);
        //设置图片信息和分类信息
        flowList.forEach((flow) -> {
            flow.setCatalog(catalogService.getCatalogByCatalogId(flow.getCatalogId()));
            flow.setUpLoadImg(upLoadImgService.getUpLoadImgById(flow.getImgId()));
        });
        //存入消息
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("flowList",flowList);
        //转发到商品列表页面
        request.getRequestDispatcher(FLOW_LIST_PATH).forward(request,response);
    }
}
