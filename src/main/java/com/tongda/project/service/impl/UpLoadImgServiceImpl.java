package com.tongda.project.service.impl;

import com.tongda.project.bean.UpLoadImg;
import com.tongda.project.dao.UpLoadImgDao;
import com.tongda.project.service.UpLoadImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-30 17:34
 */
@Service
public class UpLoadImgServiceImpl implements UpLoadImgService {
    @Autowired
    private UpLoadImgDao upLoadImgDao;

    /**
     * 根据图片id得到图片信息
     * @param imgId
     * @return
     */
    @Override
    public UpLoadImg getUpLoadImgById(int imgId) {
        return upLoadImgDao.getUpLoadImgById(imgId);
    }

    /**
     * 添加图片信息
     * @param upLoadImg
     * @return
     */
    @Override
    public int addUpLoadImg(UpLoadImg upLoadImg) {
        return upLoadImgDao.addUpLoadImg(upLoadImg);
    }

    /**
     * 根据图片id得到图片名称
     * @param imgId
     * @return
     */
    @Override
    public String getImgNameById(int imgId) {
        return upLoadImgDao.getImgNameById(imgId);
    }

    /**
     * 根据图片id删掉对应图片信息
     * @param imgId
     * @return
     */
    @Override
    public boolean delImgById(int imgId) {
        return upLoadImgDao.delImgById(imgId);
    }
}
