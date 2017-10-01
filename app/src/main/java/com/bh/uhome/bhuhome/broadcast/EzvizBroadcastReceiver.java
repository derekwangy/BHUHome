package com.bh.uhome.bhuhome.broadcast;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.activity.main.cameralist.EZRealPlayActivity;
import com.bh.uhome.bhuhome.app.AppApplication;
import com.bh.uhome.bhuhome.util.EZUtils;
import com.videogo.constant.Constant;
import com.videogo.constant.IntentConsts;
import com.videogo.openapi.EzvizAPI;
import com.videogo.openapi.bean.EZAccessToken;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.util.Utils;

/**
 * @author 凌霄
 * @date 2017/9/30.
 * @time 13:44.
 * @description Describe
 */
public class EzvizBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "EzvizBroadcastReceiver";
    private EZCameraInfo cameraInfo = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            EzvizAPI.getInstance().refreshNetwork();
        } else if (action.equals(Constant.ADD_DEVICE_SUCCESS_ACTION)) {
            String deviceId = intent.getStringExtra(IntentConsts.EXTRA_DEVICE_ID);
            Utils.showToast(context, context.getString(R.string.device_is_added, deviceId));
        } else if (action.equals(Constant.OAUTH_SUCCESS_ACTION)) {
            Log.i(TAG, "onReceive: OAUTH_SUCCESS_ACTION");
//            Intent toIntent = new Intent(context, EZCameraListActivity.class);
//            toIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            /*******   获取登录成功之后的EZAccessToken对象   *****/
            EZAccessToken token = AppApplication.getOpenSDK().getEZAccessToken();
            AppApplication.YS_TOKEN = token.getAccessToken();
            AppApplication.getOpenSDK().setAccessToken(AppApplication.YS_TOKEN);
//            context.startActivity(toIntent);

//            cameraInfo = EZUtils.getCameraInfoFromDevice(deviceInfo, 0);
//            Intent toIntent = new Intent(context, EZRealPlayActivity.class);
//            toIntent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
//            toIntent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
//            context.startActivity(toIntent);

        }
    }
}
