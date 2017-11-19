package com.bh.uhome.bhuhome.http.api.body;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 19:35.
 * @description Describe
 */
public class RegisterBody {
    String userName;
    String checkCode;
    String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
