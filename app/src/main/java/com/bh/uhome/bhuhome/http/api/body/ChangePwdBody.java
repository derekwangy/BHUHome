package com.bh.uhome.bhuhome.http.api.body;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 19:35.
 * @description Describe
 */
public class ChangePwdBody {
    String userName;
    String oldpassword;
    String newpassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
