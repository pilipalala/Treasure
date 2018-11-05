package com.wyj.materialdesign.recyclerview;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BottomSheetActivity extends BaseActivity {


    BottomSheetBehavior behavior;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button0)
    Button button0;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.bottom_sheet)
    NestedScrollView bottomSheet;

    private String TAG = BottomSheetActivity.class.getSimpleName();


    @Override
    protected int getContentViewID() {
        return 0;
    }

    @Override
    protected int contentView() {
        return R.layout.activity_bottom_sheet;
    }

    @Override
    protected void initData() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_top_back);
        toolbar.setNavigationOnClickListener(view -> finish());
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            /**
             * behavior_hideable 对应代码 behavior.setHideable(false);
             * behavior_peekHeight 对应代码 behavior.setPeekHeight(50);
             * */
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                /*bottomSheet状态的改变*/
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    Log.e(TAG, "完全展开的状态: ");
                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    Log.e(TAG, "隐藏状态: ");
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    Log.e(TAG, "折叠关闭状态: ");
                } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    Log.e(TAG, "拖拽松开之后到达终点位置（collapsed or expanded）前的状态: ");
                } else if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    Log.e(TAG, "被拖拽状态:");
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                /*这里是拖拽的回调，根据slideOffset可以做些动画*/
                //这里是拖拽中的回调，slideOffset为0-1 完全收起为0 完全展开为1


            }
        });
    }

    @OnClick({R.id.button0, R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button0:
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.button1:
                final BottomSheetDialog dialog = new BottomSheetDialog(this);
                View inflate = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
                dialog.setContentView(inflate);
                Button bsdButton = inflate.findViewById(R.id.bsd_button);
                TextView textView = inflate.findViewById(R.id.bsd_text);

                textView.setText("BottomSheetDialog");
                bsdButton.setOnClickListener(view1 -> {
                    startActivity(new Intent(BottomSheetActivity.this, RecyclerViewDetailActivity.class));
                    dialog.dismiss();
                });
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                break;
            case R.id.button2:
                new FullSheetDialogFragment().show(getSupportFragmentManager(), "dialog");
                break;
        }
    }
}
