package com.bh.uhome.bhuhome.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;
import java.io.IOException;
import java.io.InputStream;
import android.support.annotation.Nullable;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.util.FileUtil;
import com.google.gson.annotations.Expose;

import java.io.File;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类用途  版本更新--下载apk
 *
 * @version V2.6 <描述当前版本功能>
 * @FileName: com.yuyuetech.yuyue.service.UpdateVersionService.java
 * @author: derek
 * @date: 2016-08-17 17:18
 */
public class UpdateVersionService extends Service {
    private NotificationManager notificationManager;
    private Notification myNotify;
    private int notificationId = 1234;
    private String url;
    private String apkName = "bhuhome.apk";

    public static final int DOWN_SUCCESS = 101;   //下载成功
    public static final int DOWN_FAILED = 102;    //下载失败
    public static final int DOWN_PROGRESS = 103;  //下载过程中,更新进度条


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            int progress = Integer.parseInt(message.obj.toString());
            RemoteViews rv;
            switch (message.what) {
                case DOWN_PROGRESS:  //更改通知栏UI布局
                    rv = new RemoteViews(getPackageName(),
                            R.layout.update_version);
                    rv.setTextViewText(R.id.text_title, getResources().getString(R.string.download_doing));
                    rv.setProgressBar(R.id.progress, 100, progress, false);
                    rv.setTextViewText(R.id.text_content, progress + "%");
                    myNotify.contentView = rv;
                    notificationManager.notify(notificationId, myNotify);
                    break;
                case DOWN_SUCCESS:  //下载成功
                    rv = new RemoteViews(getPackageName(),
                            R.layout.update_version);
                    rv.setTextViewText(R.id.text_title, getResources().getString(R.string.download_finish));
                    rv.setProgressBar(R.id.progress, 100, progress, false);
                    rv.setTextViewText(R.id.text_content, progress + "%");
                    myNotify.contentView = rv;


                    //下载完成,点击可以去安装文件
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);// android.intent.action.VIEW
                    Uri uri=null;
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(getApplicationContext(), "com.bh.uhome.bhuhome.FileProvider", new File(FileUtil.getPath() + apkName));
                    } else {
                        uri = Uri.fromFile(new File(FileUtil.getPath() + apkName));
                    }
                    intent.setDataAndType(uri,"application/vnd.android.package-archive");
                    myNotify.flags = Notification.FLAG_AUTO_CANCEL;
                    myNotify.contentIntent = PendingIntent.getActivity(UpdateVersionService.this, 1, intent, 0);
                    notificationManager.notify(notificationId, myNotify);

                    installAPK();

                    notificationManager.cancel(notificationId);

                    break;
                case DOWN_FAILED:  //下载失败
                    rv = new RemoteViews(getPackageName(),
                            R.layout.update_version);
                    rv.setTextViewText(R.id.text_title, getResources().getString(R.string.download_failed));
                    myNotify.contentView = rv;
                    notificationManager.notify(notificationId, myNotify);
                    notificationManager.cancel(notificationId);
                    break;
            }
            return false;
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        myNotify = new Notification();
        myNotify.icon = R.mipmap.ic_launcher;
        myNotify.tickerText = getResources().getString(R.string.download_preview);
        myNotify.when = System.currentTimeMillis();

        myNotify.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除

        RemoteViews rv = new RemoteViews(getPackageName(),
                R.layout.update_version);
        rv.setProgressBar(R.id.progress, 100, 0, false);
        rv.setTextViewText(R.id.text_content, getResources().getString(R.string.download_start)); //这里就是使用自定义布局了 初始化的时候不设置Intent,点击的时候就不会有反应了,亏得我还找了好久  T-T

        myNotify.contentView = rv;
        notificationManager.notify(notificationId, myNotify);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        url = null;
        myNotify = null;
        notificationManager = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            url = intent.getStringExtra("url");
            init();
        }
        Expose nd;
        return super.onStartCommand(intent, flags, startId);
    }

    // 安装apk
    private void installAPK() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri=null;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(getApplicationContext(), "com.bh.uhome.bhuhome.FileProvider", new File(FileUtil.getPath() + apkName));
        } else {
            uri = Uri.fromFile(new File(FileUtil.getPath() + apkName));
        }
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        getApplicationContext().startActivity(intent);

    }


    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient(); //使用okhttp下载文件
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        String s = e.getMessage();
                        Log.i("DownLoad APK Fail:",s);
                        mHandler.sendEmptyMessage(DOWN_FAILED);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful())
                            throw new IOException("Unexpected code " + response);
                        InputStream is = response.body().byteStream(); //成功的回调中拿到字节流
                        String path = FileUtil.getPath();



//                        Uri uri=null;
//                        if (Build.VERSION.SDK_INT >= 24) {
//                            uri = FileProvider.getUriForFile(getApplicationContext(), "com.bh.uhome.bhuhome.FileProvider", new File(FileUtil.getPath()));
//                        } else {
//                            uri = Uri.fromFile(new File(FileUtil.getPath()));
//                        }
//
//                        grantUriPermission("com.bh.uhome.bhuhome",uri , Intent.FLAG_GRANT_READ_URI_PERMISSION
//                                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                        long fileLength = response.body().contentLength(); //获取文件长度
                        FileUtil.saveFile(is, path.toString(), apkName, mHandler, fileLength); //保存下载的apk文件
                    }

                });


            }
        }).start();
    }
}
