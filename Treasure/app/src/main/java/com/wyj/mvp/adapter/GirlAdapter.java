package com.wyj.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyj.treasure.R;

/**
 * Created by wangyujie
 * on 2017/10/17.16:30
 * TODO
 */

public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.MyAdapter> {
    private Context context;

    public GirlAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_girl_item, parent, false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        public MyAdapter(View itemView) {
            super(itemView);
        }
    }
}
