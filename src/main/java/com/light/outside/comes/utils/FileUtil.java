package com.light.outside.comes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
public class FileUtil {
    private static final String PATH = "photo/";
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 保存
     *
     * @param file
     * @return
     */
    public static String saveFile(MultipartFile file) {
        String file_name = null;
        try {
            if (!file.isEmpty()) {
                file_name = String.valueOf(System.currentTimeMillis());
                byte[] bytes = file.getBytes();
                file_name = PATH + file_name + ".jpg";
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(file_name)));
                stream.write(bytes);
                stream.close();
                LOG.info("You successfully uploaded" + file.getName() + " to path file " + file_name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_name;
    }

}
