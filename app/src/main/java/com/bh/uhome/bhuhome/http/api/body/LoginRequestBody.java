package com.bh.uhome.bhuhome.http.api.body;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 14:52.
 * @description Describe
 */
public class LoginRequestBody {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
