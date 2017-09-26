package com.bh.uhome.bhuhome.entity;

/**
 * @author 凌霄
 * @date 2017/9/25.
 * @time 0:01.
 * @description Describe
 */
public class VersionInfo {
    private int versionCode;
    private String versionName;
    private String updateType;//值为1时是强制更新，0时不时强制更新
    private String downloadUrl;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
