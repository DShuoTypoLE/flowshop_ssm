package com.tongda.project.service.impl;

import com.tongda.project.bean.Charger;
import com.tongda.project.bean.PageBean;
import com.tongda.project.dao.ChargerDao;
import com.tongda.project.service.ChargerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-03 21:50
 */
@Service
public class ChargerServiceImpl implements ChargerService {
    @Resource
    private ChargerDao chargerDao;

    /**
     * 获取所有配送员的数量
     * @return
     */
    @Override
    public long getAllCount() {
        return chargerDao.getAllCount();
    }

    /**
     * 根据分页对象得到当前页面要展示的数据
     * @param pageBean
     * @return
     */
    @Override
    public List<Charger> getChargersByPage(PageBean pageBean) {
        return chargerDao.getChargersByPage(pageBean);
    }

    /**
     * 添加配送员
     * @param charger
     * @return
     */
    @Override
    public boolean addCharger(Charger charger) {
        return chargerDao.addCharger(charger);
    }

    /**
     * 根据id得到配送员信息
     * @param chargerId
     * @return
     */
    @Override
    public Charger getChargerById(int chargerId) {
        return chargerDao.getChargerById(chargerId);
    }

    /**
     * 修改配送员信息
     * @param charger
     * @return
     */
    @Override
    public boolean updateCharger(Charger charger) {
        return chargerDao.updateCharger(charger);
    }

    /**
     * 删除配送员信息
     * @param chargerId
     * @return
     */
    @Override
    public boolean delCharger(int chargerId) {
        return chargerDao.delCharger(chargerId);
    }

    /**
     * 得到配送员列表
     * @return
     */
    @Override
    public List<Charger> getChargers() {
        return chargerDao.getChargers();
    }
}
