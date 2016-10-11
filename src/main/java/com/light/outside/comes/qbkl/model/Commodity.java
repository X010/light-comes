package com.light.outside.comes.qbkl.model;

import com.light.outside.comes.model.BaseModel;

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
     *商品ID
     */
    private int goodsid;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品图片
     */
    private byte[] picture;

    /**
     * 商品售价
     */
    private float price;


    /**
     * 商品售价
     */
    private float saleprice;

    /**
     * 商品规格
     */
    private String specification;

    /**
     * 商品标签
     */
    private String tags;

    /**
     * 商品类型
     */
    private  int kind;


    /**
     * 商品关键词
     */
    private String searchwords;

    /**
     * 商品分类名称
     */
    private String categorie;

    /**
     * 商品品牌
     */
    private String brand;

    /**
     * 商品保质期
     */
    private String guarantee;

    /**
     * 商品图片信息
     */
    private byte[] detail;

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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

    public byte[] getDetail() {
        return detail;
    }

    public void setDetail(byte[] detail) {
        this.detail = detail;
    }
}
