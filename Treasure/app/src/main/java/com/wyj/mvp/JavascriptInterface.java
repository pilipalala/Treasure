package com.wyj.mvp;

import android.content.Context;

import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

/**
 * @author wangyujie
 * @date 2018/9/18.18:48
 * @describe 添加描述
 */
public class JavascriptInterface {

    private Context context;

    public JavascriptInterface(Context context) {
        this.context = context;
    }

    /**
     * 在WebView中 点击图片
     */
    @android.webkit.JavascriptInterface
    public void openImage(String img) {
        ToastUtil.show(img);
        LogUtil.d(img);
    }
}
