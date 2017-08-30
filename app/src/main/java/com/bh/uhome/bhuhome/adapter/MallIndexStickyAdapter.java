package com.bh.uhome.bhuhome.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.entity.MallIndexInfo;
import com.bh.uhome.bhuhome.util.MallUtils;
import com.bh.uhome.bhuhome.util.UIUtils;
import com.bumptech.glide.Glide;


/**
 * Created by derek on 16/5/17.
 */
public class MallIndexStickyAdapter extends BaseRecyclerAdapter<MallIndexInfo.DataBean.GoodsListBean> {
    private int with;
    private Activity mContext;
    private OnRecyclerViewItemClickListener listener = null;

    public MallIndexStickyAdapter(Activity mContext, OnRecyclerViewItemClickListener listener) {
        this.mContext = mContext;
        int screenWith = UIUtils.getwidthHeight()[0];
        with = (screenWith - (UIUtils.dip2px(10)) * 3) / 2;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_common_mall_list, parent, false);
        return new MallHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, final MallIndexInfo.DataBean.GoodsListBean data) {

        if (viewHolder instanceof MallHolder) {
            MallHolder mallHolder = (MallHolder) viewHolder;
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(with, with);
//                holder.ivGood.setLayoutParams(params);
//            GlobleLoadImage.loadImage(UrlUtil.getImageUrl(data.getMainPic()),
//                    R.mipmap.load_default_mid_square,
//                    R.mipmap.load_default_mid_square,
//                    mallHolder.ivGood,
//              mContext);
            Glide.with(mContext).load(data.getMainPic()).into(mallHolder.ivGood);
            mallHolder.tvGoodName.setText(data.getName());
            mallHolder.tvGoodPriceNow.setText(MallUtils.getPrice(data.getPrice()));
            mallHolder.txtContent.setText(data.getSource());

            //市场价  此处RealPrice表示市场价
            String  marketPrice = data.getRealPrice();



            mallHolder.bind(getmDatas().get(RealPosition), listener, RealPosition);

//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//            if (RealPosition % 2 == 1) {
//                params.setMargins(20, 20, 20, 0);
//            } else {
//                params.setMargins(20, 20, 0, 0);
//            }
//            mallHolder.rayGoodsItem.setLayoutParams(params);
        }

    }

    //click事件接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(MallIndexInfo.DataBean.GoodsListBean itemBean, int position);
    }

    class MallHolder extends RecyclerView.ViewHolder {
        //商品列表属性
        public ImageView ivGood;
        public TextView tvGoodName;
        public TextView tvGoodPriceNow;
        public TextView txtContent;


        public MallHolder(View itemView) {
            super(itemView);
            ivGood = (ImageView) itemView.findViewById(R.id.imgGoods);
            tvGoodName = (TextView) itemView.findViewById(R.id.tv_good_name);
            tvGoodPriceNow = (TextView) itemView.findViewById(R.id.txtGoodsPrice);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);

        }

        public void bind(final MallIndexInfo.DataBean.GoodsListBean itemBean, final OnRecyclerViewItemClickListener listener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemBean, position);
                }
            });
        }
    }

}
