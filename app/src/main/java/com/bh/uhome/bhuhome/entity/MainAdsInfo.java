package com.bh.uhome.bhuhome.entity;

/**
 * 主页广告
 *
 * @Author Derek
 * @Date 2015/12/24 18:45.
 */

public class MainAdsInfo {

    public String code;
    private AdsData data;
    public String msg;

    public static class AdsData {
        private AdsBanner banner;
        private AppVersion version;

        public AdsBanner getBanner() {
            return banner;
        }

        public AppVersion getVersion() {
            return version;
        }

        public void setBanner(AdsBanner banner) {
            this.banner = banner;
        }

        public void setVersion(AppVersion version) {
            this.version = version;
        }
    }

    public class AdsBanner{
        private String link;
        private String img_id;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImg_id() {
            return img_id;
        }

        public void setImg_id(String img_id) {
            this.img_id = img_id;
        }
    }

    public class AppVersion{
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

    public void setData(AdsData data) {
        this.data = data;
    }

    public AdsData getData() {
        return data;
    }
}
