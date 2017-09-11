package com.bh.uhome.bhuhome.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.adapter.MallIndexStickyAdapter;
import com.bh.uhome.bhuhome.adapter.MallTabAdapter;
import com.bh.uhome.bhuhome.banner.MallIndexBanner;
import com.bh.uhome.bhuhome.db.mockdata.MallFragmentData;
import com.bh.uhome.bhuhome.entity.BannerInfoBean;
import com.bh.uhome.bhuhome.entity.MallIndexInfo;
import com.bh.uhome.bhuhome.oninterface.OnStickyLayout;
import com.bh.uhome.bhuhome.recycleviewmanager.FastScrollGridLayoutManager;
import com.bh.uhome.bhuhome.recycleviewmanager.MyLinearLayoutManager;
import com.bh.uhome.bhuhome.recycleviewmanager.pulltorefresh.PullToRefreshLayout;
import com.bh.uhome.bhuhome.recycleviewmanager.pulltorefresh.PullableRecyclerView;
import com.bh.uhome.lib.base.base.BaseFragment;

import java.util.ArrayList;


/**
 * @author 凌霄
 * @date 2017/8/25.
 * @time 14:50.
 * @description Describe
 */
public class MallFragment extends BaseFragment implements OnStickyLayout {
    private TextView title_header_title_tv = null;
    private ImageView title_header_right1_iv = null;

    private MallIndexBanner mall_viewpager_banner = null;
    private ArrayList<BannerInfoBean> banners;
    private FrameLayout framViewPager;
    private View parentView,headView;
    private PullToRefreshLayout ptRefresh;

    private PullableRecyclerView mRecyclerView;
    private View mStickyView;         //悬浮view
    private int mScrollY;    //recycleview 滚动距离
    private MallIndexStickyAdapter mMallAdapter;
    private View placeHolderView = null;
    private RecyclerView tabRecyclerView = null;

    private int pageCur = 1;     //页面索引
    private MallIndexInfo mallIndexInfo;
    private ArrayList<MallIndexInfo.DataBean.GoodsListBean> goodsList = null;   //商品列表数据
    private ArrayList<MallIndexInfo.DataBean.GoodsListBean> tempGoodList = null;
    private MallTabAdapter tabAdapter = null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_mall, container, false);
        headView = getActivity().getLayoutInflater().inflate(R.layout.sticky_view_holder, null);
        initView(parentView);
        initData();
        return parentView;
    }

    private void initView(View parentView) {
        ptRefresh = (PullToRefreshLayout) parentView.findViewById(R.id.refresh_view);
        mRecyclerView = (PullableRecyclerView) parentView.findViewById(R.id.recycler);
        mStickyView = parentView.findViewById(R.id.sticky_view);
        tabRecyclerView = (RecyclerView) parentView.findViewById(R.id.tabRecyclerView);
        title_header_title_tv = parentView.findViewById(R.id.title_header_title_tv);
        title_header_right1_iv = parentView.findViewById(R.id.title_header_right1_iv);

        //header
        mall_viewpager_banner = headView.findViewById(R.id.mall_viewpager_banner);
        framViewPager = (FrameLayout) headView.findViewById(R.id.framViewPager);
        placeHolderView = headView.findViewById(R.id.place_holder);
        placeHolderView.setVisibility(View.GONE);
    }

    private void initData() {
        title_header_title_tv.setText("商城");
        title_header_right1_iv.setVisibility(View.VISIBLE);
        title_header_right1_iv.setBackgroundResource(R.mipmap.shopping_cart);

        mall_viewpager_banner.setSource(MallFragmentData.getBannersData())
                .setIndicatorSelectorRes(R.drawable.dot_unfoucs, R.drawable.dot_foucs)
                .startScroll();

        mall_viewpager_banner.setOnItemClickL(new MallIndexBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {

            }
        });

        MyLinearLayoutManager myLinearLayoutManager = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(myLinearLayoutManager);

        mMallAdapter = new MallIndexStickyAdapter(getActivity(), new MallIndexStickyAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(MallIndexInfo.DataBean.GoodsListBean itemBean, int position) {

            }
        });
        mMallAdapter.setHeaderView(headView);

        placeHolderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                onStickyLayout(placeHolderView.getTop(), placeHolderView.getHeight());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    placeHolderView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    placeHolderView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

            }
        });

        ptRefresh.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pageCur = 1;
//                requestMallData();
                ptRefresh.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//                requestrecommendTags();
                ptRefresh.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });

        firstSetGoods(null);

        setTabData();
    }




    private void firstSetGoods(ArrayList<MallIndexInfo.DataBean.GoodsListBean> goodsList) {

        MyLinearLayoutManager myLinearLayoutManager = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
//        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(myLinearLayoutManager);


        mMallAdapter = new MallIndexStickyAdapter(getActivity(), new MallIndexStickyAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(MallIndexInfo.DataBean.GoodsListBean itemBean, int position) {
                String link = itemBean.getLink();
//                CommonClickUtil.commonClick(link, getActivity());
            }
        });
        mRecyclerView.setAdapter(mMallAdapter);
        mMallAdapter.setDatas(MallFragmentData.getgoodsList());

        if (goodsList != null) {
            pageCur++;
        }

        mMallAdapter.setHeaderView(headView);
        tempGoodList = MallFragmentData.getgoodsList();

    }

    private  void setTabData() {
        ArrayList<MallIndexInfo.DataBean.RecommendTagsBean> tagsBeans = MallFragmentData.getTabData();
        if (tagsBeans != null) {
        MyLinearLayoutManager myLinearLayoutManager = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        tabRecyclerView.setLayoutManager(myLinearLayoutManager);
        tabAdapter = new MallTabAdapter(getActivity(), tagsBeans, new MallTabAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(MallIndexInfo.DataBean.RecommendTagsBean itemBean, int position) {
                tabAdapter.setClickPosition(position);
                tabAdapter.notifyDataSetChanged();

//                recommendUrl = itemBean.getLink();

                //解决吸顶bug
                mScrollY = framViewPager.getTop();

//                if (!TextUtils.isEmpty(recommendUrl)) {
//                    pageCur = 1;
//                    requestrecommendTags();
//                }

            }
        });
        tabRecyclerView.setAdapter(tabAdapter);

        mStickyView.setVisibility(View.VISIBLE);
        placeHolderView.setVisibility(View.INVISIBLE);
    } else {
        mStickyView.setVisibility(View.GONE);
        placeHolderView.setVisibility(View.GONE);
    }

        mStickyView.setVisibility(View.GONE);
        placeHolderView.setVisibility(View.GONE);

}



    @Override
    public void onStickyLayout(final int top, int stickyViewHeight) {
        ViewCompat.setTranslationY(mStickyView, top);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mScrollY += dy;

                int translationY = top - mScrollY;

                if (translationY < 0) translationY = 0;

                ViewCompat.setTranslationY(mStickyView, translationY);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    int firstVisiblePosition = mRecyclerView.getFirstVisiblePosition();
                    int lastVisiblePosition = mRecyclerView.getLastVisiblePosition();



                }
            }

        });
    }



}
