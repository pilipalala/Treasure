package com.wyj.treasure.superadapter;

/**
 * Created by wangyujie
 * Date 2017/10/31
 * Time 23:37
 * TODO
 */

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder基类
 */
public class SuperViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    public SuperViewHolder(View view) {
        super(view);
        this.views = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getRootView() {
        return itemView;
    }
}