package com.wyj.treasure.customcontrol;

import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;

public class DrawingArticlesOneActivity extends BaseActivity {

    @BindView(R.id.view_one)
    MyDrawingViewOne viewOne;
    @BindView(R.id.view_two)
    MyDrawingViewTwo viewTwo;
    @BindView(R.id.view_text)
    MyDrawingViewText viewText;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_drawing_articles;
    }

    @Override
    protected void initData() {
        viewOne.setVisibility(View.VISIBLE);
        viewTwo.setVisibility(View.GONE);
        viewText.setVisibility(View.GONE);
    }
}
