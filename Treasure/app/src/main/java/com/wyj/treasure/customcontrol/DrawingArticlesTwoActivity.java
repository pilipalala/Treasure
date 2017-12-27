package com.wyj.treasure.customcontrol;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawingArticlesTwoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_one)
    MyDrawingViewOne viewOne;
    @BindView(R.id.view_two)
    MyDrawingViewTwo viewTwo;
    @BindView(R.id.view_text)
    MyDrawingViewText viewText;
    @BindView(R.id.tv_right_title)
    TextView tvRight;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_drawing_articles);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(v -> finish());
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("文字");
        viewOne.setVisibility(View.GONE);
        viewText.setVisibility(View.GONE);
        viewTwo.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.tv_right_title)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(DrawingArticlesTwoActivity.this, DrawingArticlesTextActivity.class);
        startActivity(intent);

    }
}
