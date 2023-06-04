package com.tongda.project.dao;

import com.tongda.project.bean.Charger;
import com.tongda.project.bean.Order;
import com.tongda.project.bean.PageBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-06-03 21:51
 */
@Repository
public interface ChargerDao {

    /**
     * 获取所有配送员的数量
     * @return
     */
    @Select("select count(1) as count from s_charger")
    long getAllCount();

    /**
     * 根据分页对象得到当前页面要展示的数据
     * @param pageBean
     * @return
     */
    @Select("select * from s_charger limit ${(curPage-1)*maxSize},#{maxSize}")
    List<Charger> getChargersByPage(PageBean pageBean);

    /**
     * 添加配送员
     * @param charger
     * @return
     */
    @Insert("insert into s_charger values(null,#{name},#{phone},#{no})")
    boolean addCharger(Charger charger);

    /**
     * 根据id得到配送员信息
     * @param chargerId
     * @return
     */
    @Select("select * from s_charger where id=#{chargerId}")
    Charger getChargerById(int chargerId);

    /**
     * 修改配送员信息
     * @param charger
     * @return
     */
    @Update("update s_charger set name=#{name},phone=#{phone} where id=#{id}")
    boolean updateCharger(Charger charger);

    /**
     * 删除配送员信息
     * @param chargerId
     * @return
     */
    @Delete("delete from s_charger where id=#{chargerId}")
    boolean delCharger(int chargerId);

    /**
     * 得到配送员列表
     * @return
     */
    @Select("select * from s_charger")
    List<Charger> getChargers();


}
