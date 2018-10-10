package com.wyj.baseadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyj.treasure.R;
import com.wyj.treasure.customcontrol.Point;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2018/2/27
 * Time 21:38
 * TODO RecyclerView的通用Adapter
 */

public abstract class RecyclerCommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private final LayoutInflater inflater;
    private Context mContext;
    //布局id
    private int mLayoutId;
    //通用参数
    private List<T> mData;
    private ItemClickListener listener;
    private ItemLongClickListener longListener;

    public RecyclerCommonAdapter(Context context, List<T> mData, int mLayoutId) {
        this.mContext = context;
        this.mLayoutId = mLayoutId;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
    }

    public RecyclerCommonAdapter(Context context, int mLayoutId) {
        this.mContext = context;
        this.mLayoutId = mLayoutId;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(mLayoutId, parent, false);
        return new ViewHolder(inflate);
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mData.get(position), position);
        holder.itemView.setOnClickListener(v -> {
            //条目点击事件
            if (listener != null) {
                listener.onItemClick(v, position);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (longListener != null) {
                longListener.onItemLongClick(position);
            }
            /*返回true 触发长按事件 不触发点击事件*/
            return true;
        });

    }

    public void setOnItemClick(ItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClick(ItemLongClickListener longListener) {
        this.longListener = longListener;
    }

    /**
     * @param holder
     * @param data     当前条目
     * @param position 当前位置
     */
    public abstract void convert(ViewHolder holder, T data, int position);


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }
}
