package com.wyj.treasure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.mode.ItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin
 * on 2017/8/14.
 * TODO
 */

public class BaseListAdapter extends RecyclerView.Adapter<BaseListAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ItemInfo> data;
    private OnClickListener listener;

    public BaseListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_base_list_item, parent, false);
        return new ViewHolder(view);
    }

    public interface OnClickListener {
        void clickListener(int position);
    }
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(data.get(position).toString());
        holder.itemView.setOnClickListener(v -> {
            if (null != listener) {
                listener.clickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<ItemInfo> data) {
        this.data = data;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.text)
        TextView text;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
