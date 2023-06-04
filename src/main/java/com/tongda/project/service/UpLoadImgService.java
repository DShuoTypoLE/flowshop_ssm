package com.tongda.project.service;

import com.tongda.project.bean.UpLoadImg;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 17:32
 */
public interface UpLoadImgService {

    /**
     * 根据图片id得到图片信息
     * @param imgId
     * @return
     */
    UpLoadImg getUpLoadImgById(int imgId);

    /**
     * 添加图片信息,这里将返回图片id
     * @param upLoadImg
     * @return
     */
    int addUpLoadImg(UpLoadImg upLoadImg);

    /**
     * 根据图片id得到图片名称
     * @param imgId
     * @return
     */
    String getImgNameById(int imgId);

    /**
     * 根据图片id删掉对应图片信息
     * @param imgId
     * @return
     */
    boolean delImgById(int imgId);
}
