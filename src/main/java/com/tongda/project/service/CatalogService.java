package com.tongda.project.service;

import com.tongda.project.bean.Catalog;
import com.tongda.project.bean.PageBean;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-29 17:40
 */
public interface CatalogService {
    /**
     * 获取分类信息
     * @return
     */
    List<Catalog> getCatalogs();

    /**
     * 根据分类id得到对应数量
     * @return
     * @param catalogId
     */
    long getCountByCatalogId(int catalogId);

    /**
     * 根据分类id得到分类对象信息
     * @param catalogId
     * @return
     */
    Catalog getCatalogByCatalogId(int catalogId);

    /**
     * 获取分类数量
     * @return
     */
    long getCatalogCount();

    /**
     * 根据分页得到分类信息集合
     * @param pageBean
     * @return
     */
    List<Catalog> getCatalogsByPage(PageBean pageBean);

    /**
     * 根据输入名称判断名称是否可用
     * @param catalogName
     * @return
     */
    boolean findCatalogByName(String catalogName);

    /**
     * 添加分类
     * @param catalogName
     * @return
     */
    boolean addCatalog(String catalogName);

    /**
     * 修改分类信息
     * @param catalog
     * @return
     */
    boolean updateCatalog(Catalog catalog);

    /**
     * 删除单个分类信息
     * @param catalogId
     * @return
     */
    boolean delCatalogById(int catalogId);
}
