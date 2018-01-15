package com.wyj.navigationbar;

import android.app.Activity;
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
        setText(R.id.tv_title, getPramas().mTitle);
        setText(R.id.tv_right_title, getPramas().mRightTitle);
        setOnClickListener(R.id.tv_right_title, getPramas().mRightClickListener);
        setToolBarOnClickListener(R.id.toolbar, getPramas().mLeftClickListener);
    }




    public static class Builder extends AbsNavigationBar.Builder {
        DefaultNavigationPrams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationPrams(context, parent);

        }

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationPrams(context, null);

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

        /**
         * 设置右边的点击事件
         *
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener listener) {
            P.mRightClickListener = listener;
            return this;
        }
        /**
         * 设置左边的点击事件
         *
         * @param listener
         * @return
         */
        public DefaultNavigationBar.Builder setLeftClickListener(View.OnClickListener listener) {
            P.mLeftClickListener = listener;
            return this;
        }

        public static class DefaultNavigationPrams extends AbsNavigationBar.Builder.AbsNavigationParams {
            /*所有的效果*/
            public String mTitle;
            public int mRightIcon;
            public View.OnClickListener mRightClickListener;
            public String mRightTitle;
            public View.OnClickListener mLeftClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity)mContext).finish();
                }
            };

            public DefaultNavigationPrams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
