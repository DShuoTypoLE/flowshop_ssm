package com.tongda.project.service.impl;

import com.tongda.project.bean.Catalog;
import com.tongda.project.bean.PageBean;
import com.tongda.project.dao.CatalogDao;
import com.tongda.project.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-29 17:41
 */
@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogDao catalogDao;

    /**
     * 获取分类信息
     *
     * @return
     */
    @Override
    public List<Catalog> getCatalogs() {
        return catalogDao.getCatalogs();
    }

    /**
     * 根据分类id得到对应数量
     *
     * @param catalogId
     * @return
     */
    @Override
    public long getCountByCatalogId(int catalogId) {
        return catalogDao.getCountByCatalogId(catalogId);
    }

    /**
     * 根据分类id得到分类对象信息
     *
     * @param catalogId
     * @return
     */
    @Override
    public Catalog getCatalogByCatalogId(int catalogId) {
        return catalogDao.getCatalogByCatalogId(catalogId);
    }

    /**
     * 获取分类数量
     *
     * @return
     */
    @Override
    public long getCatalogCount() {
        return catalogDao.getCatalogCount();
    }

    /**
     * 根据分页得到分类信息集合
     *
     * @param pageBean
     * @return
     */
    @Override
    public List<Catalog> getCatalogsByPage(PageBean pageBean) {
        return catalogDao.getCatalogsByPage(pageBean);
    }

    /**
     * 根据输入名称判断名称是否可用
     *
     * @param catalogName
     * @return
     */
    @Override
    public boolean findCatalogByName(String catalogName) {
        Catalog catalog = catalogDao.findCatalogByName(catalogName);
        if (catalog != null) {
            //查到了，即用户名已存在
            return true;
        }
        return false;
    }

    /**
     * 添加分类
     * @param catalogName
     * @return
     */
    @Override
    public boolean addCatalog(String catalogName) {
        return catalogDao.addCatalog(catalogName);
    }

    /**
     * 修改分类信息
     * @param catalog
     * @return
     */
    @Override
    public boolean updateCatalog(Catalog catalog) {
        return catalogDao.updateCatalog(catalog);
    }

    /**
     * 删除单个分类信息
     * @param catalogId
     * @return
     */
    @Override
    public boolean delCatalogById(int catalogId) {
        return catalogDao.delCatalogById(catalogId);
    }
}
