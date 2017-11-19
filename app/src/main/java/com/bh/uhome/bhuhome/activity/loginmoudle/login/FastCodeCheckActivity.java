package com.bh.uhome.bhuhome.activity.loginmoudle.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.activity.loginmoudle.home.HomeActivity;
import com.bh.uhome.bhuhome.entity.StandarDataInfo;
import com.bh.uhome.bhuhome.entity.UserInfo;
import com.bh.uhome.bhuhome.http.api.LoginApi;
import com.bh.uhome.bhuhome.http.api.LoginCodeApi;
import com.bh.uhome.bhuhome.http.api.VerificationCodeAPI;
import com.bh.uhome.bhuhome.http.parse.ParseDataUtil;
import com.bh.uhome.bhuhome.util.TextTimeDown;
import com.bh.uhome.lib.base.base.BaseActivity;

import com.bh.uhome.lib.base.log.LogUtil;
import com.bh.uhome.lib.base.net.exception.ApiException;
import com.bh.uhome.lib.base.net.http.HttpManager;
import com.bh.uhome.lib.base.net.listener.HttpOnNextListener;
import com.bh.uhome.lib.base.toast.ToastUtil;
import com.jungly.gridpasswordview.GridPasswordView;


import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.FormUrlEncoded;

/**
 * 验证码校验页面
 *
 * @author zhujinlong@ichoice.com
 * @date 2016/7/14
 * @time 14:34
 * @description Describe the place where the class needs to pay attention.
 */
public class FastCodeCheckActivity extends BaseActivity implements HttpOnNextListener {
    public static final String PARAM_PHONE = "phone";
    public static final String PARAM_RETRIEVE = "retrieve";

    @Bind(R.id.text_view_get_code)
    TextView textViewGetCode;

    @Bind(R.id.text_code)
    GridPasswordView textCode;

    @Bind(R.id.image_view_back)
    ImageView imageViewBack;

    String phone;
    boolean retrieve = false;
    private TextTimeDown timeDown;
    private long timeLeft = 30 * 1000;
    private long lastTime; //记录上一次获取语音验证码的时间

    private VerificationCodeAPI codeAPI;
    protected HttpManager manager; //网络管理类
    private LoginCodeApi loginCodeApi;

    public static void actionStart(Activity activity, String phone, boolean retrieve) {
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_PHONE, phone);
        bundle.putBoolean(PARAM_RETRIEVE, retrieve);
        Intent mIntnet = new Intent(activity,FastCodeCheckActivity.class);
        mIntnet.putExtras(bundle);
        activity.startActivity(mIntnet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_check_code);
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

    /**
     * 获取验证码
     */
    private void requestCode(){
        codeAPI = new VerificationCodeAPI(phone);
        manager.doHttpDeal(codeAPI);
    }

    /**
     * 验证码登录
     */
    private void loginCode(String code){
        loginCodeApi = new LoginCodeApi(phone,code);
        manager.doHttpDeal(loginCodeApi);
    }

    private void initView() {
        phone = getIntent().getStringExtra(PARAM_PHONE);
        retrieve = getIntent().getBooleanExtra(PARAM_RETRIEVE, false);
        timeDown = new TextTimeDown(textViewGetCode, timeLeft, 1000);
        timeDown.setCountDownListen(new TextTimeDown.CountDownListen() {
            @Override
            public void OnTick(TextView textView, long l) {
                timeLeft = l;
                textView.setText(String.format(Locale.CHINA, getString(R.string.string_retry_count_down), l / 1000));
            }

            @Override
            public void onFinish(TextView textView) {
                timeLeft = 0;
                textView.setText(getString(R.string.string_retry));
                textView.setTextColor(Color.parseColor("#3d94ea"));
            }
        });
        timeDown.start();
        textViewGetCode.setTextColor(Color.parseColor("#a7a7a7"));
        // 验证码以明文显示
        textCode.setPasswordVisibility(true);
        // 验证码输入监听,输入完毕后主动调用接口
        textCode.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String s) {

            }

            @Override
            public void onInputFinish(String code) {
                checkCode(code);
            }
        });
        textCode.postDelayed(new Runnable() {
            @Override
            public void run() {
                textCode.performClick();//模拟点击事件,主动弹出软键盘
                ToastUtil.showLong(FastCodeCheckActivity.this,getString(R.string.string_send_code));
            }
        }, 100);
    }

    @OnClick({R.id.image_view_back, R.id.text_view_get_code, R.id.text_view_audio})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_back:
                //返回
                onBackPressed();
                break;
            case R.id.text_view_get_code:
                //获取验证码
                getCode();
                break;
            case R.id.text_view_audio:
                //语音验证
                if (lastTime == 0 || System.currentTimeMillis() - lastTime >= 60000) {
                    // 60s 内不重复获取
                    if (retrieve) {// 找回密码-获取语音验证码
//                        ResetPasswordAPI.getVoiceCode(this, phone, new RedAgateRequestListener<StandardMessage>() {
//                            @Override
//                            public void requestSuccess(StandardMessage standardMessage) {
//                                lastTime = System.currentTimeMillis();
//                                ToastUtils.toastShort(getString(R.string.string_send_voice), ToastUtils.STYLE_DEFAULT);
//                            }
//
//                            @Override
//                            public void requestFailure(String s, String s1) {
//                                lastTime = 0;
//                                ToastUtils.toastShort(s, s1, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
//                            }
//                        });
                    } else { // 快捷登录-获取语音验证码
//                        LoginAPI.getVoiceCode(this, phone, new RedAgateRequestListener<StandardMessage>() {
//                            @Override
//                            public void requestSuccess(StandardMessage standardMessage) {
//                                lastTime = System.currentTimeMillis();
//                                ToastUtil.toastShort(getString(R.string.string_send_voice), ToastUtils.STYLE_DEFAULT);
//                            }
//
//                            @Override
//                            public void requestFailure(String s, String s1) {
//                                lastTime = 0;
//                                ToastUtils.toastShort(s, s1, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
//                            }
//                        });
                    }
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.string_send_voice_ok))
                            .setPositiveButton(getString(R.string.string_confirm_submit), null)
                            .create()
                            .show();
                }
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        if (timeLeft > 0) {
            return;
        }
        if (retrieve) { // 重置
//            ResetPasswordAPI.getVerificationCode(this, phone, new RedAgateRequestListener<StandardMessage>() {
//                @Override
//                public void requestSuccess(StandardMessage standardMessage) {
//                    loadingDialog.dismiss();
//                    timeDown.start();
//                    textViewGetCode.setTextColor(Color.parseColor("#a7a7a7"));
//                }
//
//                @Override
//                public void requestFailure(String s, String s1) {
//                    loadingDialog.dismiss();
//                    ToastUtils.toastShort(s, s1, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
//                }
//            });
            requestCode();
        } else { // 快速登陆
//            LoginAPI.getVerificationCode(this, phone, new RedAgateRequestListener<StandardMessage>() {
//                @Override
//                public void requestSuccess(StandardMessage standardMessage) {
//                    loadingDialog.dismiss();
//                    timeDown.start();
//                    textViewGetCode.setTextColor(Color.parseColor("#a7a7a7"));
//                }
//
//                @Override
//                public void requestFailure(String errorCode, String errorMessage) {
//                    loadingDialog.dismiss();
//                    ToastUtils.toastShort(errorCode, errorMessage, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
//                }
//            });

        }
    }

    /**
     * 重置密码时校验验证码 / 快速登录
     */
    private void checkCode(final String code) {
        if (retrieve) {// 重置密码
//            ResetPasswordAPI.checkVerificationCode(this, phone, code, new RedAgateRequestListener<StandardMessage>() {
//                @Override
//                public void requestSuccess(StandardMessage standardMessage) {
//                    loadingDialog.dismiss();
//                    FastSetPasswordActivity.actionStart(FastCodeCheckActivity.this, code, phone, retrieve);
//                    finish();
//                }
//
//                @Override
//                public void requestFailure(String s, String s1) {
//                    loadingDialog.dismiss();
//                    textCode.clearPassword();
//                    ToastUtils.toastShort(s, s1, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
//                }
//            });
        } else {   // 快速登录
//            LoginAPI.login(this, phone, code, true, new RedAgateRequestListener<UserInfo_>() {
//                @Override
//                public void requestSuccess(UserInfo user) {
//
//                }
//
//                @Override
//                public void requestFailure(String s, String s1) {
//                    ToastUtil.toastShort(s, s1, ToastUtils.STYLE_FAIL_OR_UNUSUAL);
//                }
//            });

            loginCode(code);
        }
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
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.string_code_remind))
                .setNegativeButton(getString(R.string.cancel), null)
                .setPositiveButton(getString(R.string.string_confirm_submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeDown != null) {
            timeDown.cancel();
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if (VerificationCodeAPI.METHOD.equals(method)){
            StandarDataInfo info = ParseDataUtil.paseJsonData(resulte,StandarDataInfo.class,FastCodeCheckActivity.this);
            if (1 == info.getCode()){
                timeDown.start();
                textViewGetCode.setTextColor(Color.parseColor("#a7a7a7"));
            }
        }else if (LoginCodeApi.METHOD.equals(method)){
            UserInfo user = ParseDataUtil.paseJsonData(resulte,UserInfo.class,FastCodeCheckActivity.this);
            if (1 == user.getCode()){
                HomeActivity.actionStart(this,0);
                finish();
            }
        }

    }

    @Override
    public void onError(ApiException e, String method) {
        if (VerificationCodeAPI.METHOD.equals(method)){
            ToastUtil.showLong(FastCodeCheckActivity.this,"验证码获取失败");
        }else if (LoginApi.METHOD.equals(method)) {
            ToastUtil.showShort(this, getResources().getString(R.string.service_exception));
        }
    }
}