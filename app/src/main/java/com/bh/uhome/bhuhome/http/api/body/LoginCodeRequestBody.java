package com.bh.uhome.bhuhome.http.api.body;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 14:52.
 * @description Describe
 */
public class LoginCodeRequestBody {
    private String userName;
    private String checkCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
