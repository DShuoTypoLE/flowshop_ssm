package com.tongda.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongda.project.util.ImageCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 验证码
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 14:39
 */
@RestController
public class CodeController {

    @RequestMapping("/CodeServlet")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断前台传递过来的action参数，点击登录页面的时候
        //页面携带的是 src="CodeServlet?action=code" 执行获取验证码方法
        //或者点击切换验证码按钮："reCode()"，会重新获取验证码
        String action=request.getParameter("action");
        if("code".equals(action)) {
            getCode(request,response);
        }
        //ckCode是验证验证码是否正确，如果是输入验证码之后，就走这个方法，判断验证码对不对
        //请求路径为：ajaxurl:"CodeServlet?action=ckCode",
        if("ckCode".equals(action)) {
            ckCode(request,response);
        }
    }

    /**
     * 验证验证码
     * @param request
     * @param response
     */
    private void ckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code=request.getParameter("param");
        System.out.println(code);
        String ck_code=(String) request.getSession().getAttribute("checkCode");
        HashMap<String, Object> map = new HashMap<>();
        //比较验证码并返回信息
        if (ck_code.equals(code)) {
            map.put("info", "验证码正确");
            map.put("status", "y");
        } else {
            map.put("info", "验证码输入不正确");
            map.put("status", "n");
        }
        String json = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(json);
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     */
    private void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置浏览器不要缓存此图片
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ServletOutputStream outputStream = response.getOutputStream();
        String rands= ImageCode.getImageCode(70, 30, outputStream);
        //将生成的随机四个字符保存在session范围checkCode属性
        request.getSession().setAttribute("checkCode", rands);

        outputStream.close();
    }
}
