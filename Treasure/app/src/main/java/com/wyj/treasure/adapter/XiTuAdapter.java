package com.wyj.treasure.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyj.materialdesign.recyclerview.PaletteActivity;
import com.wyj.materialdesign.recyclerview.RecyclerViewDetailActivity;
import com.wyj.materialdesign.recyclerview.XiTuActivity;
import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyujie
 *         on 2018/3/19.16:21
 *         TODO
 */

public class XiTuAdapter extends RecyclerView.Adapter<XiTuAdapter.MyViewHolder> {
    public XiTuAdapter(Context mContext, int num) {
        this.mContext = mContext;
        this.num = num;
    }
    private final Context mContext;
    private final int num;



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycleview_tablayout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.llDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position % 3 == 0) {
                    mContext.startActivity(new Intent(mContext, RecyclerViewDetailActivity.class));
                } else if (position % 3 == 1) {
                    mContext.startActivity(new Intent(mContext, XiTuActivity.class));
                } else if (position % 3 == 2) {
                    mContext.startActivity(new Intent(mContext, PaletteActivity.class));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return num;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pullmorerecycle)
        ImageView ivPullmorerecycle;
        @BindView(R.id.tv_pullmorerecycle_title)
        TextView tvPullmorerecycleTitle;
        @BindView(R.id.ll_detail)
        LinearLayout llDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
