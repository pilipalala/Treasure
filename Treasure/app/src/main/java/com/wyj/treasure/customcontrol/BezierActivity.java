package com.wyj.treasure.customcontrol;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wyj.dialog.BaseAlertDialog;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BezierActivity extends BaseActivity {

    @BindView(R.id.myview)
    BezierView myview;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_bezier;
    }

    @Override
    protected void initData() {
        setRightTitle("效果", v -> onViewClicked());
    }

    public void onViewClicked() {
        myview.startAnim();
        BaseAlertDialog dialog = new BaseAlertDialog.Builder(this)
                .setContentView(R.layout.dialog_change_num)
                .setText(R.id.tv_cancle, "拒绝")
                .setText(R.id.tv_sure, "同意")
                .setCancelable(true)
                .setDefaultAnimation()
                .show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("");

        EditText etNum = dialog.getView(R.id.et_num);
        final int[] i = {1};
        dialog.setOnClick(R.id.bt_add, v -> runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etNum.setText((i[0]++) + "");
            }
        }));
    }
}
