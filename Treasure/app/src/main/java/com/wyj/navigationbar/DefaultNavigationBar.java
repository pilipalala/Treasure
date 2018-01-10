package com.wyj.navigationbar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.wyj.treasure.R;

/**
 * Created by wangyujie
 * Date 2018/1/9
 * Time 23:12
 * TODO
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationPrams> {
    public DefaultNavigationBar(Builder.DefaultNavigationPrams mPramas) {
        super(mPramas);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title;
    }

    @Override
    public void applyView() {
        /*绑定参数*/
        setText(R.id.tv_title,getPramas().mTitle);
        setText(R.id.tv_right_title,getPramas().mRightTitle);
        setOnClickListener(R.id.tv_right_title,getPramas().mRightClickListener);
    }



    public static class Builder extends AbsNavigationBar.Builder {
        DefaultNavigationPrams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationPrams(context, parent);

        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;

        }

        /*设置所有的效果*/

        /**
         * 设置标题
         *
         * @param title
         * @return
         */
        public DefaultNavigationBar.Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }
        public DefaultNavigationBar.Builder setRightTitle(String rightTitle) {
            P.mRightTitle = rightTitle;
            return this;
        }

        /**
         * 设置右边图片
         *
         * @param rightIcon
         * @return
         */
        public DefaultNavigationBar.Builder setRightIcon(int rightIcon) {
            P.mRightIcon = rightIcon;
            return this;
        }
        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener listener) {
            P.mRightClickListener = listener;
            return this;
        }

        public static class DefaultNavigationPrams extends AbsNavigationBar.Builder.AbsNavigationParams {
            /*所有的效果*/
            public String mTitle;
            public int mRightIcon;
            public View.OnClickListener mRightClickListener;
            public String mRightTitle;

            public DefaultNavigationPrams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
