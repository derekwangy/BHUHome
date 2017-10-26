package com.bh.uhome.bhuhome.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 凌霄
 * @date 2017/10/26.
 * @time 17:07.
 * @description Describe
 */
public class DeviceControlInfo {

    /**
     * code : 1
     * msg : 请求成功
     * data : [{"homeName":"佳安别院","devNames":[{"deviceID":"100000aabbcc","state":"1","type":"3","location":"客厅","cameraaccount":"","appkey":"","secretkey":"","cameralistno":""},{"deviceID":"200000aabbcc","state":"1","type":"5","location":"客厅","cameraaccount":"18994388793","appkey":"0e4b3763549e44069c0b83fa27fdd681","secretkey":"6c7c9646a624c46dba0046597e1571e3","cameralistno":"0"}]},{"homeName":"苏州金石","devNames":[{"deviceID":"100000aabbcc","state":"1","type":"1","location":"办公室","cameraaccount":"","appkey":"","secretkey":"","cameralistno":""},{"deviceID":"100000aabbcc","state":"1","type":"2","location":"大门","cameraaccount":"","appkey":"","secretkey":"","cameralistno":""},{"deviceID":"100000aabbcc","state":"1","type":"1","location":"办公室","cameraaccount":"","appkey":"","secretkey":"","cameralistno":""}]}]
     */

    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * homeName : 佳安别院
         * devNames : [{"deviceID":"100000aabbcc","state":"1","type":"3","location":"客厅","cameraaccount":"","appkey":"","secretkey":"","cameralistno":""},{"deviceID":"200000aabbcc","state":"1","type":"5","location":"客厅","cameraaccount":"18994388793","appkey":"0e4b3763549e44069c0b83fa27fdd681","secretkey":"6c7c9646a624c46dba0046597e1571e3","cameralistno":"0"}]
         */

        @SerializedName("homeName")
        private String homeName;
        @SerializedName("devNames")
        private List<DevNamesBean> devNames;

        public String getHomeName() {
            return homeName;
        }

        public void setHomeName(String homeName) {
            this.homeName = homeName;
        }

        public List<DevNamesBean> getDevNames() {
            return devNames;
        }

        public void setDevNames(List<DevNamesBean> devNames) {
            this.devNames = devNames;
        }

        public static class DevNamesBean {
            /**
             * deviceID : 100000aabbcc
             * state : 1
             * type : 3
             * location : 客厅
             * cameraaccount :
             * appkey :
             * secretkey :
             * cameralistno :
             */

            @SerializedName("deviceID")
            private String deviceID;
            @SerializedName("state")
            private String state;
            @SerializedName("type")
            private String type;
            @SerializedName("location")
            private String location;
            @SerializedName("cameraaccount")
            private String cameraaccount;
            @SerializedName("appkey")
            private String appkey;
            @SerializedName("secretkey")
            private String secretkey;
            @SerializedName("cameralistno")
            private String cameralistno;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getCameraaccount() {
                return cameraaccount;
            }

            public void setCameraaccount(String cameraaccount) {
                this.cameraaccount = cameraaccount;
            }

            public String getAppkey() {
                return appkey;
            }

            public void setAppkey(String appkey) {
                this.appkey = appkey;
            }

            public String getSecretkey() {
                return secretkey;
            }

            public void setSecretkey(String secretkey) {
                this.secretkey = secretkey;
            }

            public String getCameralistno() {
                return cameralistno;
            }

            public void setCameralistno(String cameralistno) {
                this.cameralistno = cameralistno;
            }
        }
    }
}
