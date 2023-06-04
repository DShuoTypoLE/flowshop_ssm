package com.tongda.project.service;

import com.tongda.project.bean.Flow;
import com.tongda.project.bean.PageBean;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 15:38
 */
public interface FlowService {

    /**
     * 得到推荐鲜花列表
     * @param recNum
     * @return
     */
    List<Flow> getRecFlows(int recNum);

    /**
     * 得到最新鲜花列表
     * @param newNum
     * @return
     */
    List<Flow> getNewFlows(int newNum);

    /**
     * 根据分类id得到对应数量
     * @param catalogId
     * @return
     */
    long getFlowCountByCatalogId(int catalogId);

    /**
     * 根据分页对象以及分类id得到对应商品集合
     * @param pageBean
     * @param catalogId
     * @return
     */
    List<Flow> getFlowsByPage(PageBean pageBean, int catalogId);

    /**
     * 查询所有商品列表数量
     * @return
     */
    long getFlowListCount();

    /**
     * 查询所有商品集合
     * @param pageBean
     * @return
     */
    List<Flow> flowList(PageBean pageBean);

    /**
     * 根据鲜花id查询对应商品信息
     * @param flowId
     * @return
     */
    Flow getFlowByFlowId(int flowId);

    /**
     * 根据输入名称模糊查询得到数量
     * @param seachname
     * @return
     */
    long getFlowCountByLike(String seachname);

    /**
     * 根据分页对象和商品名称模糊查询得到信息列表
     * @param pageBean
     * @param seachname
     * @return
     */
    List<Flow> getFlowsByLike(PageBean pageBean, String seachname);

    /**
     * 根据商品名称判断名称是否可用
     * @param flowName
     * @return
     */
    boolean findFlowByName(String flowName);

    /**
     * 添加商品
     * @param flow
     * @return
     */
    boolean addFlow(Flow flow);

    /**
     * 根据商品id将其中的图片id更换
     * @param imgId
     * @param flowId
     * @return
     */
    boolean updateFlowImgById(int imgId, int flowId);

    /**
     * 更新商品消息
     * @param flow
     * @return
     */
    boolean updateFlow(Flow flow);

    /**
     * 根据商品id得到对应图片id
     * @param flowId
     * @return
     */
    int getImgIdByFlowId(int flowId);

    /**
     * 根据商品id删除商品
     * @param flowId
     * @return
     */
    boolean delFlowById(int flowId);
}
