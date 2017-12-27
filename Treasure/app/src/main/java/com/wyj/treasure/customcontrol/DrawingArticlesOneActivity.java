package com.wyj.treasure.customcontrol;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawingArticlesOneActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_one)
    MyDrawingViewOne viewOne;
    @BindView(R.id.view_two)
    MyDrawingViewTwo viewTwo;
    @BindView(R.id.view_text)
    MyDrawingViewText viewText;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_drawing_articles);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(v -> finish());
        viewOne.setVisibility(View.VISIBLE);
        viewTwo.setVisibility(View.GONE);
        viewText.setVisibility(View.GONE);

    }
}
