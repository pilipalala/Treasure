package com.wyj.treasure.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.GlideUtils;

/**
 * Created by admin
 * on 2017/8/31.
 * TODO
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {
    private static final String TAG = "FeedAdapter";

    @Override public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new FeedHolder(itemView);
    }

    @Override public void onBindViewHolder(FeedHolder holder, int position) {
        GlideUtils.loadImageResource(holder.itemView.getContext(),getAvatarResId(position),holder.mIvAvatar);
        GlideUtils.loadImageResource(holder.itemView.getContext(),getContentResId(position),holder.mIvContent);
        holder.mTvNickname.setText("Taeyeon " + position);
    }

    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.mipmap.avatar1;
            case 1:
                return R.mipmap.avatar2;
            case 2:
                return R.mipmap.avatar3;
            case 3:
                return R.mipmap.avatar4;
        }
        return 0;
    }

    private int getContentResId(int position) {
        switch (position % 4) {
            case 0:
                return R.mipmap.taeyeon_one;
            case 1:
                return R.mipmap.taeyeon_two;
            case 2:
                return R.mipmap.taeyeon_three;
            case 3:
                return R.mipmap.taeyeon_four;
        }
        return 0;
    }

    @Override public int getItemCount() {
        return 100;
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {
        ImageView mIvAvatar;
        ImageView mIvContent;
        TextView mTvNickname;

        public FeedHolder(View itemView) {
            super(itemView);
            mIvAvatar = itemView.findViewById(R.id.iv_avatar);
            mIvContent = itemView.findViewById(R.id.iv_content);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
        }
    }
}
