package com.tongda.project.dao;

import com.tongda.project.bean.PageBean;
import com.tongda.project.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 23:28
 */
@Repository
public interface UserDao {
    /**
     * 添加用户
     * @param user
     * @return
     */
    @Insert("insert into s_user values(null," +
            "#{userName},#{userPassWord},#{name}," +
            "#{sex},#{age},#{tell},#{address},#{enabled})")
    boolean userAdd(User user);

    /**
     * 根据用户名判断用户是否存在
     * @param username
     * @return
     */
    @Select("select * from s_user where userName = #{username}")
    User findUserByUsername(String username);

    /**
     * 获取用户数量
     * @return
     */
    @Select("select count(1) as count from s_user")
    long getUserCount();

    /**
     * 根据分页对象得到当前页要展示的数据
     * @param pageBean
     * @return
     */
    @Select("select * from s_user limit ${(curPage-1)*maxSize},#{maxSize}")
    List<User> getUserByPage(PageBean pageBean);

    /**
     * 根据id得到对应用户信息
     * @param userId
     * @return
     */
    @Select("select * from s_user where userId = #{userId}")
    User findUserById(int userId);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Update("update s_user set "+
            "userPassWord=#{userPassWord}," +
            "name=#{name}," +
            "sex=#{sex}," +
            "age=#{age}," +
            "tell=#{tell}," +
            "address=#{address}," +
            "enabled=#{enabled}" +
            " where userId=#{userId}")
    boolean updateUser(User user);

    /**
     * 删除单个用户
     * @param userId
     * @return
     */
    @Delete("delete from s_user where userId = #{userId}")
    boolean userDel(int userId);

    /**
     * 根据输入的用户名进行模糊查询得到数量
     * @param username
     * @return
     */
    @Select("select count(1) as count from s_user " +
            "where userName like concat('%',#{username},'%')")
    long getUserCountByLike(String username);

    /**
     * 根据分页对象和模糊查询得到当前页面要展示的数据
     * @param pageBean
     * @param username
     * @return
     */
    @Select("select * from s_user " +
            "where userName like concat('%',#{username},'%') " +
            "limit ${(pageBean.curPage-1)*pageBean.maxSize},#{pageBean.maxSize}")
    List<User> getUserByLike(@Param("pageBean") PageBean pageBean, @Param("username") String username);
}
