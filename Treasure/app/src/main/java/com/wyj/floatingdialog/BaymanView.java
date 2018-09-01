package com.wyj.floatingdialog;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.ToastUtil;

/**
 * @author wangyujie
 * @date 2018/8/23.17:24
 * @describe 悬浮移动按钮
 */
public class BaymanView extends BaseFloatView {

    public BaymanView(Activity activity) {
        super(activity);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.bayman_button_layout;
    }

    @Override
    public void bindView(View v, AttributeSet attrs) {
        TextView textView = v.findViewById(R.id.tv_float);
        textView.setOnClickListener(v1 -> ToastUtil.show("哈哈哈"));
    }
}