package com.wyj.baseadapter;

import android.widget.ImageView;

import com.wyj.treasure.MyApplication;
import com.wyj.treasure.utils.GlideUtils;

/**
 * Created by wangyujie
 * Date 2018/2/27
 * Time 23:56
 * TODO
 */

public class ImageLoad extends HolderImageLoader {
    public ImageLoad(String path) {
        super(path);
    }

    @Override
    public void loadImage(ImageView view, String path) {
        //第三方 加载图片
        GlideUtils.loadImageUrl(MyApplication.getContext(), path, view);
    }
}
