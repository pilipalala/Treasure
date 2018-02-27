package com.wyj.baseadapter;

import android.widget.ImageView;

/**
 * Created by wangyujie
 * Date 2018/2/27
 * Time 23:58
 * TODO
 */

abstract class HolderImageLoader {

    private String path;

    public HolderImageLoader(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public abstract void loadImage(ImageView view, String path);
}
