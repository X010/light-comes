package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.model.PastModel;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.utils.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Service
public class PastService {

    @Autowired
    private PersistentDao persistentDao;


    public PastModel getPastModelById() {
        return this.persistentDao.getPastById(CONST.PAST_ID);
    }

    /**
     * 保存签到活动信息
     *
     * @param pastModel
     * @return
     */
    public PastModel svePastModel(PastModel pastModel) {
        Preconditions.checkNotNull(pastModel);
        pastModel.setId(CONST.PAST_ID);
        PastModel oldPast = this.persistentDao.getPastById(CONST.PAST_ID);

        if (oldPast != null) {
            //更新
            this.persistentDao.updatePast(pastModel);
        } else {
            //添加
            pastModel.setCreate_time(new Date());
            this.persistentDao.addPast(pastModel);
        }
        return pastModel;
    }
}
