package com.wyj.navigationbar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by wangyujie
 * Date 2018/1/9
 * Time 23:12
 * TODO
 */

public class DefaultNavigationBar extends AbsNavigationBar {
    public DefaultNavigationBar(Builder.AbsNavigationParams mPramas) {
        super(mPramas);
    }

    @Override
    public int bindLayoutId() {
        return 0;
    }

    @Override
    public void applyView() {

    }

    public static class Builder extends AbsNavigationBar.Builder {

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
        }

        @Override
        public AbsNavigationBar builder() {
            return null;

        }
    }
}
