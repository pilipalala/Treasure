package com.wyj.treasure.customcontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wyj.dialog.MyAlertDialog;
import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BezierActivity extends AppCompatActivity {

    @BindView(R.id.myview)
    BezierView myview;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        ButterKnife.bind(this);
        tvRightTitle.setVisibility(View.VISIBLE);
        tvRightTitle.setText("效果");

    }

    @OnClick(R.id.tv_right_title)
    public void onViewClicked() {
        myview.startAnim();
        MyAlertDialog dialog = new MyAlertDialog.Builder(this)
                .setContentView(R.layout.dialog_change_num)
                .setText(R.id.tv_cancle, "拒绝")
                .setText(R.id.tv_sure, "同意")
                .setCancelable(false)
                .setDefaultAnimation()
                .show();
        EditText etNum = dialog.getView(R.id.et_num);
        final int[] i = {1};
        dialog.setOnClick(R.id.bt_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etNum.setText((i[0]++) + "");

                    }
                });
            }
        });
    }
}
