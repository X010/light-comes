package com.light.outside.comes.qbkl.model;

import com.light.outside.comes.model.BaseModel;

import javax.sql.rowset.spi.SyncResolver;
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
public class Commodity extends BaseModel {
    /**
     * 名称
     */
    private String name;

    /**
     * 条形码
     */
    private String barcode;

    /**
     * 商品编码
     */
    private String goodscode;

    /**
     * 图片
     */
    private String picture;

    /**
     * 价格
     */
    private float price;

    /**
     * 销售价
     */
    private float saleprice;

    /**
     * 规格
     */
    private String specification;

    /**
     * 标签
     */
    private String tags;

    private  int kind;

    /**
     * 搜索关键字
     */
    private String searchwords;

    /**
     * 分类ID
     */
    private long categoryid;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 保质期
     */
    private String guarantee;

    private int sn;

    /**
     * 产地
     */
    private String located;

    /**
     * 城市
     */
    private String country;

    /**
     * 规格名称
     */
    private String specs;

    /**
     * 产品模式
     */
    private String productionmode;


    /**
     * 创建时间
     */
    private Date createtime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGoodscode() {
        return goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(float saleprice) {
        this.saleprice = saleprice;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getSearchwords() {
        return searchwords;
    }

    public void setSearchwords(String searchwords) {
        this.searchwords = searchwords;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(long categoryid) {
        this.categoryid = categoryid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getLocated() {
        return located;
    }

    public void setLocated(String located) {
        this.located = located;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getProductionmode() {
        return productionmode;
    }

    public void setProductionmode(String productionmode) {
        this.productionmode = productionmode;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
