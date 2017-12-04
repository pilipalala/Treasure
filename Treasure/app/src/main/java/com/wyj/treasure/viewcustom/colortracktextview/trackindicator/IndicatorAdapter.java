package com.wyj.treasure.viewcustom.colortracktextview.trackindicator;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author wangyujie
 *         on 2017/12/2.13:59
 *         TODO
 */

public abstract class IndicatorAdapter {
    /**
     * 获取总共的显示条数
     */
    public abstract int getCount();

    /**
     * 根据当前的位置获取view
     */
    public abstract View getView(int position, ViewGroup parent);

    /**
     * 高亮当前位置
     */
    public void setHightLightIndicator(View view) {

    }

    /**
     * 重置当前位置
     */
    public void setResetIndicator(View view) {

    }

    /**
     * 添加底部跟踪的指示器
     */
    public View getBottomTrackView() {

        return null;
    }
}
