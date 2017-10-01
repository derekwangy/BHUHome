package com.bh.uhome.bhuhome.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
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
import com.bh.uhome.bhuhome.entity.HomeMenuInfo;
import com.bh.uhome.bhuhome.entity.VersionInfo;
import com.bh.uhome.bhuhome.recycleviewmanager.FullyLinearLayoutManager;
import com.bh.uhome.bhuhome.service.UpdateVersionService;
import com.bh.uhome.bhuhome.util.ActivityUtils;
import com.bh.uhome.bhuhome.util.CommonUtil;
import com.bh.uhome.bhuhome.util.EZUtils;
import com.bh.uhome.bhuhome.util.UIUtils;
import com.bh.uhome.bhuhome.util.UpdateVersionUtil;
import com.bh.uhome.lib.base.base.BaseFragment;
import com.bh.uhome.lib.base.toast.ToastUtil;
import com.ezvizuikit.open.EZUIError;
import com.ezvizuikit.open.EZUIKit;
import com.ezvizuikit.open.EZUIPlayer;
import com.videogo.constant.IntentConsts;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LogUtil;
import com.videogo.util.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author 凌霄
 * @date 2017/8/25.
 * @time 14:48.
 * @description Describe
 */
public class SmartFrament extends BaseFragment implements View.OnClickListener{
    private static final String TAG = "SmartFrament-Cammar:";
    private TextView title_header_title_tv = null;
    private View parentView = null;
    private ImageView title_header_right1_iv = null;
    private RecyclerView homeMenu,childHomeMenu;
    private GalleryGoodsAdapter homeMenuAdapter = null;
    private GalleryChildMenuAdapter childHomeMenuAdapter = null;
    private MallIndexBanner mall_viewpager_banner = null;
    private EZUIPlayer mPlayer = null;  //视频播放
    private SurfaceView realplay_sv = null;  //播放
    private EZCameraInfo cameraInfo = null;
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
        mPlayer = (EZUIPlayer) parentView.findViewById(R.id.player_ui);
        realplay_sv = (SurfaceView)parentView.findViewById(R.id.realplay_sv);
    }

    private void initData() {
        title_header_title_tv.setText("我的家");
        title_header_right1_iv.setVisibility(View.VISIBLE);

        setHomeMenuData();
        setChildHomeMenuData();
        setHomeAdBannerData();
        SmartFragmentData.getData();

        checkVersion();

//        initplayer();
        ActivityUtils.goToLoginAgain(getActivity());

        startPlayer();
    }

    private void startPlayer() {
        new GetCamersInfoListTask().execute();
    }

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
                Intent toIntent = new Intent(getActivity(), EZRealPlayActivity.class);
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

    private void initplayer() {
        //设置播放回调callback
        mPlayer.setCallBack(new EZUIPlayer.EZUIPlayerCallBack() {
            @Override
            public void onPlaySuccess() {

            }

            @Override
            public void onPlayFail(EZUIError ezuiError) {

            }

            @Override
            public void onVideoSizeChange(int i, int i1) {

            }

            @Override
            public void onPrepared() {

            }

            @Override
            public void onPlayTime(Calendar calendar) {

            }

            @Override
            public void onPlayFinish() {

            }
        });
        //设置授权token
//        EZUIKit.setAccessToken(AppApplication.YS_TOKEN);
        AppApplication.getOpenSDK().setAccessToken(AppApplication.YS_TOKEN);
        //设置播放宽高
//        mPlayer.setSurfaceSize(int width, int height);
        String playUrl = "ezopen://open.ys7.com/771907733/1.rec";
        //设置播放参数
        mPlayer.setUrl(playUrl);
        //开始播放
        mPlayer.startPlay();
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

        childHomeMenuAdapter = new GalleryChildMenuAdapter(getActivity(), SmartFragmentData.getChildHomeMenuData(), new GalleryChildMenuAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(HomeMenuInfo itemBean, int position) {
                ToastUtil.showShort(getActivity(),itemBean.getName());
                Intent mIntent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(mIntent);
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
        //停止播放
        mPlayer.stopPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放资源
        mPlayer.releasePlayer();
    }
}
