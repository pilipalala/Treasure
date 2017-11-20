package com.wyj.treasure.mdcustom.splash;

import android.content.Context;

import com.wyj.treasure.R;

/**
 * Created by wangyujie
 * Date 2017/10/29
 * Time 15:32
 * TODO
 */

public class ContentView extends android.support.v7.widget.AppCompatImageView {
    public ContentView(Context context) {
        super(context);
        setImageResource(R.mipmap.icon_one);
        setScaleType(ScaleType.FIT_XY);
    }
}
