package com.tongda.project.dao;

import com.tongda.project.bean.Admin;
import com.tongda.project.bean.PageBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-31 21:42
 */
@Repository
public interface AdminDao {

    /**
     * 根据管理员信息查询管理员是否存在
     * @param admin
     * @return
     */
    @Select("select * from s_admin where userName=#{userName} " +
            "and passWord = #{passWord}")
    Admin adminLogin(Admin admin);

    /**
     * 更新登陆时间
     * @param newTime
     * @param id
     * @return
     */
    @Update("update s_admin set lastLoginTime=#{newTime}" +
            "where id = #{id}")
    boolean updateLastTime(@Param("newTime") Timestamp newTime, @Param("id") Integer id);

    /**
     * 管理员列表数量
     * @return
     */
    @Select("select count(1) as count from s_admin")
    long allAdminCount();

    /**
     * 根据分页对象查找当前页面需要展示的数据
     * @param pageBean
     * @return
     */
    @Select("select * from s_admin limit ${(curPage-1)*maxSize},#{maxSize}")
    List<Admin> getAdminsByPage(PageBean pageBean);

    /**
     * 根据用户名查询名称是否可用
     * @param userName
     * @return
     */
    @Select("select * from s_admin where userName = #{userName}")
    Admin findAdminByUserName(String userName);

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @Insert("insert into s_admin(userName,passWord,name) " +
            "values(#{userName},#{passWord},#{name})")
    boolean addAdmin(Admin admin);

    /**
     * 根据管理员id得到管理员信息
     * @param adminId
     * @return
     */
    @Select("select * from s_admin where id=#{adminId}")
    Admin findAdminById(int adminId);

    /**
     * 更新管理员信息
     * @param admin
     * @return
     */
    @Update("update s_admin set passWord=#{passWord},name=#{name} where id=#{id}")
    boolean updateAdmin(Admin admin);

    /**
     * 删除单个管理员信息
     * @param adminId
     * @return
     */
    @Delete("delete from s_admin where id=#{adminId}")
    boolean delAdmin(int adminId);
}
