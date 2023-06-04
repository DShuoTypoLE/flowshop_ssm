package com.tongda.project.service.impl;

import com.tongda.project.bean.PageBean;
import com.tongda.project.bean.User;
import com.tongda.project.dao.UserDao;
import com.tongda.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 23:27
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public boolean userAdd(User user) {
        return userDao.userAdd(user);
    }

    /**
     * 根据用户名判断用户是否存在
     * @param username
     * @return
     */
    @Override
    public boolean findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        return user != null? true:false;
    }

    /**
     * 根据用户信息查询用户
     * @param user
     * @return
     */
    @Override
    public User findUserByUser(User user) {
        return userDao.findUserByUsername(user.getUserName());
    }

    /**
     * 获取用户数量
     * @return
     */
    @Override
    public long getUserCount() {
        return userDao.getUserCount();
    }

    /**
     * 根据分页对象得到当前页要展示的数据
     * @param pageBean
     * @return
     */
    @Override
    public List<User> getUserByPage(PageBean pageBean) {
        return userDao.getUserByPage(pageBean);
    }

    /**
     * 根据id得到对应用户信息
     * @param userId
     * @return
     */
    @Override
    public User findUserById(int userId) {
        return userDao.findUserById(userId);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    /**
     * 删除单个用户
     * @param userId
     * @return
     */
    @Override
    public boolean userDel(int userId) {
        return userDao.userDel(userId);
    }

    /**
     * 根据输入的用户名进行模糊查询得到数量
     * @param username
     * @return
     */
    @Override
    public long getUserCountByLike(String username) {
        return userDao.getUserCountByLike(username);
    }

    /**
     * 根据分页对象和模糊查询得到当前页面要展示的数据
     * @param pageBean
     * @param username
     * @return
     */
    @Override
    public List<User> getUserByLike(PageBean pageBean, String username) {
        return userDao.getUserByLike(pageBean,username);
    }
}
