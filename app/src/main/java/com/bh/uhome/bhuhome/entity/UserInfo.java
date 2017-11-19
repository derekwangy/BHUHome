package com.bh.uhome.bhuhome.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author 凌霄
 * @date 2017/11/14.
 * @time 23:52.
 * @description Describe
 */
public class UserInfo {

    /**
     * code : 1
     * message : Success
     * data :
     */

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
