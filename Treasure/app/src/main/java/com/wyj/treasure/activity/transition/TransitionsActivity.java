package com.wyj.treasure.activity.transition;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransitionsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_transitions);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("属性动画");
        toolbar.setNavigationIcon(R.mipmap.icon_top_back);
        toolbar.setNavigationOnClickListener(v -> finish());
    }


    @OnClick({R.id.btn, R.id.btn_list})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn:
                intent.setClass(this, TransitionsSlideActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_list:
                intent.setClass(this, TransitionsListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
