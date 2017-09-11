package com.bh.uhome.bhuhome.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.dialog.DownLoadApkDialog;
import com.bh.uhome.bhuhome.entity.MainAdsInfo;
import com.bh.uhome.bhuhome.service.UpdateVersionService;
import com.bh.uhome.lib.base.toast.ToastUtil;


/**
 * 版本更新
 *
 * @Author Derek
 * @Date 2016/1/21 19:40.
 */

public class UpdateVersionUtil implements DownLoadApkDialog.CancleDownloadApkListener {
    private Activity mActivity = null;

    private DownLoadApkDialog downloadDialod = null;
    private AlertDialog alertDialog = null;
    //    private UpdateVersionDialog updateVersionDialog;
    private String updateType = "";
    private String versionNameDB = "";
    private String downUrl = "";
    private MainAdsInfo mainAdsInfo = null;
    //版本更新
    private SharedPreferences sharedPreferences = null;
    public final static String SAVE_UPDATE_VERSION = "update_version_db";//保存Cookie数据库
    public static final String UPDATE_VERSION_CODE = "updateVersionCode";   //版本更新code

    private static final String UPDATE_TYPE_TAG = "1"; //1强制更新,0非强制更新

    public UpdateVersionUtil(Activity mActivity, MainAdsInfo mainAdsInfo) {
        this.mainAdsInfo = mainAdsInfo;
        this.mActivity = mActivity;
        compareVersionUpdate();
    }

    /**
     * 对比版本更新
     */
    private void compareVersionUpdate() {
        try {
            if (mainAdsInfo.getData().getVersion() != null) {
                int localVersionCode = CommonUtil.getAppVersionCode(mActivity);
                int dbVersionCode = mainAdsInfo.getData().getVersion().getVersionCode();
                if (dbVersionCode > localVersionCode) {
                    versionNameDB = mainAdsInfo.getData().getVersion().getVersionName();
                    downUrl = mainAdsInfo.getData().getVersion().getDownloadUrl();
                    updateType = mainAdsInfo.getData().getVersion().getUpdateType();
                    checkVersion();
                } else {
                    ToastUtil.showLong(mActivity, mActivity.getResources().getString(R.string.mine_latest_version));
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 测试弹出对话框时使用：
     */
//    private void compareVersionUpdate() {
//
//
//                int localVersionCode = CommonUtil.getAppVersionCode(mActivity);
//                int dbVersionCode = mainAdsInfo.getData().getVersion().getVersionCode();
//
//                    versionNameDB = mainAdsInfo.getData().getVersion().getVersionName();
//                    downUrl = mainAdsInfo.getData().getVersion().getDownloadUrl();
//                    updateType = mainAdsInfo.getData().getVersion().getUpdateType();
//                    checkVersion();
//
//    }
    /**
     * 检查更新
     */
    private void checkVersion() {
        //downUrl = "https://www.houpix.com/app/android/houpix-2.0.0.apk";
//        updateVersionDialog = new UpdateVersionDialog(mActivity, new ConfirmDialogBtnClickListener() {
//            @Override
//            public void onOKBtnClick() {
//                downloadDialod = new DownLoadApkDialog(mActivity, UpdateVersionUtil.this, downUrl, updateType);
//                downloadDialod.showDialog(0, 0);
//            }
//
//            @Override
//            public void onCancelBtnClick() {
//                if (!updateType.equals("1")) { // 强制更新
//                    saveSharefVersionCode();
//                    updateVersionDialog.dismiss();
//                } else {
//                    ToastUtils.show(mActivity, mActivity.getResources().getString(R.string.mine_ensure_version));
//                }
//            }
//        }, versionNameDB);
//        updateVersionDialog.showDialog(0, 0);

        try {
            alertDialog = new AlertDialog.Builder(mActivity).create();
            // 设置触摸对话框以外的地方取消对话框  true:点击dialog之外和back键关闭  false:点击dialog之外不关闭  20160303 申鹏修改
            alertDialog.setCanceledOnTouchOutside(false);
//          alertDialog.setCancelable(false);//设置触摸对话框以外的地方取消对话框  true:点击dialog之外和back键关闭  false:点击dialog之外和back键都不关闭  20160303 申鹏修改
            alertDialog.show();
            alertDialog.getWindow().setContentView(R.layout.dialog_update_version);

            TextView txtVersionInfo = (TextView) alertDialog.getWindow()
                    .findViewById(R.id.txtVersionInfo);
            txtVersionInfo.setText(UIUtils.getString(R.string.mine_version_latest) + versionNameDB +
                    "," + UIUtils.getString(R.string.mine_version_update_ornot));


            //确定
            alertDialog.getWindow()
                    .findViewById(R.id.dialog_confirm_tv)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            downloadDialod = new DownLoadApkDialog(mActivity, UpdateVersionUtil.this, downUrl, updateType);
//                            downloadDialod.setCanceledOnTouchOutside(false);// 设置触摸对话框以外的地方取消对话框  true:点击dialog之外和back键关闭  false:点击dialog之外不关闭
//                            //downloadDialod.showDialog(0, 0);   //下载对话框关闭,后台静默下载  2016-7-5 修改 by derek

                            // 显示下载对话框     8-18 修改 by derek
                            Intent serviceIntent = new Intent(mActivity, UpdateVersionService.class);
                            serviceIntent.putExtra("url", downUrl);
                            mActivity.startService(serviceIntent);

                            if (alertDialog != null){
                                alertDialog.dismiss();
                            }
                        }

                    });
            //忽略
            alertDialog.getWindow()
                    .findViewById(R.id.dialog_cancel_tv)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!updateType.equals(UPDATE_TYPE_TAG)) { //  非强制更新
                                saveSharefVersionCode();
                                alertDialog.dismiss();
                            } else {
                                ToastUtil.showLong(mActivity, mActivity.getResources().getString(R.string.mine_ensure_version));
                            }
                        }

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void cancleDownloadClick(int position) {
        downloadDialod.cancleDownload();
        alertDialog.dismiss();
//        updateVersionDialog.dismiss();
    }

    /**
     * 保存当前版本code
     */
    public void saveSharefVersionCode() {
        try {
            if (mainAdsInfo.getData().getVersion() != null) {
                sharedPreferences = mActivity.getSharedPreferences(SAVE_UPDATE_VERSION, Context.MODE_PRIVATE);
                sharedPreferences = mActivity.getSharedPreferences(SAVE_UPDATE_VERSION, Context.MODE_PRIVATE);
                int dbVersionCode = mainAdsInfo.getData().getVersion().getVersionCode();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(UPDATE_VERSION_CODE, dbVersionCode + "");
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
