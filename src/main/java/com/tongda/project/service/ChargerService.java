package com.tongda.project.service;

import com.tongda.project.bean.Charger;
import com.tongda.project.bean.PageBean;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-03 21:50
 */
public interface ChargerService {
    /**
     * 获取所有配送员的数量
     * @return
     */
    long getAllCount();

    /**
     * 根据分页对象得到当前页面要展示的数据
     * @param pageBean
     * @return
     */
    List<Charger> getChargersByPage(PageBean pageBean);

    /**
     * 添加配送员
     * @param charger
     * @return
     */
    boolean addCharger(Charger charger);

    /**
     * 根据id得到配送员信息
     * @param chargerId
     * @return
     */
    Charger getChargerById(int chargerId);

    /**
     * 修改配送员信息
     * @param charger
     * @return
     */
    boolean updateCharger(Charger charger);

    /**
     * 删除配送员信息
     * @param chargerId
     * @return
     */
    boolean delCharger(int chargerId);

    /**
     * 得到配送员列表
     * @return
     */
    List<Charger> getChargers();
}
