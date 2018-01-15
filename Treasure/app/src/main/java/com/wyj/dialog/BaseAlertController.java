package com.wyj.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by wangyujie
 * Date 2018/1/7
 * Time 15:22
 * TODO
 */

public class BaseAlertController {

    private BaseAlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;
    /*TODO BaseAlertDialog_7*/
    public BaseAlertController(BaseAlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    public BaseAlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    /**
     * 设置文本
     *
     * @param charSequence
     * @param viewId
     */
    public void setText(CharSequence charSequence, int viewId) {
        mViewHelper.setText(charSequence, viewId);

    }

    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    /**
     * 设置点击事件
     *
     * @param listener
     * @param viewId
     */
    public void setOnClick(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnClick(viewId, listener);
    }

    public static class AlertParams {
        public int mThemeResId;
        public Context mContext;
        /*点击空白是否能够取消*/
        public boolean mCancelable = true;
        /*dialog取消监听*/
        public DialogInterface.OnCancelListener mOnCancelListener;
        /*dialog消失监听*/
        public DialogInterface.OnDismissListener mOnDismissListener;
        /*按键监听*/
        public DialogInterface.OnKeyListener mOnKeyListener;
        /*自定义布局View*/
        public View mView;
        /*布局layout*/
        public int mViewLayoutResId;
        /*存放字体的修改*/
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        /*存放点击事件*/
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        /*默认宽度*/
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        /*设置高度*/
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        /*设置位置*/
        public int mGravity = Gravity.CENTER;
        /*设置动画*/
        public int mAnimation = 0;

        /*TODO BaseAlertDialog_3*/
        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }
        /*TODO BaseAlertDialog_8*/
        public void apply(BaseAlertController mAlert) {
            /*设置参数*/
            DialogViewHelper viewHelper = null;


            /*设置布局*/
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }
            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }
            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局");
            }

            /*给dialog设置布局*/
            mAlert.getDialog().setContentView(viewHelper.getContentViewId());
            /*设置Controller的辅助类*/
            mAlert.setViewHelper(viewHelper);
            /*设置文本*/

            for (int i = 0; i < mTextArray.size(); i++) {
                viewHelper.setText(mTextArray.valueAt(i), mTextArray.keyAt(i));

            }

            /*设置点击事件*/
            for (int i = 0; i < mClickArray.size(); i++) {
                viewHelper.setOnClick(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            /*配置自定义效果 全屏 动画*/

            Window window = mAlert.getWindow();
            /*设置位置*/
            window.setGravity(mGravity);
            /*设置动画*/
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = mWidth;
            attributes.height = mHeight;
            window.setAttributes(attributes);


        }

    }
}
