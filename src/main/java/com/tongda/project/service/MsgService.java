package com.tongda.project.service;

import com.tongda.project.bean.Msg;
import com.tongda.project.bean.PageBean;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 21:12
 */
public interface MsgService {
    /**
     * 添加留言
     * @param msg1
     * @return
     */
    boolean addMsg(Msg msg1);

    /**
     * 得到留言数量
     * @return
     */
    long allCount();

    /**
     * 根据分页对象得到当前页面要展示的信息
     * @param pageBean
     * @return
     */
    List<Msg> getMsgsByPage(PageBean pageBean);

    /**
     * 删除留言
     * @param msgId
     * @return
     */
    boolean delMsg(int msgId);
}
