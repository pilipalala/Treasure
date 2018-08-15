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

    @BindView(R.id.view_one)
    MyDrawingViewOne viewOne;
    @BindView(R.id.view_two)
    MyDrawingViewTwo viewTwo;
    @BindView(R.id.view_text)
    MyDrawingViewText viewText;


    @Override
    protected int initView() {
        return R.layout.activity_drawing_articles;
    }

    @Override
    protected void initData() {
        setRightTitle("文字", v -> startActivity(new Intent(DrawingArticlesTwoActivity.this, DrawingArticlesTextActivity.class)));
        viewOne.setVisibility(View.GONE);
        viewText.setVisibility(View.GONE);
        viewTwo.setVisibility(View.VISIBLE);
    }
}
