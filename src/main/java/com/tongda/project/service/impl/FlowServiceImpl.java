package com.tongda.project.service.impl;

import com.tongda.project.bean.Flow;
import com.tongda.project.bean.PageBean;
import com.tongda.project.dao.FlowDao;
import com.tongda.project.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 15:40
 */
@Service
public class FlowServiceImpl implements FlowService {
    @Autowired
    private FlowDao flowDao;

    /**
     * 得到推荐鲜花列表
     * @param recNum
     * @return
     */
    @Override
    public List<Flow> getRecFlows(int recNum) {
        return flowDao.getRecFlows(recNum);
    }

    /**
     * 得到最新鲜花列表
     * @param newNum
     * @return
     */
    @Override
    public List<Flow> getNewFlows(int newNum) {
        return flowDao.getNewFlows(newNum);
    }

    /**
     * 根据分类id得到对应数量
     * @param catalogId
     * @return
     */
    @Override
    public long getFlowCountByCatalogId(int catalogId) {
        return flowDao.getFlowCountByCatalogId(catalogId);
    }

    /**
     * 根据分页对象以及分类id得到对应商品集合
     * @param pageBean
     * @param catalogId
     * @return
     */
    @Override
    public List<Flow> getFlowsByPage(PageBean pageBean, int catalogId) {
        return flowDao.getFlowsByPage(pageBean,catalogId);
    }

    /**
     * 查询所有商品列表数量
     * @return
     */
    @Override
    public long getFlowListCount() {
        return flowDao.getFlowListCount();
    }

    /**
     * 查询所有商品集合
     * @param pageBean
     * @return
     */
    @Override
    public List<Flow> flowList(PageBean pageBean) {
        return flowDao.flowList(pageBean);
    }

    /**
     * 根据鲜花id查询对应商品信息
     * @param flowId
     * @return
     */
    @Override
    public Flow getFlowByFlowId(int flowId) {
        return flowDao.getFlowByFlowId(flowId);
    }

    /**
     * 根据输入名称模糊查询得到数量
     * @param seachname
     * @return
     */
    @Override
    public long getFlowCountByLike(String seachname) {
        return flowDao.getFlowCountByLike(seachname);
    }

    /**
     * 根据分页对象和商品名称模糊查询得到信息列表
     * @param pageBean
     * @param seachname
     * @return
     */
    @Override
    public List<Flow> getFlowsByLike(PageBean pageBean, String seachname) {
        return flowDao.getFlowsByLike(pageBean,seachname);
    }

    /**
     * 根据商品名称判断名称是否可用
     * @param flowName
     * @return
     */
    @Override
    public boolean findFlowByName(String flowName) {
        Flow flow = flowDao.findFlowByName(flowName);
        return flow != null ? true : false;
    }

    /**
     * 添加商品
     * @param flow
     * @return
     */
    @Override
    public boolean addFlow(Flow flow) {
        return flowDao.addFlow(flow);
    }

    /**
     * 根据商品id将其中的图片id更换
     * @param imgId
     * @param flowId
     * @return
     */
    @Override
    public boolean updateFlowImgById(int imgId, int flowId) {
        return flowDao.updateFlowImgById(imgId,flowId);
    }

    /*&*
    更新商品消息
     */
    @Override
    public boolean updateFlow(Flow flow) {
        return flowDao.updateFlow(flow);
    }

    /**
     * 根据商品id得到对应图片id
     * @param flowId
     * @return
     */
    @Override
    public int getImgIdByFlowId(int flowId) {
        return flowDao.getImgIdByFlowId(flowId);
    }

    /**
     * 根据商品id删除商品
     * @param flowId
     * @return
     */
    @Override
    public boolean delFlowById(int flowId) {
        return flowDao.delFlowById(flowId);
    }
}
