package com.bh.uhome.bhuhome.activity.loginmoudle.login;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.activity.loginmoudle.home.HomeActivity;
import com.bh.uhome.bhuhome.constant.APPConstant;
import com.bh.uhome.bhuhome.db.sharedprefer.ShareCache;
import com.bh.uhome.bhuhome.entity.StandarDataInfo;
import com.bh.uhome.bhuhome.http.api.ChangePwdAPI;
import com.bh.uhome.bhuhome.http.api.RegisterAPI;
import com.bh.uhome.bhuhome.http.api.VerificationCodeAPI;
import com.bh.uhome.bhuhome.http.parse.ParseDataUtil;
import com.bh.uhome.bhuhome.util.MD5Util;
import com.bh.uhome.bhuhome.util.ToastUtil;
import com.bh.uhome.bhuhome.widget.DeleteEditText;
import com.bh.uhome.bhuhome.widget.PhoneTextWatcher;
import com.bh.uhome.lib.base.app.RxRetrofitApp;
import com.bh.uhome.lib.base.base.BaseActivity;
import com.bh.uhome.lib.base.net.db.ShareToken;
import com.bh.uhome.lib.base.net.exception.ApiException;
import com.bh.uhome.lib.base.net.http.HttpManager;
import com.bh.uhome.lib.base.net.listener.HttpOnNextListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 凌霄
 * @date 2017/11/19.
 * @time 19:01.
 * @description Describe
 */
public class ChangePasswordActivity extends BaseActivity implements HttpOnNextListener {
    @Bind(R.id.edit_text_phone)
    DeleteEditText edit_text_phone;

    @Bind(R.id.edit_check_code)
    DeleteEditText edit_check_code;

    @Bind(R.id.btnCode)
    Button btnCode;

    @Bind(R.id.edit_password)
    DeleteEditText edit_password;

    @Bind(R.id.edit_password_second)
    DeleteEditText edit_password_second;

    @Bind(R.id.button_register)
    Button button_register;

    @Bind(R.id.imgBack)
    ImageView image_view_back;

    @Bind(R.id.txtMidTitle)
    TextView txtMidTitle;

    protected HttpManager manager; //网络管理类
    private ChangePwdAPI changePwdAPI;
    private VerificationCodeAPI codeAPI;
    private String phone;


    public static void actionStart(Activity activity) {
        Intent mIntent = new Intent(activity,ChangePasswordActivity.class);
        activity.startActivity(mIntent);
    }
    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        manager = new HttpManager(this,this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        txtMidTitle.setText("修改密码");

        edit_text_phone.addTextChangedListener(new PhoneTextWatcher(edit_text_phone) {

            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                // 手机号是否输入完成
                boolean phoneOK = editable != null
                        && editable.toString().trim().replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY).length()
                        == APPConstant.PHONE_NUMBER_LENGTH;
                button_register.setEnabled(phoneOK);
            }
        });
    }
    /**
     * 获取验证码
     */
    private void requestCode(){
        codeAPI = new VerificationCodeAPI(phone);
        manager.doHttpDeal(codeAPI);
    }

    @OnClick({R.id.btnCode, R.id.button_register,R.id.imgBack})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCode:
                if (TextUtils.isEmpty(edit_text_phone.getText().toString())){
                    ToastUtil.showLong(ChangePasswordActivity.this,"请输入手机号");
                    return;
                }
                phone = edit_text_phone.getText().toString().trim().replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY);
                requestCode();
                break;
            case R.id.button_register:
                requestRegister();
                break;
            case R.id.imgBack:
                finish();
                break;
        }
    }

    private void requestRegister() {
        phone = edit_text_phone.getText().toString().trim().replace(APPConstant.STRING_ONE_BLANK, APPConstant.STRING_EMPTY);
        if (TextUtils.isEmpty(phone)){
            ToastUtil.showLong(ChangePasswordActivity.this,"请输入手机号");
            return;
        }
        if (!phone.matches(APPConstant.PHONE_REGX)){
            ToastUtil.showLong(ChangePasswordActivity.this,"请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(edit_check_code.getText().toString())){
            ToastUtil.showLong(ChangePasswordActivity.this,"请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(edit_password.getText().toString())){
            ToastUtil.showLong(ChangePasswordActivity.this,"请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(edit_password_second.getText().toString())){
            ToastUtil.showLong(ChangePasswordActivity.this,"请输入新密码");
            return;
        }

        if (!edit_password.getText().toString().equals(edit_password_second.getText().toString())){
            ToastUtil.showLong(ChangePasswordActivity.this,"两次输入密码不一致");
            return;
        }

        String oldPwd = MD5Util.getMD5(edit_check_code.getText().toString());
        String pwd = MD5Util.getMD5(edit_password.getText().toString());
        if (TextUtils.isEmpty(pwd)){
            ToastUtil.showLong(ChangePasswordActivity.this,"校验密码错误");
            return;
        }

        changePwdAPI = new ChangePwdAPI(phone,oldPwd,pwd);
        manager.doHttpDeal(changePwdAPI);

    }

    @Override
    public void onNext(String resulte, String method) {
        if (ChangePwdAPI.METHOD.equals(method)){
            StandarDataInfo info = ParseDataUtil.paseJsonData(resulte,StandarDataInfo.class,ChangePasswordActivity.this);
            if (info != null && 1 == info.getCode()){
                ToastUtil.showLong(ChangePasswordActivity.this,"修改密码成功，请重新登录！");
                ShareToken.getInstance(this).saveToken("");
                LoginNewActivity.actionStart(this, ShareCache.getInstance(this).getUserName());
                finish();
            }
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if (RegisterAPI.METHOD.equals(method)){
            ToastUtil.showLong(ChangePasswordActivity.this,"修改密码失败");
        }
    }
}
