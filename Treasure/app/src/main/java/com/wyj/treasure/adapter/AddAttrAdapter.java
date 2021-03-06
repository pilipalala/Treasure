package com.wyj.treasure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.tagview.Tag;
import com.wyj.treasure.tagview.TagView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/9/6
 * Time 22:37
 * TODO
 */

public class AddAttrAdapter extends RecyclerView.Adapter<AddAttrAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private OnDeteleListener deteleListener;
    private OnNotifyListener notifyListener;

    public AddAttrAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_add_attr_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("字段 1"));
        tagList.add(new Tag("字段 2"));
        Tag tag = new Tag("字段 4");
        tag.radius = 20f;
        tagList.add(tag);
        tagList.add(new Tag("字段 5"));
        tagList.add(new Tag("字段 5"));
        tagList.add(new Tag("字段 5"));
        tagList.add(new Tag("字段 5"));
        tagList.add(new Tag("字段 6"));
        holder.tagviewItemList.addTags(tagList);
        holder.tvDeteleAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deteleListener != null) {
                    deteleListener.delete(position);
                }
            }
        });
        holder.tvNotifyAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notifyListener != null) {
                    notifyListener.notify(position);
                }
            }
        });
    }

    public interface OnDeteleListener {
        void delete(int position);
    }

    public interface OnNotifyListener {
        void notify(int position);
    }

    public void setOnDeteleListener(OnDeteleListener deteleListener) {
        this.deteleListener = deteleListener;
    }

    public void setOnNotifyListener(OnNotifyListener notifyListener) {
        this.notifyListener = notifyListener;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tagview_item_list)
        TagView tagviewItemList;
        @BindView(R.id.tv_detele_attr)
        TextView tvDeteleAttr;
        @BindView(R.id.tv_notify_attr)
        TextView tvNotifyAttr;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
