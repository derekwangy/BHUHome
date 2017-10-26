package com.bh.uhome.bhuhome.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.entity.DeviceControlInfo;
import com.bh.uhome.bhuhome.entity.HomeMenuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 凌霄
 * @date 2017/9/4.
 * @time 19:24.
 * @description Describe
 */
public class GalleryChildMenuAdapter extends RecyclerView.Adapter<GalleryChildMenuHolder>{
    private Activity mContext;
    private List<DeviceControlInfo.DataBean.DevNamesBean> goodsList = null;
    private int[] arrImage = {R.mipmap.ic_baoyuefuwu,R.mipmap.ic_find_medical,R.mipmap.ic_find_life,R.mipmap.ic_find_sport};

    //click事件接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(DeviceControlInfo.DataBean.DevNamesBean itemBean, int position);
    }
    private GalleryChildMenuAdapter.OnRecyclerViewItemClickListener listener = null;

    public GalleryChildMenuAdapter(Activity mContext,List<DeviceControlInfo.DataBean.DevNamesBean> goodsList,GalleryChildMenuAdapter.OnRecyclerViewItemClickListener listener){
        this.mContext = mContext;
        this.goodsList = goodsList;
        this.listener = listener;
    }

    @Override
    public GalleryChildMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_child_menu,parent, false);
        return new GalleryChildMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryChildMenuHolder holder, int position) {
        holder.bind(goodsList.get(position), listener, position);
        try {
            DeviceControlInfo.DataBean.DevNamesBean  data = goodsList.get(position);
            if (data != null){
                if (!TextUtils.isEmpty(data.getLocation())){
                    holder.txtGalleryGoodsName.setText(data.getLocation());
                }else{
                    holder.txtGalleryGoodsName.setText("");
                }

                if (!TextUtils.isEmpty(data.getType())){
//                    Glide.with(mContext).load(data.getImgUrl()).into(holder.imageGalleryItem);
                    holder.imageGalleryItem.setImageResource(arrImage[position]);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (goodsList != null){
            return goodsList.size();
        }
        return 0;
    }
}

class GalleryChildMenuHolder extends RecyclerView.ViewHolder{
    public TextView txtGalleryGoodsName;
    public ImageView imageGalleryItem;
    public GalleryChildMenuHolder(View itemView) {
        super(itemView);
        imageGalleryItem = (ImageView) itemView.findViewById(R.id.imageGalleryItem);
        txtGalleryGoodsName = (TextView) itemView.findViewById(R.id.txtGalleryGoodsName);

    }
    public void bind(final DeviceControlInfo.DataBean.DevNamesBean itemBean, final GalleryChildMenuAdapter.OnRecyclerViewItemClickListener listener,final int position) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(itemBean,position);
            }
        });
    }
}
