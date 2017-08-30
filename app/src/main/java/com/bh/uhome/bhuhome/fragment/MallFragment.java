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
    }

    private void initData() {
        title_header_title_tv.setText("商城");
        title_header_right1_iv.setVisibility(View.VISIBLE);
        title_header_right1_iv.setBackgroundResource(R.mipmap.shopping_cart);

        mall_viewpager_banner.setSource(getBannersData())
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

        firstSetGoods(null);

        setTabData();
    }

    private ArrayList<BannerInfoBean> getBannersData() {
        banners = new ArrayList<>();
        BannerInfoBean bean = new BannerInfoBean();
        bean.setId("1");
        bean.setImgId("http://pic.58pic.com/58pic/13/60/90/58Y58PICdIc_1024.jpg");
        banners.add(bean);
        BannerInfoBean bean1 = new BannerInfoBean();
        bean1.setId("1");
        bean1.setImgId("http://imgsrc.baidu.com/imgad/pic/item/267f9e2f07082838b5168c32b299a9014c08f1f9.jpg");
        banners.add(bean1);
        BannerInfoBean bean2 = new BannerInfoBean();
        bean2.setId("1");
        bean2.setImgId("http://pic.58pic.com/58pic/13/56/03/48858PICJU6_1024.jpg");
        banners.add(bean2);

        return banners;
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
        mMallAdapter.setDatas(getgoodsList());

        if (goodsList != null) {
            pageCur++;
        }

        mMallAdapter.setHeaderView(headView);
        tempGoodList = getgoodsList();

    }

    private ArrayList<MallIndexInfo.DataBean.GoodsListBean> getgoodsList(){
        ArrayList<MallIndexInfo.DataBean.GoodsListBean> goodsList = new ArrayList<>();
        MallIndexInfo.DataBean.GoodsListBean bean1 = new MallIndexInfo.DataBean.GoodsListBean();
        bean1.setName("志高空调");
        bean1.setPrice("20");
        bean1.setSource("品质优越，质量上乘，节能省电……");
        bean1.setLink("http://www.baidu.com");
        bean1.setMainPic("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1504009716&di=b32c9a69f2f245f7f4d58746ce78777f&src=http://imgsrc.baidu.com/imgad/pic/item/9c16fdfaaf51f3de903def489eeef01f3a29790a.jpg");
        goodsList.add(bean1);

        MallIndexInfo.DataBean.GoodsListBean bean2 = new MallIndexInfo.DataBean.GoodsListBean();
        bean2.setName("夏普空调");
        bean2.setPrice("520");
        bean2.setSource("品质优越，质量上乘，节能省电……");
        bean2.setLink("http://www.baidu.com");
        bean2.setMainPic("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1504009716&di=b32c9a69f2f245f7f4d58746ce78777f&src=http://imgsrc.baidu.com/imgad/pic/item/9c16fdfaaf51f3de903def489eeef01f3a29790a.jpg");
        goodsList.add(bean2);

        MallIndexInfo.DataBean.GoodsListBean bean3 = new MallIndexInfo.DataBean.GoodsListBean();
        bean3.setName("中央空调");
        bean3.setPrice("230");
        bean3.setSource("品质优越，质量上乘，节能省电……");
        bean3.setLink("http://www.baidu.com");
        bean3.setMainPic("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1504009716&di=b32c9a69f2f245f7f4d58746ce78777f&src=http://imgsrc.baidu.com/imgad/pic/item/9c16fdfaaf51f3de903def489eeef01f3a29790a.jpg");
        goodsList.add(bean3);

        MallIndexInfo.DataBean.GoodsListBean bean4 = new MallIndexInfo.DataBean.GoodsListBean();
        bean4.setName("格力空调");
        bean4.setPrice("340");
        bean4.setSource("品质优越，质量上乘，节能省电……");
        bean4.setLink("http://www.baidu.com");
        bean4.setMainPic("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1504009716&di=b32c9a69f2f245f7f4d58746ce78777f&src=http://imgsrc.baidu.com/imgad/pic/item/9c16fdfaaf51f3de903def489eeef01f3a29790a.jpg");
        goodsList.add(bean4);

        return goodsList;
    }

    private void setTabData(){

        ArrayList<MallIndexInfo.DataBean.RecommendTagsBean> tagsBeans = new ArrayList<>();
        MallIndexInfo.DataBean.RecommendTagsBean  bean1 = new MallIndexInfo.DataBean.RecommendTagsBean();
        bean1.setName("推荐");
        bean1.setLink("http://www.baidu.com");
        tagsBeans.add(bean1);

        MallIndexInfo.DataBean.RecommendTagsBean  bean2 = new MallIndexInfo.DataBean.RecommendTagsBean();
        bean2.setName("空调");
        bean2.setLink("http://www.baidu.com");
        tagsBeans.add(bean2);

        MallIndexInfo.DataBean.RecommendTagsBean  bean3 = new MallIndexInfo.DataBean.RecommendTagsBean();
        bean3.setName("吸层器");
        bean3.setLink("http://www.baidu.com");
        tagsBeans.add(bean3);

        MallIndexInfo.DataBean.RecommendTagsBean  bean4 = new MallIndexInfo.DataBean.RecommendTagsBean();
        bean4.setName("望远镜");
        bean4.setLink("http://www.baidu.com");
        tagsBeans.add(bean4);

        MallIndexInfo.DataBean.RecommendTagsBean  bean5 = new MallIndexInfo.DataBean.RecommendTagsBean();
        bean5.setName("电饭锅");
        bean5.setLink("http://www.baidu.com");
        tagsBeans.add(bean5);

        MallIndexInfo.DataBean.RecommendTagsBean  bean6 = new MallIndexInfo.DataBean.RecommendTagsBean();
        bean6.setName("热水器");
        bean6.setLink("http://www.baidu.com");
        tagsBeans.add(bean6);

        MallIndexInfo.DataBean.RecommendTagsBean  bean7 = new MallIndexInfo.DataBean.RecommendTagsBean();
        bean7.setName("蒸锅");
        bean7.setLink("http://www.baidu.com");
        tagsBeans.add(bean7);




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
