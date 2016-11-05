package com.light.outside.comes.model;

/**
 * Created by b3st9u on 16/11/5.
 */
public class OverchargedRecordViewModel extends OverchargedRecordModel {
    private String good_name;
    private String good_photo;
    private float over_amount;

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getGood_photo() {
        return good_photo;
    }

    public void setGood_photo(String good_photo) {
        this.good_photo = good_photo;
    }

    public float getOver_amount() {
        return over_amount;
    }

    public void setOver_amount(float over_amount) {
        this.over_amount = over_amount;
    }
}
