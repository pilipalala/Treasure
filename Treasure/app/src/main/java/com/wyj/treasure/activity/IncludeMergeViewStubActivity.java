package com.wyj.treasure.activity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IncludeMergeViewStubActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    LinearLayout llTitle;
    @BindView(R.id.viewStub)
    ViewStub viewStub;
    @BindView(R.id.btn_vs_showView)
    Button btnVsShowView;
    @BindView(R.id.btn_vs_changeHint)
    Button btnVsChangeHint;
    @BindView(R.id.btn_vs_hideView)
    Button btnVsHideView;
    private String TAG = "WWWWWWWWWW";
    private TextView hintText;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_include_merge_view_stub);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(v -> finish());
        Log.e(TAG, "viewstub: " + findViewById(R.id.viewStub));
        Log.e(TAG, "layout: " + findViewById(R.id.viewStub_new));


    }


    /*@OnClick(R.id.btn)
    public void onViewClicked() {
        View layoutView;
        layoutView = viewStub.inflate();

        Log.e(TAG, "mViewStub: " + viewStub);
        // ViewStub在visible/inflated后会被移除，所以此处为null
        Log.e(TAG, "viewstub: " + findViewById(R.id.viewStub));
//                layoutView = findViewById(R.id.act_layout_viewstub_new);
        Log.e(TAG, "layoutView equals finviewbyid(layout): " +
                layoutView.equals(findViewById(R.id.viewStub_new)));
        Log.e(TAG, "layout: " + layoutView);

        if (layoutView != null) {
            // layoutView的root view id 是mViewStub inflatedId指定的ID
            if (layoutView.getId() == R.id.viewStub_new) {
                Log.e(TAG, "layout root id is act_layout_viewstub_new");
            } *//*else if (layoutView.getId() == R.id.layout_viewstub_old) {
                Log.e(TAG, "layout root id is layout_viewstub_old");
            }*//* else {
                Log.e(TAG, "layout root id is anyone : " + layoutView.getId());
            }

            // layoutView的root view布局 和mViewStub的布局保持一致
            int width = layoutView.getLayoutParams().width;
            if (width == ViewGroup.LayoutParams.MATCH_PARENT) {
                Log.e(TAG, "layout width is MATCH_PARENT");
            } else if (width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                Log.e(TAG, "layout width is WRAP_CONTENT");
            } else {
                Log.e(TAG, "layout width is anyone : " + width);
            }
        }

    }*/


    @OnClick({R.id.btn_vs_showView, R.id.btn_vs_changeHint, R.id.btn_vs_hideView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_vs_showView:
                //inflate 方法只能被调用一次，因为调用后viewStub对象就被移除了视图树；
                // 所以，如果此时再次点击显示按钮，就会崩溃，错误信息：ViewStub must have a non-null ViewGroup viewParent；
                // 所以使用try catch ,当此处发现exception 的时候，在catch中使用setVisibility()重新显示
                try {
                    View vsContent = viewStub.inflate();
                    hintText = (TextView) vsContent.findViewById(R.id.tv_vsContent);
                } catch (Exception e) {
                    viewStub.setVisibility(View.VISIBLE);
                } finally {
                    hintText.setText("没有相关数据，请刷新");
                }
                break;
            case R.id.btn_vs_changeHint:
                LinearLayout vsLayout = (LinearLayout) findViewById(R.id.viewStub_new);
                TextView textView = (TextView) vsLayout.findViewById(R.id.tv_vsContent);
                textView.setText("网络异常，无法刷新，请检查网络");
                break;
            case R.id.btn_vs_hideView:
                viewStub.setVisibility(View.INVISIBLE);

                break;
        }
    }
}
