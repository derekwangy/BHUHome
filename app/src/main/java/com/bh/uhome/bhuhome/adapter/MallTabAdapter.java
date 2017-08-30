package com.bh.uhome.bhuhome.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.entity.MallIndexInfo;

import java.util.ArrayList;

/**
 * Created by derek on 16/5/18.
 */
public class MallTabAdapter extends RecyclerView.Adapter<MallTabAdapter.MallTabHolder> {
    private Activity mContext;
    private ArrayList<MallIndexInfo.DataBean.RecommendTagsBean> tagsBeans;
    private int clickPosition = 0;    //当前点击哪一个

    //click事件接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(MallIndexInfo.DataBean.RecommendTagsBean itemBean, int position);
    }

    private OnRecyclerViewItemClickListener listener = null;

    public MallTabAdapter(Activity mContext, ArrayList<MallIndexInfo.DataBean.RecommendTagsBean> tagsBeans, OnRecyclerViewItemClickListener listener) {
        this.mContext = mContext;
        this.tagsBeans = tagsBeans;
        this.listener = listener;

    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
    }

    @Override
    public MallTabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_mall_tab, parent, false);
        return new MallTabHolder(view);
    }


    @Override
    public void onBindViewHolder(MallTabHolder holder, int position) {
        holder.txtItemTab.setText(tagsBeans.get(position).getName());
        if (clickPosition == position) {
            holder.txtItemTab.setTextColor(mContext.getResources().getColor(R.color.color_f42e3a));
//            holder.viewLine.setBackgroundColor(mContext.getResources().getColor(R.color.textcolor_ff7200));
//            holder.txtItemTab.setTextColor(mContext.getResources().getColor(R.color.textcolor_ff7200));
            holder.viewLine.setVisibility(View.VISIBLE);
        } else {
//            holder.viewLine.setBackgroundColor(mContext.getResources().getColor(R.color.color_e8e8e8));
            holder.viewLine.setVisibility(View.INVISIBLE);
            holder.txtItemTab.setTextColor(mContext.getResources().getColor(R.color.color_666666));
//            holder.txtItemTab.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        holder.bind(tagsBeans.get(position), listener, position);

    }

    @Override
    public int getItemCount() {
        if (tagsBeans != null) {
            return tagsBeans.size();
        }
        return 0;
    }

    class MallTabHolder extends RecyclerView.ViewHolder {
        //商品列表属性
        public View viewLine;
        public TextView txtItemTab;

        public MallTabHolder(View itemView) {
            super(itemView);
            viewLine = (View) itemView.findViewById(R.id.viewLine);
            txtItemTab = (TextView) itemView.findViewById(R.id.txtItemTab);

        }

        public void bind(final MallIndexInfo.DataBean.RecommendTagsBean itemBean, final OnRecyclerViewItemClickListener listener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemBean, position);
                }
            });
        }
    }
}


