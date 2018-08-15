package com.wyj.materialdesign.recyclerview;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;

public class StyleActivity extends BaseActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @Override
    protected int initView() {
        return R.layout.activity_style;
    }

    @Override
    protected void initData() {
       setTitle("主题");
    }
}
