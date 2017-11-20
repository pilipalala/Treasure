package com.wyj.treasure.viewcustom.bannerviewpager;

import android.view.View;
import android.widget.ImageView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerViewPagerActivity extends BaseActivity {

    @BindView(R.id.banner_view)
    BannerView bannerView;

    private Integer[] imageViews = {
            R.mipmap.ai_1, R.mipmap.ai_2,
            R.mipmap.ai_3, R.mipmap.ai_4,
            R.mipmap.ai_5, R.mipmap.ai_6,
            R.mipmap.ai_7, R.mipmap.ai_8,
            R.mipmap.ai_9, R.mipmap.ai_10};
    private String[] bannerDesc = {
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10"};


    @Override
    protected void initView() {
        setContentView(R.layout.activity_banner_view_pager);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position) {
                ImageView view = new ImageView(BannerViewPagerActivity.this);
                view.setImageResource(imageViews[position]);
                return view;
            }

            @Override
            public int getCount() {
                return imageViews.length;

            }

            @Override
            public String getBannerDesc(int mCurrentPosition) {
                return bannerDesc[mCurrentPosition];
            }
        });
        bannerView.startRoll();
    }
}