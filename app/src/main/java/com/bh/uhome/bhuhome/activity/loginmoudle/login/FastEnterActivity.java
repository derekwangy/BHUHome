package com.bh.uhome.bhuhome.activity.loginmoudle.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.constant.APPConstant;
import com.bh.uhome.bhuhome.entity.StandarDataInfo;
import com.bh.uhome.bhuhome.entity.UserInfo;
import com.bh.uhome.bhuhome.http.api.VerificationCodeAPI;
import com.bh.uhome.bhuhome.http.parse.ParseDataUtil;
import com.bh.uhome.bhuhome.widget.DeleteEditText;
import com.bh.uhome.bhuhome.widget.PhoneTextWatcher;
import com.bh.uhome.lib.base.base.BaseActivity;
import com.bh.uhome.lib.base.net.exception.ApiException;
import com.bh.uhome.lib.base.net.http.HttpManager;
import com.bh.uhome.lib.base.net.listener.HttpOnNextListener;
import com.bh.uhome.lib.base.toast.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 快速登录
 *
 * @author zhujinlong@ichoice.com
 * @date 2016/7/14
 * @time 14:23
 * @description Describe the place where the class needs to pay attention.
 */
public class FastEnterActivity extends BaseActivity implements HttpOnNextListener {
    public static final String PARAM_PHONE = "phone";

    @Bind(R.id.edit_text_phone)
    DeleteEditText editTextPhone;

    @Bind(R.id.button_login)
    Button buttonLogin;

    private String phone;
    protected HttpManager manager; //网络管理类
    private VerificationCodeAPI codeAPI;
    public static void actionStart(@NonNull Activity activity, String phone) {
        Intent intent = new Intent(activity,FastEnterActivity.class);
        if (!TextUtils.isEmpty(phone)) {
            Bundle bundle = new Bundle();
            bundle.putString(PARAM_PHONE, phone);
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_enter);
        ButterKnife.bind(this);
        manager = new HttpManager(this,this);
        editTextPhone.addTextChangedListener(new PhoneTextWatcher(editTextPhone) {

            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                // 更新按钮点击状态
                buttonLogin.setEnabled(editable != null && editable.toString().trim()
                        .replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY).length() == APPConstant.PHONE_NUMBER_LENGTH);
            }
        });
        phone = getIntent().getStringExtra(PARAM_PHONE);
        if (!TextUtils.isEmpty(phone)) {
            editTextPhone.setText(phone);
            editTextPhone.setSelection(editTextPhone.getText().toString().length());
            buttonLogin.setEnabled(true);
        }
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

    private void requestCode(){
        codeAPI = new VerificationCodeAPI(phone);
        manager.doHttpDeal(codeAPI);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        phone = intent.getStringExtra(PARAM_PHONE);
        if (!TextUtils.isEmpty(phone)) {
            editTextPhone.setText(phone);
            editTextPhone.setSelection(editTextPhone.getText().toString().length());
            buttonLogin.setEnabled(true);
        }
    }

    @OnClick({R.id.text_view_password, R.id.button_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_password:
                //密码登录
                phone = editTextPhone.getText().toString().trim().replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY);
                LoginNewActivity.actionStart(this, !phone.matches(APPConstant.PHONE_REGX) ? null : phone);
                break;
            case R.id.button_login:
                //登录
                phone = editTextPhone.getText().toString().trim().replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY);
                if (!phone.matches(APPConstant.PHONE_REGX)) {
                    ToastUtil.showLong(this,getString(R.string.string_right_number_remind));
                } else {
                    /*LoginAPI.getVerificationCode(this, phone, new RedAgateRequestListener<StandardMessage>() {
                        @Override
                        public void requestSuccess(StandardMessage standardMessage) {
                            loadingDialog.dismiss();
                            FastCodeCheckActivity.actionStart(FastEnterActivity.this, phone, false);
                        }

                        @Override
                        public void requestFailure(String s, String s1) {
                            loadingDialog.dismiss();
                            ToastUtils.toastShort(s, s1, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
                        }
                    });*/
                    requestCode();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
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
        if (VerificationCodeAPI.METHOD.equals(method)){
            StandarDataInfo info = ParseDataUtil.paseJsonData(resulte,StandarDataInfo.class,FastEnterActivity.this);
            if (1 == info.getCode()){
                FastCodeCheckActivity.actionStart(FastEnterActivity.this, phone, false);
            }
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if (VerificationCodeAPI.METHOD.equals(method)){
            ToastUtil.showLong(FastEnterActivity.this,"验证码获取失败");
        }
    }
}