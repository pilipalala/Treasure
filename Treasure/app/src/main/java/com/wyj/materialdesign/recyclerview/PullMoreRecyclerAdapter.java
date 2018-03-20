package com.wyj.materialdesign.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wyj.treasure.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/19.
 */
public class PullMoreRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 1;
    //正在加载中
    public static final int LOADING_MORE = 2;
    private static final int TYPE_NORMAL_ITEM = 0;  //普通Item
    private static final int TYPE_FOOTER_ITEM = 1;  //底部FooterView
    private final Context context;
    private List<CardInfo> data;
    private LayoutInflater layoutInflater;
    private int load_more_status = 1; //默认为1
    /*添加监听器*/
    private OnItemClickListener listener;

    public PullMoreRecyclerAdapter(Context context, List<CardInfo> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        //如果viewType是普通item返回普通的布局，否则是底部布局并返回
        if (viewType == TYPE_NORMAL_ITEM) {
            View view = layoutInflater.inflate(R.layout.recyclerview_normal_view, parent, false);
            final NormalItmeViewHolder normalItmeViewHolder = new NormalItmeViewHolder(view);

            return normalItmeViewHolder;
        } else {
            View view = layoutInflater.inflate(R.layout.recyclerview_footer_view, parent, false);
            return new FooterViewHolder(view);
        }

    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalItmeViewHolder) {
            ((NormalItmeViewHolder) holder).tvPullmorerecycleContent.setText(data.get(position).getContent());
            ((NormalItmeViewHolder) holder).tvPullmorerecycleTitle.setText(data.get(position).getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.setOnItemClick(holder.itemView, position);
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.tvContent.setVisibility(View.VISIBLE);
                    footerViewHolder.tvContent.setText("上拉加载更多");
                    footerViewHolder.progressBar.setVisibility(View.GONE);
                    break;
                case LOADING_MORE:
                    footerViewHolder.tvContent.setVisibility(View.GONE);
//                    footerViewHolder.tvContent.setText("加载中...");
                    footerViewHolder.progressBar.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    @Override
    public int getItemCount() {
        //+1是加入底部的加载布局项
        return data.size() + 1;
    }

    /**
     * 如果position+1 等于整个布局所有item数的总和 就是底部布局
     * 反之则是普通item
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == position + 1) {
            Log.e("getItemViewType", "-----TYPE_FOOTER_ITEM: ");
            return TYPE_FOOTER_ITEM;
        } else {
            Log.e("getItemViewType", "TYPE_FOOTER_ITEM:----- ");
            return TYPE_NORMAL_ITEM;
        }
    }

    public void setMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    interface OnItemClickListener {
        void setOnItemClick(View itemView, int position);
    }

    /**
     * 底部FooterView布局
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.progress_view)
        ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    class NormalItmeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pullmorerecycle)
        ImageView ivPullmorerecycle;
        @BindView(R.id.tv_pullmorerecycle_title)
        TextView tvPullmorerecycleTitle;
        @BindView(R.id.tv_pullmorerecycle_content)
        TextView tvPullmorerecycleContent;

        public NormalItmeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
