package com.wyj.treasure;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyujie
 * @date 2018/8/15.10:47
 * @describe px和dp的区别以及 在布局文件和代码中的书写规律
 */
public class PxDpActivity extends BaseActivity {

    @BindView(R.id.root_layout)
    LinearLayout rootLayout;


    @Override
    protected int initView() {
        return R.layout.activity_px_dp;
    }

    @Override
    protected void initData() {
        setTitle("px和dp的区别");

        /**
         * 把 px 转成 dp
         */
        TextView tv_px = new TextView(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.width = DensityUtil.px2dip(200);//200px转dip
        params1.height = DensityUtil.px2dip(50);
        tv_px.setLayoutParams(params1);
        tv_px.setBackgroundColor(Color.CYAN);
        rootLayout.addView(tv_px);//添加px

        /**
         * 在代码中不带单位就是和 .xml 中的 px 一样
         */
        TextView rl_code2 = new TextView(this);
        rl_code2.setText("px");
        rl_code2.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.width = 200;
        params2.height = 50;
        rl_code2.setLayoutParams(params2);
        rl_code2.setBackgroundColor(Color.GRAY);

        rootLayout.addView(rl_code2);//添加


        /**
         * 这个就是把 dp 转成 px
         */
        TextView rl_code3 = new TextView(this);
        rl_code3.setText("dp/dip");
        rl_code3.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.width = DensityUtil.dip2px(200);//200dip转px
        params3.height = DensityUtil.dip2px(50);
        rl_code3.setLayoutParams(params3);
        rl_code3.setBackgroundColor(Color.BLUE);

        rootLayout.addView(rl_code3);//添加
    }
}
