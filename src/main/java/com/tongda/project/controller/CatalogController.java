package com.tongda.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongda.project.bean.Catalog;
import com.tongda.project.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-29 17:30
 */
@RestController
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    /**
     * 获取分类信息
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/GetCatalog")
    public String getCatalog() throws JsonProcessingException {
        //获取分类列表信息
        List<Catalog> catalogs = catalogService.getCatalogs();
        //根据分类id得到对应商品数量
        for (Catalog catalog : catalogs) {
            long count =  catalogService.getCountByCatalogId(catalog.getCatalogId());
            catalog.setCatalogSize(count);
        }
        //填入json中返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("catalog",catalogs);
        return new ObjectMapper().writeValueAsString(map);
    }
}
