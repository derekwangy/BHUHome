package com.bh.uhome.bhuhome.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.entity.BannerInfoBean;
import com.bumptech.glide.Glide;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;


/**
 * 商城首页banner
 */
public class SmartHomeBanner extends BaseIndicatorBanner<BannerInfoBean, SmartHomeBanner> {
    private ColorDrawable colorDrawable;

    public SmartHomeBanner(Context context) {
        this(context, null, 0);
    }

    public SmartHomeBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartHomeBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        final BannerInfoBean item = mDatas.get(position);
//        tv.setText(item.title);
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_image, null);
        ImageView iv = inflate.findViewById(R.id.iv);

        final BannerInfoBean item = mDatas.get(position);
        int itemWidth = mDisplayMetrics.widthPixels;
        int itemHeight = (int) (itemWidth * 400 * 1.0f / 750);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));

        String imgId = item.getImgId();

        if (!TextUtils.isEmpty(imgId)) {
            Glide.with(mContext)
                    .load(imgId)
                    .into(iv);
        } else {
            iv.setImageResource(R.mipmap.load_default_banner);
        }

        return inflate;
    }
}
