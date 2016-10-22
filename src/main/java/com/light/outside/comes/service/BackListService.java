package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.model.BackList;
import com.light.outside.comes.model.PageModel;
import com.light.outside.comes.model.PageResult;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.utils.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class BackListService {

    @Autowired
    private PersistentDao persistentDao;


    /**
     * 添加黑名单
     *
     * @param backList
     */
    public void addBackList(BackList backList) {
        Preconditions.checkNotNull(backList);
        BackList backListOld = this.persistentDao.getBackListByPhoneAndCtype(backList.getPhone(), backList.getCtype());
        if (backListOld == null) {
            this.persistentDao.addBackList(backList);
        }
    }


    public BackList getBackListByPhoneAndCtype(String phone, int ctype) {
        return this.persistentDao.getBackListByPhoneAndCtype(phone, ctype);
    }


    /**
     * 根据ID获取黑名单
     *
     * @param id
     * @return
     */
    public BackList getBackListById(long id) {
        Preconditions.checkArgument(id > 0);
        return this.persistentDao.getBackListById(id);
    }

    /**
     * 删除黑名单
     *
     * @param id
     */
    public void deleteBackList(long id) {
        Preconditions.checkArgument(id > 0);
        BackList backList = this.getBackListById(id);
        backList.setStatus(CONST.RAFFLE_STATUS_DELETE);
        this.persistentDao.updateBackList(backList);
    }


    /**
     * 分页获取黑名单
     *
     * @param pageModel
     * @return
     */
    public PageResult<BackList> getBackLists(PageModel pageModel) {
        Preconditions.checkNotNull(pageModel);
        int total = this.persistentDao.totalBackList();
        List<BackList> backLists = this.persistentDao.getBackLists(pageModel.getStart(), pageModel.getSize());
        PageResult<BackList> backListPageResult = new PageResult<BackList>();
        backListPageResult.setData(backLists);
        backListPageResult.setPageModel(pageModel);
        backListPageResult.setTotal(total);
        return backListPageResult;
    }
}
