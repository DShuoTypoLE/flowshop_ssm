package com.tongda.project.service;

import com.tongda.project.bean.Admin;
import com.tongda.project.bean.PageBean;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 21:41
 */
public interface AdminService {
    /**
     * 判断管理员是否登录
     * @param admin
     * @return
     */
    Admin adminLogin(Admin admin);

    /**
     * 更新最后登陆时间
     * @param newTime
     * @param id
     * @return
     */
    boolean updateLastTime(Timestamp newTime, Integer id);

    /**
     * 管理员列表数量
     * @return
     */
    long allAdminCount();

    /**
     * 根据分页对象查找当前页面需要展示的数据
     * @param pageBean
     * @return
     */
    List<Admin> getAdminsByPage(PageBean pageBean);

    /**
     * 根据用户名查询名称是否可用
     * @param userName
     * @return
     */
    boolean findAdminByUserName(String userName);

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    boolean addAdmin(Admin admin);

    /**
     * 根据管理员id得到管理员信息
     * @param adminId
     * @return
     */
    Admin findAdminById(int adminId);

    /**
     * 更新管理员信息
     * @param admin
     * @return
     */
    boolean updateAdmin(Admin admin);

    /**
     * 删除单个管理员信息
     * @param adminId
     * @return
     */
    boolean delAdmin(int adminId);
}
