package com.light.outside.comes.service;

import com.light.outside.comes.model.PastModel;
import com.light.outside.comes.model.PastTotal;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by b3st9u on 16/11/20.
 */
@Service
public class SigninService {

    @Autowired
    private PersistentDao persistentDao;

    public int sigin(UserModel userModel) {
        PastModel pastModel=this.persistentDao.getPastById(0);

        return 0;
    }

    public int shareSignin(UserModel userModel, long fid) {
        return 0;
    }

    public PastTotal getPastTotalByUid(long uid){
        return this.persistentDao.getPastTotalByUser(uid);
    }
}
