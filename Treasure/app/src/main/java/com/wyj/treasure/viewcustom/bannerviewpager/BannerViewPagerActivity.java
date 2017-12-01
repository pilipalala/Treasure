package com.wyj.treasure.viewcustom.bannerviewpager;

import android.view.View;
import android.widget.ImageView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerViewPagerActivity extends BaseActivity implements BannerViewPager.OnItemClickListener {

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
            public View getView(int position, View converView) {
                ImageView view = null;
                if (view == null) {
                    view = new ImageView(BannerViewPagerActivity.this);
                } else {
                    view = (ImageView) converView;
                }
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
        bannerView.setOnBannerItemClickListener(this);
    }

    @Override
    public void onclick(int position) {
        ToastUtil.show("第" + (position+1) + "张");
    }
}