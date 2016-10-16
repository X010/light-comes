package com.light.outside.comes.service;

import com.light.outside.comes.model.FocusImageModel;
import com.light.outside.comes.mybatis.mapper.FocusImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by b3st9u on 16/10/15.
 */
@Service
public class FocusImageService {
    @Autowired
    private FocusImageDao focusImageDao;

    public int addFocusImage(FocusImageModel focusImageModel) {
        int count = focusImageDao.insertFocusImage(focusImageModel);
        return count;
    }

    public int updateFocusImage(FocusImageModel focusImageModel) {
        int count = focusImageDao.updateFocusImage(focusImageModel);
        return count;
    }

    public List<FocusImageModel> queryFocusImageByColumn(int column){
        List<FocusImageModel> focusImageModels=focusImageDao.queryFocusImagesByColumn(column);
        return focusImageModels;
    }

}
