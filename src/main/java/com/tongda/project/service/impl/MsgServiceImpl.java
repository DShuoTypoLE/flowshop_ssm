package com.tongda.project.service.impl;

import com.tongda.project.bean.Msg;
import com.tongda.project.bean.PageBean;
import com.tongda.project.dao.MsgDao;
import com.tongda.project.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 21:12
 */
@Service
public class MsgServiceImpl implements MsgService {
    @Autowired
    private MsgDao msgDao;
    /**
     * 添加留言
     * @param msg1
     * @return
     */
    @Override
    public boolean addMsg(Msg msg1) {
        return msgDao.addMsg(msg1);
    }

    /**
     * 得到留言数量
     * @return
     */
    @Override
    public long allCount() {
        return msgDao.allCount();
    }

    /**
     * 根据分页对象得到当前页面要展示的信息
     * @param pageBean
     * @return
     */
    @Override
    public List<Msg> getMsgsByPage(PageBean pageBean) {
        return msgDao.getMsgsByPage(pageBean);
    }

    /**
     * 删除留言
     * @param msgId
     * @return
     */
    @Override
    public boolean delMsg(int msgId) {
        return msgDao.delMsg(msgId);
    }
}
