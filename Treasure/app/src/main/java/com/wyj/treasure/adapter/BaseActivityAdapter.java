package com.wyj.treasure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wyj.treasure.R;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.treasure.utils.ClickBinder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yujie
 * @date on 2018/8/1
 * @describe TODO
 **/
public class BaseActivityAdapter extends RecyclerView.Adapter<BaseActivityAdapter.MyViewHolder> {

    private List<ItemInfo> data;
    private OnClickListener listener;

    public BaseActivityAdapter(Context context) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_base_activity_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(position);


    }

    public interface OnClickListener {
        void clickListener(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<ItemInfo> data) {
        this.data = data;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn)
        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindData(int position) {
            btn.setText(data.get(position).getItem());
            ClickBinder.bind(btn, v -> {
                if (listener != null) {
                    listener.clickListener(position);
                }
            });
        }
    }
}
