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
import android.view.View;
import android.widget.Button;


import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.constant.APPConstant;
import com.bh.uhome.bhuhome.util.ToastUtil;
import com.bh.uhome.bhuhome.widget.DeleteEditText;
import com.bh.uhome.bhuhome.widget.PhoneTextWatcher;
import com.bh.uhome.lib.base.base.BaseActivity;
import com.bh.uhome.lib.base.net.exception.ApiException;
import com.bh.uhome.lib.base.net.http.HttpManager;
import com.bh.uhome.lib.base.net.listener.HttpOnNextListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码
 *
 * @author zhujinlong@ichoice.com
 * @date 2016/7/14
 * @time 14:23
 * @description Describe the place where the class needs to pay attention.
 */
public class FastRetrievePasswordActivity extends BaseActivity implements HttpOnNextListener {

    @Bind(R.id.edit_text_phone)
    DeleteEditText editTextPhone;

    @Bind(R.id.button_get_code)
    Button buttonGetCode;
    private String phone;
    protected HttpManager manager; //网络管理类

    public static void actionStart(Activity activity) {
        Intent mIntent = new Intent(activity,FastRetrievePasswordActivity.class);
        activity.startActivity(mIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_retrieve_password);
        ButterKnife.bind(this);
        manager = new HttpManager(this,this);
        initView();
    }

    @Override
    protected void initContentView() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    private void initView() {
        // 通过 监听文本变化,动态插入空格,将手机号按 3 4 4 分割
        editTextPhone.addTextChangedListener(new PhoneTextWatcher(editTextPhone) {

            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                // 按钮是否可点击
                buttonGetCode.setEnabled(editable != null && editable.toString().trim()
                        .replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY)
                        .length() == APPConstant.PHONE_NUMBER_LENGTH);
            }
        });
    }

    @OnClick({R.id.image_view_back, R.id.button_get_code})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_back:
                //返回
                finish();
                break;
            case R.id.button_get_code:
                //获取验证码
                phone = editTextPhone.getText().toString().trim()
                        .replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY);
                if (!phone.matches(APPConstant.PHONE_REGX)) {
                    ToastUtil.showLong(FastRetrievePasswordActivity.this,getString(R.string.string_right_number_remind));
                } else {
//                    ResetPasswordAPI.getVerificationCode(this, phone, new RedAgateRequestListener<StandardMessage>() {
//                        @Override
//                        public void requestSuccess(StandardMessage standardMessage) {
//                            loadingDialog.dismiss();
//                            FastCodeCheckActivity.actionStart(FastRetrievePasswordActivity.this, phone, true);
//                        }
//
//                        @Override
//                        public void requestFailure(String s, String s1) {
//                            loadingDialog.dismiss();
//                            ToastUtils.toastShort(s, s1, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
//                        }
//                    });
                }
                break;
        }
    }

    /**
     * 启动/重启生命周期.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 暂停生命周期.
     */
    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void onNext(String resulte, String method) {

    }

    @Override
    public void onError(ApiException e, String method) {

    }
}