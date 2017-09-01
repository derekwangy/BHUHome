package com.bh.uhome.bhuhome.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.uhome.bhuhome.R;
import com.bh.uhome.bhuhome.entity.HomeMenuInfo;
import com.bh.uhome.bhuhome.entity.MallIndexInfo;
import com.bh.uhome.bhuhome.util.MallUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 *
 * Created by derek on 17/5/18.
 */
public class GalleryGoodsAdapter extends RecyclerView.Adapter<GalleryGoodsHolder>{
    private Activity mContext;
    private ArrayList<HomeMenuInfo> goodsList = null;
    private int[] arrImage = {R.mipmap.ic_baoyuefuwu,R.mipmap.ic_find_medical,R.mipmap.ic_baoyuefuwu,R.mipmap.ic_find_medical};

    //click事件接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(HomeMenuInfo itemBean, int position);
    }
    private OnRecyclerViewItemClickListener listener = null;

    public GalleryGoodsAdapter(Activity mContext,ArrayList<HomeMenuInfo> goodsList,OnRecyclerViewItemClickListener listener){
        this.mContext = mContext;
        this.goodsList = goodsList;
        this.listener = listener;
    }

    @Override
    public GalleryGoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_goods,parent, false);
        return new GalleryGoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryGoodsHolder holder, int position) {
        holder.bind(goodsList.get(position), listener, position);
        try {
            HomeMenuInfo  data = goodsList.get(position);
            if (data != null){
                if (!TextUtils.isEmpty(data.getName())){
                    holder.txtGalleryGoodsName.setText(data.getName());
                }else{
                    holder.txtGalleryGoodsName.setText("");
                }

                if (!TextUtils.isEmpty(data.getImgUrl())){
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

class GalleryGoodsHolder extends RecyclerView.ViewHolder{
    public TextView txtGalleryGoodsName;
    public ImageView imageGalleryItem;
    public GalleryGoodsHolder(View itemView) {
        super(itemView);
        imageGalleryItem = (ImageView) itemView.findViewById(R.id.imageGalleryItem);
        txtGalleryGoodsName = (TextView) itemView.findViewById(R.id.txtGalleryGoodsName);

    }
    public void bind(final HomeMenuInfo itemBean, final GalleryGoodsAdapter.OnRecyclerViewItemClickListener listener,final int position) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(itemBean,position);
            }
        });
    }
}
