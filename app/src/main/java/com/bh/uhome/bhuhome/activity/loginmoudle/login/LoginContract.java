package com.bh.uhome.bhuhome.activity.loginmoudle.login;


import com.bh.uhome.lib.base.base.BasePresenter;
import com.bh.uhome.lib.base.base.BaseView;

/**
 * 登陆契约类.
 */
public interface LoginContract {

    /**
     * 登陆 View 接口定义.
     */
    interface ILoginView extends BaseView<ILoginPresenter> {

        /**
         * 弹出提示信息
         * @param msg
         */
        void showMsg(String msg);

        /**
         * 显示加载框
         */
        void showLoadingView();

        /**
         * 关闭加载框
         */
        void hideLoadingView();

        /**
         * 验证码获取成功
         */
        void getCodeSuccess();


    }

    /**
     * 登陆 Presenter 接口定义.
     */
    interface ILoginPresenter extends BasePresenter {
        // TODO

        /**
         * 获取短信验证码.
         */
        void getVerifyCode(String phoneNumber);

    }
}
