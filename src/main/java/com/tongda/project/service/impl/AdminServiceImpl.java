package com.tongda.project.service.impl;

import com.tongda.project.bean.Admin;
import com.tongda.project.bean.PageBean;
import com.tongda.project.dao.AdminDao;
import com.tongda.project.service.AdminService;
import com.tongda.project.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 21:42
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    /**
     * 判断管理员是否登录
     * @param admin
     * @return
     */
    @Override
    public Admin adminLogin(Admin admin) {
        return adminDao.adminLogin(admin);
    }

    /**
     * 更新最后登陆时间
     * @param newTime
     * @param id
     * @return
     */
    @Override
    public boolean updateLastTime(Timestamp newTime, Integer id) {
        return adminDao.updateLastTime(newTime,id);
    }

    /**
     * 管理员列表数量
     * @return
     */
    @Override
    public long allAdminCount() {
        return adminDao.allAdminCount();
    }

    /**
     * 根据分页对象查找当前页面需要展示的数据
     * @param pageBean
     * @return
     */
    @Override
    public List<Admin> getAdminsByPage(PageBean pageBean) {
        return adminDao.getAdminsByPage(pageBean);
    }

    /**
     * 根据用户名查询名称是否可用
     * @param userName
     * @return
     */
    @Override
    public boolean findAdminByUserName(String userName) {
        Admin admin = adminDao.findAdminByUserName(userName);
        return admin != null ? true:false;
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @Override
    public boolean addAdmin(Admin admin) {
        return adminDao.addAdmin(admin);
    }

    /**
     * 根据管理员id得到管理员信息
     * @param adminId
     * @return
     */
    @Override
    public Admin findAdminById(int adminId) {
        return adminDao.findAdminById(adminId);
    }

    /**
     * 更新管理员信息
     * @param admin
     * @return
     */
    @Override
    public boolean updateAdmin(Admin admin) {
        return adminDao.updateAdmin(admin);
    }

    /**
     * 删除单个管理员信息
     * @param adminId
     * @return
     */
    @Override
    public boolean delAdmin(int adminId) {
        return adminDao.delAdmin(adminId);
    }
}
