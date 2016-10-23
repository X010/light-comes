package com.light.outside.comes.model;

/**
 * 用户抽奖计数
 * Created by b3st9u on 16/10/23.
 */
public class RaffleUserModel {
    private long id;
    private long uid;
    private long rid;
    private int count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
