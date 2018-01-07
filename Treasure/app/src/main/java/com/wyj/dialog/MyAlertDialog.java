package com.wyj.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.wyj.treasure.R;

/**
 * Created by wangyujie
 * Date 2018/1/7
 * Time 15:21
 * TODO
 */

public class MyAlertDialog extends Dialog {
    private MyAlertController mAlert;

    public MyAlertDialog(@NonNull Context context) {
        this(context, R.style.myDialog);
    }

    public MyAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new MyAlertController(this, getWindow());
    }

    /**
     * 设置文本
     *
     * @param charSequence
     * @param viewId
     */
    public void setText(CharSequence charSequence, int viewId) {
        mAlert.setText(charSequence, viewId);
    }

    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnClick(int viewId, View.OnClickListener listener) {
        mAlert.setOnClick(viewId, listener);
    }

    public static class Builder {

        private MyAlertController.AlertParams P;

        public Builder(Context context) {
            this(context, R.style.myDialog);
        }

        public Builder(Context context, int themeResId) {
            P = new MyAlertController.AlertParams(context, themeResId);
        }

        public MyAlertDialog create() {
            final MyAlertDialog dialog = new MyAlertDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public MyAlertDialog show() {
            final MyAlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

        /**
         * @param layoutResId 设置布局
         * @return
         */
        public Builder setContentView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * 设置布局
         *
         * @param view
         * @return
         */
        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置文本
         *
         * @param viewId
         * @param text
         * @return
         */
        public Builder setText(int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param view
         * @param listener
         * @return
         */
        public Builder setOnCickListener(int view, View.OnClickListener listener) {
            P.mClickArray.put(view, listener);
            return this;
        }

        /**
         * 设置全屏
         *
         * @return
         */
        public Builder setFullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置方向 动画
         *
         * @param where
         * @param isAnimation
         * @return
         */
        public Builder setFromWhere(int where, boolean isAnimation) {
            if (isAnimation) {
                P.mAnimation = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = where;
            return this;
        }

        /**
         * 设置宽高
         *
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        /**
         * 设置默认动画
         *
         * @return
         */
        public Builder setDefaultAnimation() {
            P.mAnimation = R.style.dialog_scal_anim;
            return this;
        }

        /**
         * 设置动画
         *
         * @param animation
         * @return
         */
        public Builder setAnimation(int animation) {
            P.mAnimation = animation;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }
    }
}
