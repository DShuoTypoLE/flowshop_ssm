package com.tongda.project.service;

import com.tongda.project.bean.PageBean;
import com.tongda.project.bean.User;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 23:27
 */
public interface UserService {

    /**
     * 添加用户
     * @param user
     * @return
     */
    boolean userAdd(User user);

    /**
     * 根据用户名判断用户是否存在
     * @param username
     * @return
     */
    boolean findUserByUsername(String username);

    /**
     * 根据用户信息查询用户
     * @param user
     * @return
     */
    User findUserByUser(User user);

    /**
     * 获取用户数量
     * @return
     */
    long getUserCount();

    /**
     * 根据分页对象得到当前页要展示的数据
     * @param pageBean
     * @return
     */
    List<User> getUserByPage(PageBean pageBean);

    /**
     * 根据id得到对应用户信息
     * @param userId
     * @return
     */
    User findUserById(int userId);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 删除单个用户
     * @param userId
     * @return
     */
    boolean userDel(int userId);

    /**
     * 根据输入的用户名进行模糊查询得到数量
     * @param username
     * @return
     */
    long getUserCountByLike(String username);

    /**
     * 根据分页对象和模糊查询得到当前页面要展示的数据
     * @param pageBean
     * @param username
     * @return
     */
    List<User> getUserByLike(PageBean pageBean, String username);
}
