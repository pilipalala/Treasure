package com.wyj.treasure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.transition.Beauty;
import com.wyj.treasure.activity.transition.TransitionsListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin
 * on 2017/8/17.
 * TODO
 */

public class BeautyAdapter extends RecyclerView.Adapter<BeautyAdapter.ViewHolder> {
    private Context context;
    private List<Beauty> beauties;
    private LayoutInflater layoutInflater;

    public BeautyAdapter(Context context, List<Beauty> beauties) {
        this.context = context;
        this.beauties = beauties;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.adapter_beauty_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(beauties.get(position).name);
        holder.pic.setBackgroundResource(beauties.get(position).getImageResourceId(context));
    }

    @Override
    public int getItemCount() {
        return beauties == null ? 0 : beauties.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pic)
        ImageView pic;
        @BindView(R.id.name)
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TransitionsListActivity)context).startActivity(v, getPosition());
                }
            });
        }
    }

}
