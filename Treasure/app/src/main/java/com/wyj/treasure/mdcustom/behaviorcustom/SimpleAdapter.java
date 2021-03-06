package com.wyj.treasure.mdcustom.behaviorcustom;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * on 2017/10/31.13:51
 * TODO
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MyViewHolder> {
    public String[] ITEMS = {"赵丽颖", "林心如", "柳岩", "陈乔恩", "张三", "杨幂", "范冰冰", "刘亦菲"};
    private final LayoutInflater mInflater;

    public SimpleAdapter(RecyclerView recyclerView) {
        mInflater = LayoutInflater.from(recyclerView.getContext());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view,
                                       RecyclerView parent, RecyclerView.State state) {
                final int position = parent.getChildViewHolder(view).getAdapterPosition();
                final int offset = parent.getResources()
                        .getDimensionPixelOffset(R.dimen.activity_vertical_margin);
                outRect.set(offset,
                        position == 0 ? offset : 0,
                        offset,
                        offset);
            }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_simple_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text.setText(ITEMS[position]);
    }

    @Override
    public int getItemCount() {
        return ITEMS.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setClickable(true);
        }
    }

}
