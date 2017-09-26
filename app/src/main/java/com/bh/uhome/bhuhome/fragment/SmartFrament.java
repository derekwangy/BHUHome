package com.bh.uhome.bhuhome.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.adapter.GalleryChildMenuAdapter;
import com.bh.uhome.bhuhome.adapter.GalleryGoodsAdapter;
import com.bh.uhome.bhuhome.banner.MallIndexBanner;
import com.bh.uhome.bhuhome.db.mockdata.MallFragmentData;
import com.bh.uhome.bhuhome.db.mockdata.SmartFragmentData;
import com.bh.uhome.bhuhome.entity.HomeMenuInfo;
import com.bh.uhome.bhuhome.entity.VersionInfo;
import com.bh.uhome.bhuhome.recycleviewmanager.FullyLinearLayoutManager;
import com.bh.uhome.bhuhome.service.UpdateVersionService;
import com.bh.uhome.bhuhome.util.CommonUtil;
import com.bh.uhome.bhuhome.util.UIUtils;
import com.bh.uhome.bhuhome.util.UpdateVersionUtil;
import com.bh.uhome.lib.base.base.BaseFragment;
import com.bh.uhome.lib.base.toast.ToastUtil;


/**
 * @author 凌霄
 * @date 2017/8/25.
 * @time 14:48.
 * @description Describe
 */
public class SmartFrament extends BaseFragment implements View.OnClickListener{
    private TextView title_header_title_tv = null;
    private View parentView = null;
    private ImageView title_header_right1_iv = null;
    private RecyclerView homeMenu,childHomeMenu;
    private GalleryGoodsAdapter homeMenuAdapter = null;
    private GalleryChildMenuAdapter childHomeMenuAdapter = null;
    private MallIndexBanner mall_viewpager_banner = null;
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
    }

    private void initData() {
        title_header_title_tv.setText("我的家");
        title_header_right1_iv.setVisibility(View.VISIBLE);

        setHomeMenuData();
        setChildHomeMenuData();
        setHomeAdBannerData();
        SmartFragmentData.getData();

        checkVersion();

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
}
