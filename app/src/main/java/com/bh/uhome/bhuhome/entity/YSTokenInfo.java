package com.bh.uhome.bhuhome.entity;

/**
 * @author 凌霄
 * @date 2017/9/30.
 * @time 10:42.
 * @description Describe
 */
public class YSTokenInfo {

    /**
     * data : {"accessToken":"at.8saeehhy03w15ix51qiej1fud7r4p4ci-2kp2tc9ids-0owhqmi-roj0hw8fo","expireTime":1507343271248}
     * code : 200
     * msg : 操作成功!
     */

    private DataBean data;
    private String code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * accessToken : at.8saeehhy03w15ix51qiej1fud7r4p4ci-2kp2tc9ids-0owhqmi-roj0hw8fo
         * expireTime : 1507343271248
         */

        private String accessToken;
        private long expireTime;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }
    }
}
