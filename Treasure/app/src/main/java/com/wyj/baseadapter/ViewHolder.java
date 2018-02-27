package com.wyj.baseadapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wangyujie
 * Date 2018/2/27
 * Time 21:39
 * TODO RecyclerView的通用ViewHolder
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mView;
    public ViewHolder(View itemView) {
        super(itemView);
        mView = new SparseArray<>();
    }
    public <T extends View> T getView(int viewId) {
        View view =  mView.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mView.put(viewId, view);
        }

        return (T) view;
    }

    /**
     * @param viewId
     * @param text
     * @return
     * 设置文本
     */
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }
    /**
     * @param viewId
     * @param resourceId
     * @return
     * 设置图片资源
     */
    public ViewHolder setImageResource(int viewId, int resourceId) {
        ImageView view = getView(viewId);
        view.setImageResource(resourceId);
        return this;
    }

    public ViewHolder setImageResource(int viewId, HolderImageLoader imageLoader) {
        ImageView view = getView(viewId);
        imageLoader.loadImage(view, imageLoader.getPath());
        return this;
    }




    /**
     * @param viewId
     * @param listener
     * @return 设置点击事件
     */
    public ViewHolder setItemClick(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

}
