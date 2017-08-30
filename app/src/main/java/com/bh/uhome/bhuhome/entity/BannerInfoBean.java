package com.bh.uhome.bhuhome.entity;

/**
 * 类用途
 *
 * @version V2.6 <描述当前版本功能>
 * @FileName: com.yuyuetech.yuyue.networkservice.model.BannerInfoBean.java
 * @author: derek
 * @date: 2016-06-16 00:52
 */
public class BannerInfoBean {
    private String id;
    private String imgId;
    private String link;
    private int height;
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
