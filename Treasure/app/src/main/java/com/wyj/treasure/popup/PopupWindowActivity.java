package com.wyj.treasure.popup;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupWindowActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.menu)
    TextView menu;
    @BindView(R.id.line)
    View line;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_popup_window;
    }

    @Override
    protected void initData() {

    }


    private void showPopBottom() {
        View view = LayoutInflater.from(this).inflate(R.layout.popuplayout, null);
        MyPopupWindow popupWindow = new MyPopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_popup_window, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    private void showPopDropDown() {
        View view = LayoutInflater.from(this).inflate(R.layout.popuplayout, null);
        MyPopupWindow popupWindow = new MyPopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置动画所对应的style
        popupWindow.setAnimationStyle(R.style.dialog_from_bottom_anim);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(line);


    }

    @OnClick({R.id.menu, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu:
                showPopDropDown();
                break;
            case R.id.btn:
                showPopBottom();
                break;
        }
    }
}
