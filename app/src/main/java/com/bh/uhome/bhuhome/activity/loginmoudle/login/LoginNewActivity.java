/**
 * Copyright (c) 2015 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 */
package com.bh.uhome.bhuhome.activity.loginmoudle.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.activity.loginmoudle.guide.GuideActivity;
import com.bh.uhome.bhuhome.activity.loginmoudle.home.HomeActivity;
import com.bh.uhome.bhuhome.constant.APPConstant;
import com.bh.uhome.bhuhome.entity.UserInfo;
import com.bh.uhome.bhuhome.http.api.LoginApi;
import com.bh.uhome.bhuhome.http.parse.ParseDataUtil;
import com.bh.uhome.bhuhome.util.MD5Util;
import com.bh.uhome.bhuhome.widget.DeleteEditText;
import com.bh.uhome.bhuhome.widget.PhoneTextWatcher;
import com.bh.uhome.lib.base.base.BaseActivity;
import com.bh.uhome.lib.base.log.LogUtil;
import com.bh.uhome.lib.base.net.exception.ApiException;
import com.bh.uhome.lib.base.net.http.HttpManager;
import com.bh.uhome.lib.base.net.listener.HttpOnNextListener;
import com.bh.uhome.lib.base.toast.ToastUtil;
import com.videogo.util.Utils;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.bh.uhome.bhuhome.activity.loginmoudle.login.FastEnterActivity.PARAM_PHONE;

/**
 * 新模式密码登录
 *
 * @author 凌霄
 * @date 2017/10/18
 * @time 13:49
 * @description Describe the place where the class needs to pay attention.
 */
public class LoginNewActivity extends BaseActivity implements HttpOnNextListener {
    private static final String TAG = "LoginNewActivity";
    public static final String PARAM_PHONE = "phone";

    @Bind(R.id.edit_text_phone)
    DeleteEditText editTextPhone;

    @Bind(R.id.edit_text_password)
    DeleteEditText editTextPassword;

    @Bind(R.id.button_login)
    Button buttonLogin;

    boolean phoneOK, passwordOK;
    @Bind(R.id.image_logo)
    ImageView imageLogo;
//    @Bind(R.id.image_right_button)
//    ImageView imageRightButton;
    private String phone;
    private String password;

    //新网络请求管理类
    protected HttpManager manager; //网络管理类
    private LoginApi loginApi = null;


    public static void actionStart(Activity activity, String phone) {
        Intent intent = new Intent(activity,LoginNewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (!TextUtils.isEmpty(phone)) {
            Bundle bundle = new Bundle();
            bundle.putString(PARAM_PHONE, phone);
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_enter_with_password);
        ButterKnife.bind(this);
        /*初始化数据*/
        manager = new HttpManager(this,LoginNewActivity.this);
        editTextPhone.addTextChangedListener(new PhoneTextWatcher(editTextPhone) {

            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                // 手机号是否输入完成
                phoneOK = editable != null
                        && editable.toString().trim().replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY).length()
                        == APPConstant.PHONE_NUMBER_LENGTH;
                buttonLogin.setEnabled(passwordOK && phoneOK);
            }
        });
        phone = getIntent().getStringExtra(PARAM_PHONE);
//        phone = "13671508758";
        if (!TextUtils.isEmpty(phone)) {
            editTextPhone.setText(phone);
            editTextPhone.setSelection(editTextPhone.getText().toString().length());
            phoneOK = true;
        }

    }

    @Override
    protected void initContentView() {
//        setContentView(R.layout.activity_fast_enter_with_password);
//        ButterKnife.bind(this);
//        /*初始化数据*/
//        manager = new HttpManager(this,LoginNewActivity.this);
//        editTextPhone.addTextChangedListener(new PhoneTextWatcher(editTextPhone) {
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                super.afterTextChanged(editable);
//                // 手机号是否输入完成
//                phoneOK = editable != null
//                        && editable.toString().trim().replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY).length()
//                        == APPConstant.PHONE_NUMBER_LENGTH;
//                buttonLogin.setEnabled(passwordOK && phoneOK);
//            }
//        });
//        phone = getIntent().getStringExtra(PARAM_PHONE);
//        if (!TextUtils.isEmpty(phone)) {
//            editTextPhone.setText(phone);
//            editTextPhone.setSelection(editTextPhone.getText().toString().length());
//            phoneOK = true;
//        }
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        phone = intent.getStringExtra(PARAM_PHONE);
        if (!TextUtils.isEmpty(phone)) {
            editTextPhone.setText(phone);
            editTextPhone.setSelection(editTextPhone.getText().toString().length());
            phoneOK = true;
        }
        editTextPassword.setText(APPConstant.STRING_EMPTY);
    }

    /**
     * 密码是否输入
     *
     * @param editable Editable
     */
    @SuppressWarnings("unused")
    @OnTextChanged(value = R.id.edit_text_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void passwordEmpty(Editable editable) {
        passwordOK = editable != null && editable.toString().trim().length() >= APPConstant.PASSWORD_MIN_LENGTH;
        buttonLogin.setEnabled(passwordOK && phoneOK);
    }

    /**
     * 选择是否明文显示密码
     *
     * @param compoundButton CompoundButton
     * @param show           是否明文显示
     */
    @SuppressWarnings("unused")
    @OnCheckedChanged(R.id.check_box_password_show)
    public void showPassword(CompoundButton compoundButton, boolean show) {
        int inputType = show ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        editTextPassword.setInputType(inputType);
        editTextPassword.setSelection(editTextPassword.getText().length());
    }

    @OnClick({R.id.button_login, R.id.text_view_password, R.id.text_view_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_password:
                //找回密码
                FastRetrievePasswordActivity.actionStart(this);
                break;
            case R.id.text_view_login:
                //快速登录/注册
//                onBackPressed();
                Intent intent = new Intent(LoginNewActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.button_login:
                //登录
                phone = editTextPhone.getText().toString().trim().replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY);
                if (phone.matches(APPConstant.PHONE_REGX)) {// 验证手机格式正确
                    password = editTextPassword.getText().toString().trim();
                    if (password.length() >= APPConstant.PASSWORD_MIN_LENGTH && password.length() <= APPConstant.PASSWORD_MAX_LENGTH) {// 验证密码格式正确
                        String pwd = MD5Util.getMD5(password);
                        loginApi = new LoginApi(LoginNewActivity.this,phone,pwd);
                        manager.doHttpDeal(loginApi);

                    } else {
                        ToastUtil.showLong(this,getString(R.string.string_password_remind));
                    }
                } else {
                    ToastUtil.showLong(this,getString(R.string.string_right_number_remind));
                }
                break;
//            case R.id.image_right_button:
//                break;
        }
    }

    @Override
    public void onBackPressed() {
//        phone = editTextPhone.getText().toString().trim().replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY);
//        FastEnterActivity.actionStart(this, !phone.matches(APPConstant.PHONE_REGX) ? null : phone);
        finish();
    }

    /**
     * 启动/重启生命周期.
     */
    @Override
    protected void onResume() {
        // 友盟页面统计开始
        super.onResume();
    }

    /**
     * 暂停生命周期.
     */
    @Override
    protected void onPause() {
        // 友盟页面统计结束
        super.onPause();
    }


    @Override
    public void onNext(String resulte, String method) {
        UserInfo user = null;
        if (LoginApi.METHOD.equals(method)){
            user = ParseDataUtil.paseJsonData(resulte,UserInfo.class,LoginNewActivity.this);
            if (1 == user.getCode()){
                HomeActivity.actionStart(this,0);
                finish();
            }
        }

    }



    @Override
    public void onError(ApiException e, String method) {
        if (LoginApi.METHOD.equals(method)) {
            LogUtil.e(TAG, e.toString());
            ToastUtil.showShort(this, getResources().getString(R.string.service_exception));
        }
    }
}