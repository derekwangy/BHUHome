package com.bh.uhome.bhuhome.activity.loginmoudle.login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.util.StringUtils;
import com.bh.uhome.bhuhome.util.UIUtils;
import com.bh.uhome.bhuhome.widget.DeleteEditText;
import com.bh.uhome.lib.base.base.BaseActivity;
import com.bh.uhome.lib.base.toast.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 设置密码
 *
 * @author zhujinlong@ichoice.com
 * @date 2016/7/14
 * @time 14:23
 * @description Describe the place where the class needs to pay attention.
 */
public class FastSetPasswordActivity extends BaseActivity {

    @Bind(R.id.text_title)
    TextView textTitle;

    @Bind(R.id.edit_text_password)
    DeleteEditText editTextPassword;

    @Bind(R.id.button_login)
    Button buttonLogin;

    @Bind(R.id.layout_protocol)
    LinearLayout layoutProtocol;

    String phone;                 //手机号
    String code;                  //验证码
    boolean retrieve;             //是否找回密码
    private String password;      //密码


   /* public static void actionStart(Activity activity, String code, String phone, boolean retrieve) {
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putString("phone", phone);
        bundle.putBoolean("retrieve", retrieve);
        ActionUtil.actionStart(activity, FastSetPasswordActivity.class, bundle);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_set_password);
        ButterKnife.bind(this);
        code = getIntent().getStringExtra("code");
        phone = getIntent().getStringExtra("phone");
        retrieve = getIntent().getBooleanExtra("retrieve", false);
        textTitle.setText(retrieve ? "设置新密码" : "设置初始密码");
        layoutProtocol.setVisibility(retrieve ? View.GONE : View.VISIBLE);
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

    /**
     * 按钮是否可点击
     *
     * @param editable Editable
     */
    @SuppressWarnings("unused")
    @OnTextChanged(value = R.id.edit_text_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void phoneEmpty(Editable editable) {
        buttonLogin.setEnabled(editable != null && editable.toString().trim().length() >= 6);
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

    @OnClick({R.id.image_view_back, R.id.text_view_protocol, R.id.button_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_back:
                //返回
                onBackPressed();
                break;
            case R.id.text_view_protocol:
                //注册协议
//                RegisterProtocolActivity.actionStart(this);
                break;
            case R.id.button_login:
                //提交
                password = editTextPassword.getText().toString().trim();
                boolean isFit = StringUtils.isFindRegular(UIUtils.getString(R.string.common_pass_reg), password);
                if (password.length() < 6 || password.length() > 16) {
                    ToastUtil.showLong(this,"请输入6-16位的密码");
                } else if (isFit) {
                    ToastUtil.showLong(this,"请输入字母或者数字");
                } else if (retrieve) {
                    reSetPassword();// 重置密码
                } else {
                    register();     // 注册
                }
                break;
        }
    }

    /**
     * 重置密码
     */
    private void reSetPassword() {
        /*ResetPasswordAPI.resetPassword(this, phone, password, new RedAgateRequestListener<StandardMessage>() {
            @Override
            public void requestSuccess(StandardMessage standardMessage) {
                loadingDialog.dismiss();
                FastPwLoginActivity.actionStart(FastSetPasswordActivity.this, phone);
                finish();
            }

            @Override
            public void requestFailure(String s, String s1) {
                loadingDialog.dismiss();
                ToastUtils.toastShort(s, s1, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
            }
        });*/
    }

    /**
     * 注册
     */
    private void register() {
//        String areaCode = BaseApplication.areaInfo == null || TextUtils.isEmpty(BaseApplication.areaInfo.areaCode) ? "0" : BaseApplication.areaInfo.areaCode;
        /*RegisterAPI.registerAccount(this, phone, password, code, areaCode, new RedAgateRequestListener<StandardMessage>() {
            @Override
            public void requestSuccess(StandardMessage standardMessage) {
                //在系统文件中保持该号码为注册用户
                SharedPreferencesUtil.putData(getApplicationContext(), phone, true);
                WemayConfigDao.login(FastSetPasswordActivity.this, phone, password, loadingDialog, standardMessage.messageContent);
            }

            @Override
            public void requestFailure(String s, String s1) {
                loadingDialog.dismiss();
                ToastUtils.toastShort(s, s1, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("返回会中断当前操作，是否确定放弃设置密码?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();
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
}