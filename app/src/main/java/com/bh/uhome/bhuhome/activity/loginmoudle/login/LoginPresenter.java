package com.bh.uhome.bhuhome.activity.loginmoudle.login;


/**
 * 登陆 Presenter 类.
 */
public class LoginPresenter implements LoginContract.ILoginPresenter {

    public LoginContract.ILoginView mILoginView;

    public LoginPresenter(LoginContract.ILoginView mILoginView){
        this.mILoginView = mILoginView;
    }

    /**
     * 订阅.
     */
    @Override
    public void subscribe() {

    }

    /**
     * 取消订阅.
     */
    @Override
    public void unsubscribe() {

    }

    @Override
    public void toActivity(String uri) {

    }


    @Override
    public void getVerifyCode(String phoneNumber) {
        String rx = "[1][34587]\\d{9}";
        if (!phoneNumber.matches(rx)) {
            mILoginView.showMsg("请输入正确的手机号");
        } else {
            mILoginView.showLoadingView();
            try{

            }catch (Exception e){

            }

        }
    }
}
