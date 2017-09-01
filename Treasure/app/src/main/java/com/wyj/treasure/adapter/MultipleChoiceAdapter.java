package com.wyj.treasure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.mode.Entity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin
 * on 2017/8/31.
 * TODO
 */

public class MultipleChoiceAdapter extends BaseAdapter {
    private final ArrayList<Entity> timeList;
    private final Context context;

    public MultipleChoiceAdapter(Context context, ArrayList<Entity> timeList) {
        this.context = context;
        this.timeList = timeList;
    }

    @Override
    public int getCount() {
        return timeList.size();
    }

    @Override
    public Object getItem(int i) {
        return timeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_multiple_choice, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.itemText.setText(timeList.get(i).getTime());
        if (timeList.get(i).isSelect()) {
            /*被选中字体颜色变为白色   item背景变为蓝色*/
            holder.itemText.setSelected(true);
        } else {
             /*未被选中字体颜色变为蓝色   item背景变为白色*/
            holder.itemText.setSelected(false);
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.item_text)
        TextView itemText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
