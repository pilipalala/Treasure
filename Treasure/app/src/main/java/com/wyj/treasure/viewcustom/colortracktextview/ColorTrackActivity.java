package com.wyj.treasure.viewcustom.colortracktextview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.viewcustom.trackindicator.IndicatorAdapter;
import com.wyj.treasure.viewcustom.trackindicator.TrackIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorTrackActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.indicator_view)
    TrackIndicatorView indicatorView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String[] items = {"直播", "推荐", "视频视频视频", "图片", "段子", "精华"};
    private List<ColorTrackTextView> mIndicators;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_color_track);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        mIndicators = new ArrayList<>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        initIndicator();
        initViewPager();
        indicatorView.setAdapter(new IndicatorAdapter() {
            @Override
            public int getCount() {
                return items.length;

            }

            @Override
            public View getView(int position, ViewGroup parent) {
                ColorTrackTextView trackTextView = new ColorTrackTextView(ColorTrackActivity.this);
                trackTextView.setTextSize(20);
                trackTextView.setText(items[position]);
                mIndicators.add(trackTextView);
                return trackTextView;
            }
        },viewPager);

    }

    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            ColorTrackTextView trackTextView = new ColorTrackTextView(this);
            trackTextView.setTextSize(20);
            trackTextView.setText(items[i]);
            trackTextView.setLayoutParams(layoutParams);
            indicatorView.addView(trackTextView);
            mIndicators.add(trackTextView);
        }
    }

    private void initViewPager() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);

            }

            @Override
            public int getCount() {
                return items.length;
            }

        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ColorTrackTextView left = mIndicators.get(position);
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1 - positionOffset);
                try {
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.LEFE_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
