package com.tongda.project.dao;

import com.tongda.project.bean.Catalog;
import com.tongda.project.bean.PageBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-29 17:42
 */
@Repository
public interface CatalogDao {
    /**
     * 获取分类信息
     *
     * @return
     */
    @Select("select * from s_catalog")
    List<Catalog> getCatalogs();

    /**
     * 根据分类id得到对应数量
     *
     * @param catalogId
     * @return
     */
    @Select("select count(1) as count from s_flow where catalogId = #{catalogId}")
    long getCountByCatalogId(int catalogId);

    /**
     * 根据分类id得到分类对象信息
     *
     * @param catalogId
     * @return
     */
    @Select("select * from s_catalog where catalogId=#{catalogId}")
    Catalog getCatalogByCatalogId(int catalogId);

    /**
     * 获取分类数量
     *
     * @return
     */
    @Select("select count(1) as count from s_catalog")
    long getCatalogCount();

    /**
     * 根据分页得到分类信息集合
     *
     * @param pageBean
     * @return
     */
    @Select("select * from s_catalog limit ${(curPage-1)*maxSize},#{maxSize}")
    List<Catalog> getCatalogsByPage(PageBean pageBean);

    /**
     * 根据输入名称判断名称是否可用
     *
     * @param catalogName
     * @return
     */
    @Select("select * from s_catalog where catalogName=#{catalogName}")
    Catalog findCatalogByName(String catalogName);

    /**
     * 添加分类
     *
     * @param catalogName
     * @return
     */
    @Insert("insert into s_catalog values(null,#{catalogName})")
    boolean addCatalog(String catalogName);

    /**
     * 修改分类信息
     *
     * @param catalog
     * @return
     */
    @Update("update s_catalog set catalogName=#{catalogName} where catalogId=#{catalogId}")
    boolean updateCatalog(Catalog catalog);

    /**
     * 删除单个分类信息
     *
     * @param catalogId
     * @return
     */
    @Delete("delete from s_catalog where catalogId = #{catalogId}")
    boolean delCatalogById(int catalogId);
}
