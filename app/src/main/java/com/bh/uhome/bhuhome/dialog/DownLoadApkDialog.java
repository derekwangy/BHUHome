package com.bh.uhome.bhuhome.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.util.PathUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownLoadApkDialog extends Dialog implements
        View.OnClickListener {

    private TextView txtProgressCount;

    private Window window = null;
    private CancleDownloadApkListener listener;
    private int position;
    private ProgressBar mProgressBar;
    private TextView cancle_download_btn = null;
    private String downloadUrl = "";

    private String versionCodeDB = ""; // 后台版本号
    private int versionCodeLocal = 0;
    private String load_flag = "";
    private String apkName = "linShiZiMi";
    private final static int THREAD_DOWNLOADAPK = 10002;
    private final static int THREAD_FILE_SIZE = 10003;
    private final static int THREAD_DOWNLOAD_SIZE = 10004;
    private Context mContext;

    // 进度条
    private int downloadFileSize = 0;// 下载文件大小
    private int fileSize = 0; // 文件大小

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case THREAD_DOWNLOADAPK:
                    installAPK();
                    break;
                case THREAD_FILE_SIZE:
//                    mProgressBar.setMax(fileSize);
                    break;
                case THREAD_DOWNLOAD_SIZE:
//                    mProgressBar.setProgress(downloadFileSize);
                    int result = downloadFileSize * 100 / fileSize;
//                    txtProgressCount.setText(result + "");
                    break;
                default:
                    break;
            }
        }

        ;
    };
    private final DownloadApkThread thread;

    public DownLoadApkDialog(Context context, CancleDownloadApkListener listener, String downloadUrl, String load_flag) {
        super(context);
        // TODO Auto-generated constructor stub
        this.listener = listener;
        this.downloadUrl = downloadUrl;
        this.mContext = context;
        this.load_flag = load_flag;
        thread = new DownloadApkThread(downloadUrl);//原来的文件是匿名内部类，这里稍作修改。 申鹏修改20160304
        thread.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download_apk);

        initViews();
    }

    private void initViews() {
        // TODO Auto-generated method stub
        txtProgressCount = (TextView) findViewById(R.id.txtProgressCount);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        cancle_download_btn = (TextView) findViewById(R.id.cancle_download_btn);

        cancle_download_btn.setOnClickListener(this);
    }

    public void showDialog(int x, int y) {
        windowDeploy(x, y);
        // 设置触摸对话框以外的地方取消对话框
//		setCanceledOnTouchOutside(false);//原生方法我认为应该暴露在外边，保证showDialog方法的功能单一性，以防止在调用setCanceledOnTouchOutside时被showDialog中的这个方法覆盖掉   20160303 申鹏修改
        show();
    }

    /**
     * 取消下载
     */
    public void cancleDownload() {
        dismiss();
    }

    // 设置窗口显示
    public void windowDeploy(int x, int y) {
        window = getWindow(); // 得到对话框
        window.setWindowAnimations(R.style.dialog_window_anim); // 设置窗口弹出动画
        window.setBackgroundDrawableResource(R.color.color_00000000);
        // //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        // 根据x，y坐标设置窗口需要显示的位置
        wl.x = x; // x小于0左移，大于0右移
        wl.y = y; // y小于0上移，大于0下移
        // wl.alpha = 0.6f; //设置透明度
        // wl.gravity = Gravity.BOTTOM; //设置重力
        window.setAttributes(wl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle_download_btn:
//                if (!load_flag.equals("1")) {//这里的更新可以由可后台来控制，此处点击“取消”不可更新，由于是强制更新这里  20160304 申鹏修改
//                    listener.cancleDownloadClick(position);
//                    dismiss();
//                } else {
//                    ToastUtils.show(mContext, "请继续等待下载更新！");
//                }
                listener.cancleDownloadClick(position);  //明廷和广峰商量，不管是否是强制更新，点击“取消”的时候要停止更新。20160304 申鹏修改
                thread.cancel();

                break;
//            case R.id.dialog_cancel_tv:
//                dismiss();
//                break;
        }

    }

    public interface DownloadApkListener {

        public void downloadClick(int position);

    }

    public interface CancleDownloadApkListener {

        public void cancleDownloadClick(int position);

    }


    private class DownloadApkThread extends Thread {//曾经类名为：downloadApkThread；现改为DownloadApkThread  20160304 申鹏修改
        private String mDownApkUrl;
        private boolean stop;

        public DownloadApkThread(String mDownLink) {
            this.mDownApkUrl = mDownLink;
        }

        public void cancel() {
            stop =true;

        }

        @Override
        public void run() {

            try {
                PathUtil.ApkDownPath = PathUtil.saveDirectory(mContext)
                        .getAbsolutePath() + "/";
                URL url = new URL(mDownApkUrl);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                fileSize = conn.getContentLength();// 根据响应获取文件大小
                if (fileSize <= 0)
                    throw new RuntimeException("无法获知文件大小 ");
                if (is == null)
                    throw new RuntimeException("stream is null");
                sendMsg(THREAD_FILE_SIZE);
                File file = new File(PathUtil.ApkDownPath);
                if (!file.exists()) {
                    file.mkdir();
                }

                File apkFile = new File(file, apkName + ".apk");
                FileOutputStream fos = new FileOutputStream(apkFile);
                byte buf[] = new byte[1024];

                int numread = 0;
                do {
                    numread = is.read(buf);
                    if (numread <= 0) {
                        break;
                    }
                    downloadFileSize += numread;
                    sendMsg(THREAD_DOWNLOAD_SIZE);
                    fos.write(buf, 0, numread);
                    if(stop){

                        Log.d("yuyue"," stop  thread");
                        return;
                    }
                } while (true);

                fos.close();
                is.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            mHandler.obtainMessage(THREAD_DOWNLOADAPK, "b").sendToTarget();
        }
    }


    private void sendMsg(int flag) {
        Message msg = new Message();
        msg.what = flag;
        mHandler.sendMessage(msg);
    }

    // 安装apk
    private void installAPK() {
        String fileName = PathUtil.ApkDownPath + apkName + ".apk";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(fileName)),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

}
