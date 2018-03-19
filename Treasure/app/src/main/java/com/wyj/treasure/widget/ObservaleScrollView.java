package com.wyj.treasure.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author wangyujie
 *         on 2018/3/19.16:10
 *         TODO
 */

public class ObservaleScrollView extends ScrollView {
    public ObservaleScrollView(Context context) {
        super(context);
    }

    public ObservaleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservaleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public interface ScrollViewListener {
        void onScrollChanged(ObservaleScrollView scrollView, int x, int y, int oldx, int oldy);
    }


    private ScrollViewListener listener;

    public void setScrollViewListener(ScrollViewListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.onScrollChanged(this,l,t,oldl,oldt);
        }
    }
}