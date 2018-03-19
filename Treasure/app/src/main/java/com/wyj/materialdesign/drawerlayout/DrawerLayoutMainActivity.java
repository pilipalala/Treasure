package com.wyj.materialdesign.drawerlayout;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DrawerLayoutMainActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_drawer_layout_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(v -> finish());
    }


    @OnClick({R.id.drawer_layout_simple, R.id.drawer_layout_below, R.id.drawer_layout_layout})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.drawer_layout_simple:
                intent = new Intent(this, DrawerLayoutDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.drawer_layout_below:
                intent = new Intent(this, DrawerLayout_BelowToolbarActivity.class);
                startActivity(intent);
                break;
            case R.id.drawer_layout_layout:
                intent = new Intent(this, DrawerLayout_OtherActivity.class);
                startActivity(intent);
                break;
        }
    }
}
