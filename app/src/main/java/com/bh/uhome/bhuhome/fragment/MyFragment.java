package com.bh.uhome.bhuhome.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.activity.mine.AboutUsActivity;
import com.bh.uhome.lib.base.base.BaseFragment;


/**
 * @author 凌霄
 * @date 2017/8/25.
 * @time 14:57.
 * @description Describe
 */
public class MyFragment extends BaseFragment implements View.OnClickListener{
    private TextView title_header_title_tv = null;
    private View parentView = null;
    private RelativeLayout rLayAboutUs,rLayOnlineService,rLayHelpCenter,rLayDeviceManage,rLayMsgCenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_mine, container, false);
        initViews();
        initData();
        return parentView;
    }

    private void initViews() {
        title_header_title_tv = parentView.findViewById(R.id.title_header_title_tv);
        rLayAboutUs = parentView.findViewById(R.id.rLay_about_us);
        rLayOnlineService = parentView.findViewById(R.id.rLay_online_service);
        rLayHelpCenter = parentView.findViewById(R.id.rLay_help_center);
        rLayDeviceManage = parentView.findViewById(R.id.rLay_device_manage);
        rLayMsgCenter = parentView.findViewById(R.id.rLay_msg_center);

        rLayAboutUs.setOnClickListener(this);
        rLayOnlineService.setOnClickListener(this);
        rLayHelpCenter.setOnClickListener(this);
        rLayDeviceManage.setOnClickListener(this);
        rLayMsgCenter.setOnClickListener(this);
    }

    private void initData() {
        title_header_title_tv.setText("我");
    }

    @Override
    public void onClick(View view) {
        Intent mIntent = null;
        switch (view.getId()){
            case R.id.rLay_about_us:
                mIntent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rLay_online_service:
                openQQChat();
                break;
            case R.id.rLay_help_center:
                break;
            case R.id.rLay_device_manage:
                break;
            case R.id.rLay_msg_center:
                break;

        }
    }

    private void openQQChat() {
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=984098418";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
