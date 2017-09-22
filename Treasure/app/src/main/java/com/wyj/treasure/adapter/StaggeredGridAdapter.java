package com.wyj.treasure.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wyj.treasure.MyApplication;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.StaggeredGridOtherActivity;

import java.util.ArrayList;

/**
 * Created by wangyujie
 * on 2017/9/22.14:30
 * TODO
 */

public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.ViewHolder> {
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
        if (position % 3 == 0) {
            params.width = width * 1 / 2;
            params.height = width * 2 / 3;
            holder.ivImage.setLayoutParams(params);
        } else {
            params.width = width * 1 / 2;
            params.height = width * 2 / 6;
            holder.ivImage.setLayoutParams(params);
        }
        String text = dataList.get(position);
        Glide.with(holder.itemView.getContext()).load(text).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher).crossFade().into(holder.ivImage);
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.getContext().startActivity(new Intent(MyApplication.getContext(), StaggeredGridOtherActivity.class));
        });
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
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }

    }
}
