package com.wyj.treasure.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.GlideUtils;

/**
 * Created by admin
 * on 2017/9/1.
 * TODO
 */

public class MultiFeedAdapter extends RecyclerView.Adapter {
    public static final int TYPE_TIME = 0;
    public static final int TYPE_FEED = 1;

    @Override
    public int getItemViewType(int position) {
        if (position % 4 == 0) {
            return TYPE_TIME;
        } else {
            return TYPE_FEED;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_TIME) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
            return new TimeHolder(view);
        } else if (viewType == TYPE_FEED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
            return new FeedAdapter.FeedHolder(view);
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TimeHolder) {
            ((TimeHolder) holder).mTvTime.setText(getTime(position));
        } else if (holder instanceof FeedAdapter.FeedHolder) {
            ((FeedAdapter.FeedHolder) holder).mTvNickname.setText("Taeyeon " + position);
            GlideUtils.loadImageResource(holder.itemView.getContext(), getAvatarResId(position), ((FeedAdapter.FeedHolder) holder).mIvAvatar);
            GlideUtils.loadImageResource(holder.itemView.getContext(), getContentResId(position), ((FeedAdapter.FeedHolder) holder).mIvContent);
        }
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
    private String getTime(int position) {
        return "NOVEMBER " + (1 + position / 4);
    }
    @Override
    public int getItemCount() {
        return 100;
    }
    public static class TimeHolder extends RecyclerView.ViewHolder {
        TextView mTvTime;

        public TimeHolder(View itemView) {
            super(itemView);
            mTvTime = itemView.findViewById(R.id.tv_time);
        }
    }

}
