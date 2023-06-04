package com.tongda.project.dao;

import com.tongda.project.bean.Flow;
import com.tongda.project.bean.PageBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 15:40
 */
@Repository
public interface FlowDao {

    /**
     * 得到推荐鲜花列表
     * @param recNum
     * @return
     */
    @Select("select * from view_flow order by rand() limit #{recNum}")
    List<Flow> getRecFlows(int recNum);

    /**
     * 得到最新鲜花列表
     * @param newNum
     * @return
     */
    @Select("select * from view_flow order by addTime desc limit #{newNum}")
    List<Flow> getNewFlows(int newNum);

    /**
     * 根据分类id得到对应数量
     * @param catalogId
     * @return
     */
    @Select("select count(1) as count from view_flow where catalogId = #{catalogId}")
    long getFlowCountByCatalogId(int catalogId);

    /**
     * 根据分页对象以及分类id得到对应商品集合
     * @param pageBean
     * @param catalogId
     * @return
     */
    @Select("select * from view_flow where catalogId = #{catalogId} " +
            "limit ${(pageBean.curPage-1)*pageBean.maxSize},#{pageBean.maxSize}")
    List<Flow> getFlowsByPage(@Param("pageBean") PageBean pageBean, @Param("catalogId") int catalogId);

    /**
     * 查询所有商品列表数量
     * @return
     */
    @Select("select count(1) as count from s_flow")
    long getFlowListCount();

    /**
     * 查询所有商品集合
     * @param pageBean
     * @return
     */
    @Select("select * from view_flow limit ${(curPage-1)*maxSize},#{maxSize}")
    List<Flow> flowList(PageBean pageBean);

    /**
     * 根据鲜花id查询对应商品信息
     * @param flowId
     * @return
     */
    @Select("select * from view_flow where flowId = #{flowId}")
    Flow getFlowByFlowId(int flowId);

    /**
     * 根据输入名称模糊查询得到数量
     * @param seachname
     * @return
     */
    @Select("select count(1) as count from view_flow where flowName like concat('%',#{seachname},'%')")
    long getFlowCountByLike(String seachname);

    /**
     * 根据分页对象和商品名称模糊查询得到信息列表
     * @param pageBean
     * @param seachname
     * @return
     */
    @Select("select * from view_flow where flowName like concat('%',#{seachname},'%') " +
            "limit ${(pageBean.curPage-1)*pageBean.maxSize},#{pageBean.maxSize}")
    List<Flow> getFlowsByLike(@Param("pageBean") PageBean pageBean, @Param("seachname") String seachname);

    /**
     * 根据商品名称判断名称是否可用
     * @param flowName
     * @return
     */
    @Select("select * from s_flow where flowName = #{flowName}")
    Flow findFlowByName(String flowName);

    /**
     * 添加商品
     * @param flow
     * @return
     */
    @Insert("insert into s_flow values(null," +
            "#{catalogId},#{flowName},#{price},#{description}," +
            "#{imgId},#{addTime},#{keywords})")
    boolean addFlow(Flow flow);

    /**
     * 根据商品id将其中的图片id更换
     * @param imgId
     * @param flowId
     * @return
     */
    @Update("update s_flow set imgId = #{imgId} where flowID = #{flowId}")
    boolean updateFlowImgById(@Param("imgId") int imgId, @Param("flowId") int flowId);

    /**
     * 更新商品消息
     * @param flow
     * @return
     */
    @Update("update s_flow set catalogId = #{catalogId}," +
            "price=#{price}," +
            "keywords=#{keywords}," +
            "description=#{description} " +
            "where flowID = #{flowId}")
    boolean updateFlow(Flow flow);

    /**
     * 根据商品id得到对应图片id
     * @param flowId
     * @return
     */
    @Select("select imgId from s_flow where flowID = #{flowId}")
    int getImgIdByFlowId(int flowId);

    /**
     * 根据商品id删除商品
     * @param flowId
     * @return
     */
    @Delete("delete from s_flow where flowID=#{flowId}")
    boolean delFlowById(int flowId);

}
