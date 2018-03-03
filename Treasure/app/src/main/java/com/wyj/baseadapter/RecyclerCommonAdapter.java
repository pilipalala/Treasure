package com.wyj.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(mLayoutId, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mData.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //条目点击事件
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longListener != null) {
                    longListener.onItemLongClick(position);
                }
                /*返回true 触发长按事件 不触发点击事件*/
                return true;
            }
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
}
