package com.wyj.treasure.activity.transition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransitionsRedActivity extends AppCompatActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions_red);
        ButterKnife.bind(this);
        initData();
    }


    protected void initData() {
        tvTitle.setText("这是个过渡动画");
        toolbar.setNavigationOnClickListener(v -> finish());

    }

}
