package com.wyj.materialdesign;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.materialdesign.drawerlayout.DrawerLayoutMainActivity;
import com.wyj.materialdesign.recyclerview.BottomSheetActivity;
import com.wyj.materialdesign.recyclerview.PaletteActivity;
import com.wyj.materialdesign.recyclerview.RecyclerRefreshActivity;
import com.wyj.materialdesign.recyclerview.StyleActivity;
import com.wyj.materialdesign.recyclerview.XiTuActivity;
import com.wyj.materialdesign.toolbar.TabLayoutMainActivity;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaterialDesignActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_material_design);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_drawerlayout, R.id.btn_bottomsheet,
            R.id.btn_palette, R.id.btn_xitu, R.id.btn_style,
            R.id.btn_refresh,R.id.btn_tablayout})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_drawerlayout:
                intent.setClass(this, DrawerLayoutMainActivity.class);
                break;
            case R.id.btn_bottomsheet:
                intent.setClass(this, BottomSheetActivity.class);
                break;
            case R.id.btn_palette:
                intent.setClass(this, PaletteActivity.class);
                break;
            case R.id.btn_xitu:
                intent.setClass(this, XiTuActivity.class);
                break;
            case R.id.btn_style:
                intent.setClass(this, StyleActivity.class);
                break;
            case R.id.btn_refresh:
                intent.setClass(this, RecyclerRefreshActivity.class);
                break;
            case R.id.btn_tablayout:
                intent.setClass(this, TabLayoutMainActivity.class);
                break;
        }
        startActivity(intent);
    }


}
