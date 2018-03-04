package com.wyj.treasure.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.itemtouch.ItemTouchHelperAdapterCallback;
import com.wyj.treasure.activity.itemtouch.StartDragListener;
import com.wyj.treasure.mode.ViewHandleMode;
import com.wyj.treasure.utils.ToastUtil;
import com.wyj.treasure.widget.CircleImageView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * on 2017/10/27.13:00
 * TODO
 */

public class RecycleViewHandleAdapter extends RecyclerView.Adapter<RecycleViewHandleAdapter.MyViewHolder> implements ItemTouchHelperAdapterCallback {
    private StartDragListener listener;
    private List<ViewHandleMode> list;

    public RecycleViewHandleAdapter(List<ViewHandleMode> list, StartDragListener listener) {
        this.listener = listener;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_handle_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.ivLogo.setImageResource(list.get(position).getLogo());
        holder.tvName.setText(list.get(position).getName());
        holder.tvLastMsg.setText(list.get(position).getLastMsg());
        holder.tvTime.setText(list.get(position).getTime());
        holder.itemView.setOnClickListener(v -> ToastUtil.show(position));
        holder.ivLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listener.onStartDrag(holder);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //1、刷新数据
        Collections.swap(list, fromPosition, toPosition);
        //2、刷新adapter
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemSwiped(int position) {
        list.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)
        CircleImageView ivLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_lastMsg)
        TextView tvLastMsg;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
