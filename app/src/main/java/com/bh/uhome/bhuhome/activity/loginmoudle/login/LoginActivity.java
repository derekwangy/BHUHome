package com.bh.uhome.bhuhome.activity.loginmoudle.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.widget.PhoneTextWatcher;
import com.bh.uhome.lib.base.base.BaseActivity;


/**
 * 登陆页面.
 */
public class LoginActivity extends BaseActivity implements LoginContract.ILoginView {

    private LoginContract.ILoginPresenter loginPresenter;
    private EditText editText;
    private Button buttonLogin;
    private String phoneNumber;


    public static void actionStart(Activity activity){
        activity.startActivity(new Intent(activity,LoginActivity.class));
    }


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        editText = findView(R.id.edit_text_phone);
        buttonLogin = findView(R.id.button_login);
    }
    @Override
    protected void initData() {
        editText.addTextChangedListener(new PhoneTextWatcher(editText) {

            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                // 更新按钮点击状态
                buttonLogin.setEnabled(editable != null && editable.toString().trim().replace(" ", "").length() == 11);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取验证码 18658838803
                phoneNumber = editText.getText().toString().replace(" ", "");
                loginPresenter.getVerifyCode(phoneNumber);
            }
        });
    }




    /**
     * 注入 Presenter.
     *
     * @param presenter Presenter 对象.
     */
    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {
        loginPresenter = presenter;
    }



    @Override
    public void showMsg(String msg) {
        //Toast 服务器返回的信息
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void getCodeSuccess() {

    }


}
