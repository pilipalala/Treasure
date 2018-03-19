package com.wyj.treasure;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wyj.treasure.utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PxDpActivity extends AppCompatActivity {

    @BindView(R.id.root_layout)
    LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px_dp);
        ButterKnife.bind(this);
        LinearLayout rl_code1 = new LinearLayout(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.width = DensityUtil.px2dip(200);
        params1.height = DensityUtil.px2dip(50);
        rl_code1.setLayoutParams(params1);
        rl_code1.setBackgroundColor(Color.YELLOW);

        rootLayout.addView(rl_code1);//添加

        LinearLayout rl_code2 = new LinearLayout(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.width = 200;
        params2.height = 50;
        rl_code2.setLayoutParams(params2);
        rl_code2.setBackgroundColor(Color.GRAY);

        rootLayout.addView(rl_code2);//添加


        LinearLayout rl_code3 = new LinearLayout(this);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.width = DensityUtil.dip2px(200);
        params3.height = DensityUtil.dip2px(50);
        rl_code3.setLayoutParams(params3);
        rl_code3.setBackgroundColor(Color.BLUE);

        rootLayout.addView(rl_code3);//添加


    }
}
