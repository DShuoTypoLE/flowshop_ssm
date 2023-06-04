package com.tongda.project.dao;

import com.tongda.project.bean.Msg;
import com.tongda.project.bean.PageBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 21:12
 */
@Repository
public interface MsgDao {
    /**
     * 添加留言
     * @param msg1
     * @return
     */
    @Insert("insert into s_msg values(null,#{contain},#{inputtime},#{inputperson})")
    boolean addMsg(Msg msg1);

    /**
     * 得到留言数量
     * @return
     */
    @Select("select count(1) as count from s_msg")
    long allCount();

    /**
     * 根据分页对象得到当前页面要展示的信息
     * @param pageBean
     * @return
     */
    @Select("select * from s_msg limit ${(curPage-1)*maxSize},#{maxSize}")
    List<Msg> getMsgsByPage(PageBean pageBean);

    /**
     * 删除留言
     * @param msgId
     * @return
     */
    @Delete("delete from s_msg where id=#{msgId}")
    boolean delMsg(int msgId);
}
