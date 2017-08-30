package com.bh.uhome.bhuhome.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;


/**
 * Created by huxiaotian  on 2015/11/2.
 */
public class TitleBarView extends LinearLayout {
    public LinearLayout titleHeaderLayout;
    public TextView titleHeaderTitleTv;//标题
    public ImageView titleHeaderLeftIv;//左侧icon
    public TextView titleHeaderLeft2Iv;//左侧文字
    public ImageView titleHeaderLeftRedIv;//左侧 小红点
    public TextView titleHeaderLeftTv;//左侧 红色 文字描述
    public FrameLayout titleHeaderRight1Layout;//最右侧layout
    public ImageView titleHeaderRight1Iv;//最右侧icon
    public ImageView titleHeaderRight1RedIv;//最右侧icon 红色小圆点
    public TextView titleHeaderRight1Tv;
    public FrameLayout titleHeaderRight2Layout;//右边的layout
    public ImageView titleHeaderRight2Iv;//右侧靠近中间的icon
    public TextView titleHeaderRight2Tv; //右侧靠近中间 红点 购物车数量
    public ImageView titleHeaderRight3Iv;//离title文字最近的 图片
    public LinearLayout ll_left;//左侧包裹icon和文字的布局
//    public BadgeView badgeLeft;//左侧红点
//    public BadgeView badgeRight;//右侧红点

    private Activity activity; //当前的activity
    public View titleHeaderUnderLine;

    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_titlebar, this, true);
        initData(view);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    private void initData(View view) {
        assignViews(view);
    }

    private void assignViews(View view) {
        titleHeaderLayout = (LinearLayout) view.findViewById(R.id.title_header_layout);
        titleHeaderTitleTv = (TextView) view.findViewById(R.id.title_header_title_tv);
        titleHeaderLeftIv = (ImageView) view.findViewById(R.id.title_header_left_iv);
        titleHeaderLeftRedIv = (ImageView) view.findViewById(R.id.title_header_left_red_iv);
        titleHeaderLeftTv = (TextView) view.findViewById(R.id.title_header_left_Tv);
        titleHeaderLeft2Iv = (TextView) view.findViewById(R.id.title_header_left2_iv);
        titleHeaderRight1Layout = (FrameLayout) view.findViewById(R.id.title_header_right1_layout);
        titleHeaderRight1Iv = (ImageView) view.findViewById(R.id.title_header_right1_iv);
        titleHeaderRight1RedIv = (ImageView) view.findViewById(R.id.title_header_right1_red_round_iv);
        titleHeaderRight1Tv = (TextView) view.findViewById(R.id.title_header_right1_tv);
        titleHeaderRight2Layout = (FrameLayout) view.findViewById(R.id.titile_header_right_layout);
        titleHeaderRight2Iv = (ImageView) view.findViewById(R.id.title_header_right2_iv);
        titleHeaderRight2Tv = (TextView) view.findViewById(R.id.title_header_right2_tv);
        titleHeaderRight3Iv = (ImageView) view.findViewById(R.id.title_header_right3_iv);
        titleHeaderUnderLine = view.findViewById(R.id.title_header_underline);

        titleHeaderRight2Tv.addTextChangedListener(new MsgTextWatcher(titleHeaderRight2Tv));
        titleHeaderLeftTv.addTextChangedListener(new MsgTextWatcher(titleHeaderLeftTv));


        ll_left = (LinearLayout) view.findViewById(R.id.ll_left);

        titleHeaderRight2Tv.addTextChangedListener(new MsgTextWatcher(titleHeaderRight2Tv));
        titleHeaderLeftTv.addTextChangedListener(new MsgTextWatcher(titleHeaderLeftTv));

//        badgeLeft = new BadgeView(UIUtils.getContext(), titleHeaderLeftIv);
//        badgeLeft.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//        badgeLeft.setTextSize(7);
//
//        badgeRight = new BadgeView(UIUtils.getContext(), titleHeaderRight1Iv);
//        badgeRight.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//        badgeRight.setTextSize(7);

        titleHeaderLeftIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != activity) {
                    activity.finish();
                }
            }
        });
    }
}
