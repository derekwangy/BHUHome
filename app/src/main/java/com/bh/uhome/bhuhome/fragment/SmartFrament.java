package com.bh.uhome.bhuhome.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.activity.main.cameralist.EZRealPlayActivity;
import com.bh.uhome.bhuhome.activity.mine.AboutUsActivity;
import com.bh.uhome.bhuhome.adapter.GalleryChildMenuAdapter;
import com.bh.uhome.bhuhome.adapter.GalleryGoodsAdapter;
import com.bh.uhome.bhuhome.app.AppApplication;
import com.bh.uhome.bhuhome.banner.MallIndexBanner;
import com.bh.uhome.bhuhome.db.mockdata.MallFragmentData;
import com.bh.uhome.bhuhome.db.mockdata.SmartFragmentData;
import com.bh.uhome.bhuhome.dialog.WaitDialog;
import com.bh.uhome.bhuhome.entity.DeviceControlInfo;
import com.bh.uhome.bhuhome.entity.HomeMenuInfo;
import com.bh.uhome.bhuhome.entity.RealPlaySquareInfo;
import com.bh.uhome.bhuhome.entity.VersionInfo;
import com.bh.uhome.bhuhome.recycleviewmanager.FullyLinearLayoutManager;
import com.bh.uhome.bhuhome.service.UpdateVersionService;
import com.bh.uhome.bhuhome.util.ActivityUtils;
import com.bh.uhome.bhuhome.util.CommonUtil;
import com.bh.uhome.bhuhome.util.DataManager;
import com.bh.uhome.bhuhome.util.EZUtils;
import com.bh.uhome.bhuhome.util.UIUtils;
import com.bh.uhome.bhuhome.util.UpdateVersionUtil;
import com.bh.uhome.bhuhome.widget.loading.LoadingTextView;
import com.bh.uhome.lib.base.base.BaseFragment;
import com.bh.uhome.lib.base.toast.ToastUtil;
import com.videogo.constant.IntentConsts;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LogUtil;
import com.videogo.util.Utils;

import java.util.List;
import java.util.Random;


/**
 * @author 凌霄
 * @date 2017/8/25.
 * @time 14:48.
 * @description Describe
 */
public class SmartFrament extends BaseFragment implements View.OnClickListener,SurfaceHolder.Callback{
    private static final String TAG = "SmartFrament-Cammar:";
    private TextView title_header_title_tv = null;
    private View parentView = null;
    private ImageView title_header_right1_iv = null;
    private RecyclerView homeMenu,childHomeMenu;
    private GalleryGoodsAdapter homeMenuAdapter = null;
    private GalleryChildMenuAdapter childHomeMenuAdapter = null;
    private MallIndexBanner mall_viewpager_banner = null;
    //视频设置
    /**
     * 动画时间
     */
    private static final int ANIMATION_DURING_TIME = 500;

    public static final float BIG_SCREEN_RATIO = 1.60f;

    // UI消息
    public static final int MSG_PLAY_UI_UPDATE = 200;

    public static final int MSG_AUTO_START_PLAY = 202;

    public static final int MSG_CLOSE_PTZ_PROMPT = 203;

    public static final int MSG_HIDE_PTZ_DIRECTION = 204;

    public static final int MSG_HIDE_PAGE_ANIM = 205;

    public static final int MSG_PLAY_UI_REFRESH = 206;

    public static final int MSG_PREVIEW_START_PLAY = 207;

    public static final int MSG_SET_VEDIOMODE_SUCCESS = 105;

    /**
     * 设置视频质量成功
     */
    public static final int MSG_SET_VEDIOMODE_FAIL = 106;

    // 视频广场URL
    private String mRtspUrl = null;
    // 视频广场播放信息
    private RealPlaySquareInfo mRealPlaySquareInfo = null;
    private SurfaceView mRealPlaySv = null; //播放
    private SurfaceHolder mRealPlaySh = null;
    private EZCameraInfo cameraInfo = null;
    private EZConstants.EZVideoLevel mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_HD;
    private EZDeviceInfo mDeviceInfo = null;
    private EZCameraInfo mCameraInfo = null;

    /**
     * 演示点预览控制对象
     */
    private EZPlayer mEZPlayer = null;
    /**
     * 标识是否正在播放
     */
    private int mStatus = RealPlayStatus.STATUS_INIT;
    private boolean mIsOnStop = false;
    private LoadingTextView mRealPlayPlayLoading;
    private WaitDialog mWaitDialog = null;

    private Handler mHandler = new Handler(){

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_smart, container, false);
        initViews();
        initData();
        return parentView;
    }

    private void initViews() {
        title_header_title_tv = parentView.findViewById(R.id.title_header_title_tv);
        title_header_right1_iv = parentView.findViewById(R.id.title_header_right1_iv);
        homeMenu = parentView.findViewById(R.id.homeMenu);
        childHomeMenu = parentView.findViewById(R.id.childHomeMenu);
        mall_viewpager_banner = parentView.findViewById(R.id.mall_viewpager_banner);
        //获取EZUIPlayer实例
        mRealPlaySv = (SurfaceView)parentView.findViewById(R.id.realplay_sv);
    }

    private void initData() {
        title_header_title_tv.setText("我的家");
        title_header_right1_iv.setVisibility(View.VISIBLE);

        setHomeMenuData();
        setChildHomeMenuData();
        setHomeAdBannerData();
        SmartFragmentData.getData();

        checkVersion();

        ActivityUtils.goToLoginAgain(getActivity());

        startPlayer();
    }

    private void startPlayer() {
        new GetCamersInfoListTask().execute();
    }

    //**************surfaceview*******************//
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    //**************surfaceview*******************//

    /**
     * 获取事件消息任务
     */
    class GetCamersInfoListTask extends AsyncTask<Void, Void, List<EZDeviceInfo>> {
        private int mErrorCode = 0;

        public GetCamersInfoListTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mListView.setFooterRefreshEnabled(true);
            //加载前处理显示问题
        }

        @Override
        protected List<EZDeviceInfo> doInBackground(Void... params) {

            if (!ConnectionDetector.isNetworkAvailable(getActivity())) {
                mErrorCode = ErrorCode.ERROR_WEB_NET_EXCEPTION;
                return null;
            }
            try {
                List<EZDeviceInfo> result = null;
                result = AppApplication.getOpenSDK().getDeviceList(0, 20);
                return result;

            } catch (BaseException e) {
                ErrorInfo errorInfo = (ErrorInfo) e.getObject();
                mErrorCode = errorInfo.errorCode;
                LogUtil.d(TAG, errorInfo.toString());

                return null;
            }
        }

        @Override
        protected void onPostExecute(List<EZDeviceInfo> result) {
            super.onPostExecute(result);
            if (result != null) {
                cameraInfo = EZUtils.getCameraInfoFromDevice(result.get(0), 0);
                Intent toIntent = new Intent(getActivity(), MainFrament.class);
                toIntent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                toIntent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, result.get(0));
                startActivity(toIntent);
            }

            if (mErrorCode != 0) {
                onError(mErrorCode);
            }
        }

        protected void onError(int errorCode) {
            switch (errorCode) {
                case ErrorCode.ERROR_WEB_SESSION_ERROR:
                case ErrorCode.ERROR_WEB_SESSION_EXPIRE:
                    ActivityUtils.handleSessionException(getActivity());
                    break;
                default:

                    break;
            }
        }
    }

    private void setHomeAdBannerData() {
        mall_viewpager_banner.setSource(MallFragmentData.getBannersData())
                .setIndicatorSelectorRes(R.drawable.dot_unfoucs, R.drawable.dot_foucs)
                .startScroll();

        mall_viewpager_banner.setOnItemClickL(new MallIndexBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    private void setHomeMenuData(){
        //设置布局管理器
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeMenu.setLayoutManager(linearLayoutManager);

        homeMenuAdapter = new GalleryGoodsAdapter(getActivity(), SmartFragmentData.getHomeMenuData(), new GalleryGoodsAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(HomeMenuInfo itemBean, int position) {
                ToastUtil.showShort(getActivity(),itemBean.getName());
            }

        });
        homeMenu.setAdapter(homeMenuAdapter);
    }

    private void setChildHomeMenuData(){
        //设置布局管理器
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        childHomeMenu.setLayoutManager(linearLayoutManager);
        List<DeviceControlInfo.DataBean.DevNamesBean>  devNamesBean = null;
        childHomeMenuAdapter = new GalleryChildMenuAdapter(getActivity(), devNamesBean, new GalleryChildMenuAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(DeviceControlInfo.DataBean.DevNamesBean itemBean, int position) {
                ToastUtil.showShort(getActivity(),itemBean.getLocation());
                ActivityUtils.goToLoginAgain(getActivity());
            }

        });
        childHomeMenu.setAdapter(childHomeMenuAdapter);
    }

    /**
     * 检查版本更新
     */
    public void checkVersion() {
        VersionInfo info = SmartFragmentData.getVersionData();

        try {
            int localVersionCode = CommonUtil.getAppVersionCode(getActivity());
            int dbVersionCode = info.getVersionCode();
            if (dbVersionCode > localVersionCode) {
                new UpdateVersionUtil(getActivity(), info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  try{
        String versionNameDB = "1.0";
        final String downUrl = "http://139.224.116.55:8080/webside/resources/apk/yijia.apk";
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        // 设置触摸对话框以外的地方取消对话框  true:点击dialog之外和back键关闭  false:点击dialog之外不关闭  20160303 申鹏修改
        alertDialog.setCanceledOnTouchOutside(false);
//          alertDialog.setCancelable(false);//设置触摸对话框以外的地方取消对话框  true:点击dialog之外和back键关闭  false:点击dialog之外和back键都不关闭  20160303 申鹏修改
        alertDialog.show();
        alertDialog.getWindow().setContentView(R.layout.dialog_update_version_new);

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
                        Intent serviceIntent = new Intent(getActivity(), UpdateVersionService.class);
                        serviceIntent.putExtra("url", downUrl);
                        getActivity().startService(serviceIntent);

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
//                        if (!updateType.equals(UPDATE_TYPE_TAG)) { //  非强制更新
//                            saveSharefVersionCode();
//                            alertDialog.dismiss();
//                        } else {
//                            ToastUtil.showLong(mActivity, mActivity.getResources().getString(R.string.mine_ensure_version));
//                        }
                    }

                });
    } catch (Exception e) {
        e.printStackTrace();
    }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_header_right1_iv:
                ToastUtil.showShort(getActivity(),"add");
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 开始播放
     */
    private void startRealPlay() {
        // 增加手机客户端操作信息记录
        LogUtil.debugLog(TAG, "startRealPlay");

        if (mStatus == RealPlayStatus.STATUS_START || mStatus == RealPlayStatus.STATUS_PLAY) {
            return;
        }

        // 检查网络是否可用
        if (!ConnectionDetector.isNetworkAvailable(getActivity())) {
            // 提示没有连接网络
//            setRealPlayFailUI(getString(R.string.realplay_play_fail_becauseof_network));
            return;
        }

        mStatus = RealPlayStatus.STATUS_START;
//        setRealPlayLoadingUI();

        if (mCameraInfo != null) {
            if (mEZPlayer == null) {
                mEZPlayer = AppApplication.getOpenSDK().createPlayer(mCameraInfo.getDeviceSerial(), mCameraInfo.getCameraNo());
            }

            if (mEZPlayer == null)
                return;
            if (mDeviceInfo == null) {
                return;
            }
            if (mDeviceInfo.getIsEncrypt() == 1) {
                mEZPlayer.setPlayVerifyCode(DataManager.getInstance().getDeviceSerialVerifyCode(mCameraInfo.getDeviceSerial()));
            }

            mEZPlayer.setHandler(mHandler);
            mEZPlayer.setSurfaceHold(mRealPlaySh);
            mEZPlayer.startRealPlay();
        } else if (mRtspUrl != null) {
            mEZPlayer = AppApplication.getOpenSDK().createPlayerWithUrl(mRtspUrl);
            //mStub.setCameraId(mCameraInfo.getCameraId());////****  mj
            if (mEZPlayer == null)
                return;
            mEZPlayer.setHandler(mHandler);
            mEZPlayer.setSurfaceHold(mRealPlaySh);

            mEZPlayer.startRealPlay();
        }
        updateLoadingProgress(0);
    }

    /**
     * 码流配置 清晰度 2-高清，1-标清，0-流畅
     *
     * @see
     * @since V2.0
     */
    private void setQualityMode(final EZConstants.EZVideoLevel mode) {
        // 检查网络是否可用
        if (!ConnectionDetector.isNetworkAvailable(getActivity())) {
            // 提示没有连接网络
            Utils.showToast(getActivity(), R.string.realplay_set_fail_network);
            return;
        }

        if (mEZPlayer != null) {
            mWaitDialog.setWaitText(this.getString(R.string.setting_video_level));
            mWaitDialog.show();

            Thread thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // need to modify by yudan at 08-11
                        AppApplication.getOpenSDK().setVideoLevel(mCameraInfo.getDeviceSerial(), mCameraInfo.getCameraNo(), mode.getVideoLevel());
                        mCurrentQulityMode = mode;
                        Message msg = Message.obtain();
                        msg.what = MSG_SET_VEDIOMODE_SUCCESS;
                        mHandler.sendMessage(msg);
                        LogUtil.i(TAG, "setQualityMode success");
                    } catch (BaseException e) {
                        mCurrentQulityMode = EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET;
                        e.printStackTrace();
                        Message msg = Message.obtain();
                        msg.what = MSG_SET_VEDIOMODE_FAIL;
                        mHandler.sendMessage(msg);
                        LogUtil.i(TAG, "setQualityMode fail");
                    }

                }
            }) {
            };
            thr.start();
        }
    }

    private void updateLoadingProgress(final int progress) {
        mRealPlayPlayLoading.setTag(Integer.valueOf(progress));
        mRealPlayPlayLoading.setText(progress + "%");
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (mRealPlayPlayLoading != null) {
                    Integer tag = (Integer) mRealPlayPlayLoading.getTag();
                    if (tag != null && tag.intValue() == progress) {
                        Random r = new Random();
                        mRealPlayPlayLoading.setText((progress + r.nextInt(20)) + "%");
                    }
                }
            }

        }, 500);
    }
}
