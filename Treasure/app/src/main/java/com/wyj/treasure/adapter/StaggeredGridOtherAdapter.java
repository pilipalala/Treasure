package com.wyj.treasure.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wyj.treasure.R;

import java.util.ArrayList;

/**
 * Created by wangyujie
 * on 2017/9/22.14:30
 * TODO
 */

public class StaggeredGridOtherAdapter extends RecyclerView.Adapter<StaggeredGridOtherAdapter.ViewHolder> {
    private ArrayList<String> dataList = new ArrayList<>();

    public void replaceAll(ArrayList<String> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_staggered_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int width = ((Activity) holder.ivImage.getContext()).getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams params = holder.ivImage.getLayoutParams();
        //设置图片的相对于屏幕的宽高比


        if (position % 5 == 0 || position % 5 == 1 || position % 5 == 2) {
            /*长宽比 28:25*/
            params.height = (width * 25) / 56;
        } else {
            /*长宽比 47:25*/
            params.height = (width * 25) / 94;
        }
        String text = dataList.get(position);
        Glide.with(holder.itemView.getContext()).load(text).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher).crossFade().into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private String data;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
        }

    }
}
