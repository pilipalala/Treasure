package com.wyj.treasure.viewcustom.bannerviewpager;

import android.view.View;

/**
 * @author wangyujie
 *         on 2017/11/18.13:02
 *         TODO
 */

public abstract class BannerAdapter {

    /**
     * 1.根据位置获取viewpager里面的子view
     */
    public abstract View getView(int position, View mConverView);

    /**
     * 5.获取轮播的数量
     */
    public abstract int getCount();

    /**
     * 根据位置获取广告位描述
     *
     * @param mCurrentPosition
     * @return
     */
    public String getBannerDesc(int mCurrentPosition) {
        return "";
    }
}
