package com.bh.uhome.bhuhome.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bh.uhome.bhuhome.util.NetworkUtil;


/**
 * 网络状态监听
 * @author 凌霄
 * @date 2017/6/22.
 * @time 20:05.
 * @description Describe
 */
public class NetWorkStateReceiver extends BroadcastReceiver {

    public static NetWorkStateReceiver.OnChangeNetListener netListener = null;
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String status = NetworkUtil.getConnectivityStatusString(context);

        Log.e("Receiver ", "" + status);

        if (status.equals(NetworkUtil.NOT_CONNECT)) {
            Log.e("Receiver ", "not connection");
            if (netListener != null){
                netListener.netChangeListener(false);
            }

        } else {
            Log.e("Receiver ", "connected to internet");
            if (netListener != null){
                netListener.netChangeListener(true);
            }
        }

    }

    public static void setOnChangeNetListener(OnChangeNetListener listener){
        netListener = listener;
    }
    public interface OnChangeNetListener{
        void netChangeListener(boolean isHasNet);
    }

}