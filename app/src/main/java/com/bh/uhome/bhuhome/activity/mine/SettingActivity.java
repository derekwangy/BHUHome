package com.bh.uhome.bhuhome.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.activity.loginmoudle.login.ChangePasswordActivity;
import com.bh.uhome.bhuhome.activity.loginmoudle.login.LoginNewActivity;
import com.bh.uhome.bhuhome.db.sharedprefer.ShareCache;
import com.bh.uhome.bhuhome.entity.StandarDataInfo;
import com.bh.uhome.bhuhome.http.api.LoginOutAPI;
import com.bh.uhome.bhuhome.http.api.RegisterAPI;
import com.bh.uhome.bhuhome.http.parse.ParseDataUtil;
import com.bh.uhome.bhuhome.util.ToastUtil;
import com.bh.uhome.lib.base.base.BaseActivity;
import com.bh.uhome.lib.base.net.db.ShareToken;
import com.bh.uhome.lib.base.net.exception.ApiException;
import com.bh.uhome.lib.base.net.http.HttpManager;
import com.bh.uhome.lib.base.net.listener.HttpOnNextListener;

/**
 * Created by derek on 2017/12/10.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener,HttpOnNextListener {
    private RelativeLayout rLay_find_pwd,rLay_about_us,rLay_logout;
    private TextView text_logout,txtMidTitle;
    private ImageView imgBack;
    protected HttpManager manager; //网络管理类
    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_setting);
        manager = new HttpManager(this,this);
    }

    @Override
    protected void initViews() {
        rLay_find_pwd = findView(R.id.rLay_find_pwd);
        rLay_about_us = findView(R.id.rLay_about_us);
        rLay_logout = findView(R.id.rLay_logout);
        text_logout = findView(R.id.text_logout);
        txtMidTitle = findView(R.id.txtMidTitle);
        imgBack = findView(R.id.imgBack);

        rLay_find_pwd.setOnClickListener(this);
        rLay_about_us.setOnClickListener(this);
        rLay_logout.setOnClickListener(this);
        text_logout.setOnClickListener(this);
        imgBack.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        txtMidTitle.setText("设置");
    }

    @Override
    public void onClick(View view) {
        Intent mIntent = null;
        switch (view.getId()){
            case R.id.rLay_find_pwd:
                mIntent = new Intent(this, ChangePasswordActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rLay_about_us:
                mIntent = new Intent(this, AboutUsActivity.class);
                startActivity(mIntent);
                break;
            case R.id.imgBack:
                finish();
                break;
            case R.id.rLay_logout:
            case R.id.text_logout:
                logoutDialog();
                break;
        }
    }

    private void logoutDialog(){
        new AlertDialog.Builder(SettingActivity.this,R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.string_logout))
                .setMessage(getString(R.string.string_are_you_sure_you_want_to_logout))
                .setNegativeButton(getResources().getString(R.string.string_cancel), null)
                .setPositiveButton(getResources().getString(R.string.string_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                requestLoginout();
            }
        }).show();

    }

    private void requestLoginout() {
        if (TextUtils.isEmpty(ShareCache.getInstance(this).getUserName())){
            ToastUtil.showLong(this,"手机号为空！");
            return;
        }
        manager.doHttpDeal(new LoginOutAPI(ShareCache.getInstance(this).getUserName()));
    }

    @Override
    public void onNext(String resulte, String method) {
        if (LoginOutAPI.METHOD.equals(method)){
            StandarDataInfo info = ParseDataUtil.paseJsonData(resulte,StandarDataInfo.class,this);
            if (info != null && 1 == info.getCode()){
                ShareToken.getInstance(this).saveToken("");
                LoginNewActivity.actionStart(this,ShareCache.getInstance(this).getUserName());
                finish();
            }
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if (RegisterAPI.METHOD.equals(method)){
            ToastUtil.showLong(this,"退出登录失败");
        }
    }
}
