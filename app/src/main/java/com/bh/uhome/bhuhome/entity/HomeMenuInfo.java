package com.bh.uhome.bhuhome.entity;

/**
 * @author 凌霄
 * @date 2017/8/31.
 * @time 14:37.
 * @description Describe
 */
public class HomeMenuInfo {
    private String id;
    private String name;
    private String state; //开关状态 0关，1开
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
