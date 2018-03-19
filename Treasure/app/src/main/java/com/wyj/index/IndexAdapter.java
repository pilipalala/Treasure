package com.wyj.index;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wyj.treasure.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/6/4
 * Time 16:22
 * TODO
 */

public class IndexAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Person> data;
    private String word;

    public IndexAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_index_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        word = data.get(position).getPinyin().substring(0, 1);
        holder.tvWord.setText(word);
        holder.tvName.setText(data.get(position).getName());
        if (position == 0) {
            holder.tvWord.setVisibility(View.VISIBLE);
        } else {
            /*得到前一个位置对应的字母，如果当前的字母和上一个相同则隐藏，反之显示*/
            if (word.equals(data.get(position - 1).getPinyin().substring(0, 1))) {
                holder.tvWord.setVisibility(View.GONE);
            } else {
                holder.tvWord.setVisibility(View.VISIBLE);
            }

        }
        return convertView;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    static class ViewHolder {
        @BindView(R.id.tv_word)
        TextView tvWord;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
