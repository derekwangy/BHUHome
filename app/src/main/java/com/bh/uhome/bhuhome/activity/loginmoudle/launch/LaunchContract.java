package com.bh.uhome.bhuhome.activity.loginmoudle.launch;


import com.bh.uhome.lib.base.base.BasePresenter;
import com.bh.uhome.lib.base.base.BaseView;

/**
 * @author 凌霄
 * @date 2017/6/15.
 * @time 10:12.
 * @description Describe
 */
public interface LaunchContract {

    /**
     * 登陆 View 接口定义.
     */
    interface ILaunchView extends BaseView<ILaunchPresenter> {

        void adSuccess(String url);
        void adFailed();
        void checkLoginOver(boolean online);

    }

    /**
     * 登陆 Presenter 接口定义.
     */
    interface ILaunchPresenter extends BasePresenter {
        /**
         * 获取广告图片
         */
        void getADImage();

        /**
         * 检查登录
         */
        void checkLogin();
    }

}
