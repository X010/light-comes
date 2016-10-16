package com.light.outside.comes.service;

import com.light.outside.comes.model.OverchargedModel;
import com.light.outside.comes.mybatis.mapper.OverchargedDao;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.dao.ReadDao;
import com.light.outside.comes.qbkl.model.Commodity;
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
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Service
public class OverchargedService {


    @Autowired
    private OverchargedDao overchargedDao;
    @Autowired
    private ReadDao readDao;

    /**
     * @return
     */
    public List<Commodity> queryCommodityByName(String name) {
        name = "%" + name + "%";
        return readDao.queryCommodity(name);
    }

    public int saveOvercharged(OverchargedModel overchargedModel) {
        int count = overchargedDao.addOvercharged(overchargedModel);
        return count;
    }
}
