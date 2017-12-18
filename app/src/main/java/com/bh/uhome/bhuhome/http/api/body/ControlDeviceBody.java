package com.bh.uhome.bhuhome.http.api.body;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 19:35.
 * @description Describe
 */
public class ControlDeviceBody {
    String userName;
    String homeName;
    String deviceID;
    String state;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
