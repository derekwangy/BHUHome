package com.bh.uhome.bhuhome.fragment;

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
import com.bh.uhome.bhuhome.recycleviewmanager.FullyLinearLayoutManager;
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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_header_right1_iv:
                ToastUtil.showShort(getActivity(),"add");
                break;
        }
    }
}
