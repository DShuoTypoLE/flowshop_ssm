package com.tongda.project.dao;

import com.tongda.project.bean.UpLoadImg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 17:35
 */
@Repository
public interface UpLoadImgDao {

    /**
     * 根据图片id得到图片信息
     * @param imgId
     * @return
     */
    @Select("select * from s_uploadimg where imgId = #{imgId}")
    UpLoadImg getUpLoadImgById(int imgId);

    /**
     * 添加图片信息
     * @param upLoadImg
     * @return
     */
    @SelectKey(keyProperty = "imgId",keyColumn = "imgId",
            before = false,resultType = Integer.class,
            statement = {"select last_insert_id()"})
    @Insert("insert into s_uploadimg values(null,#{imgName},#{imgSrc},#{imgType})")
    int addUpLoadImg(UpLoadImg upLoadImg);

    /**
     * 根据图片id得到图片名称
     * @param imgId
     * @return
     */
    @Select("select imgName from s_uploadimg where imgId = #{imgId}")
    String getImgNameById(int imgId);

    /**
     * 根据图片id删掉对应图片信息
     * @param imgId
     * @return
     */
    @Delete("delete from s_uploadimg where imgId=#{imgId}")
    boolean delImgById(int imgId);
}
