package com.wyj.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by wangyujie
 * Date 2018/1/7
 * Time 15:22
 * TODO
 */

public class DialogViewHelper {
    private Context mContext;
    private View mContentView;
    /*防止内存泄漏*/
    private SparseArray<WeakReference<View>> viewArray;
    /*TODO BaseAlertDialog_9*/
    public DialogViewHelper(Context mContext, int mViewLayoutResId) {
        this();
        this.mContext = mContext;
        mContentView = LayoutInflater.from(mContext).inflate(mViewLayoutResId, null);
    }

    public DialogViewHelper() {
        viewArray = new SparseArray<>();
    }

    /**
     * 设置布局
     *
     * @param contentView
     */
    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    /**
     * 设置文本
     *
     * @param charSequence
     * @param viewId
     */
    public void setText(CharSequence charSequence, int viewId) {
        /*减少findviewbyid的次数*/
        TextView textView = getView(viewId);

        if (textView != null) {
            textView.setText(charSequence);
        }

    }

    public <T extends View> T getView(int viewId) {
        View view = null;
        WeakReference<View> weakReference = viewArray.get(viewId);
        /**/
        if (weakReference != null) {
            view = weakReference.get();
        }

        if (view == null) {
            view = mContentView.findViewById(viewId);
            viewArray.put(viewId, new WeakReference<>(view));
        }
        return (T) view;
    }

    /**
     * 设置点击事件
     *
     * @param listener
     * @param viewId
     */
    public void setOnClick(int viewId, View.OnClickListener listener) {
        View view = mContentView.findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 获取ContentView
     *
     * @return
     */
    public View getContentViewId() {
        return mContentView;
    }
}
