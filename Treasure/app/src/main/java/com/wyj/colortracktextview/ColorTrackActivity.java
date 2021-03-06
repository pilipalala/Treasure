package com.wyj.colortracktextview;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wyj.colortracktextview.trackindicator.IndicatorAdapter;
import com.wyj.colortracktextview.trackindicator.TrackIndicatorView;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ColorTrackActivity extends BaseActivity {

    @BindView(R.id.indicator_view)
    TrackIndicatorView indicatorView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private String[] items = {"直播", "Jpg'h", "视频", "图片", "段子", "精华", "同城", "游戏"};
    private List<ColorTrackTextView> mIndicators;
    /**
     * 是否滑动
     */
    private boolean isSettling = false;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_color_track;

    }

    @Override
    protected List<ItemInfo> getListData() {
        return null;
    }

    @Override
    protected void initData() {
        mIndicators = new ArrayList<>();
//        initIndicator();
        initViewPager();
        indicatorView.setAdapter(new IndicatorAdapter<ColorTrackTextView>() {
            @Override
            public int getCount() {
                return items.length;

            }

            @Override
            public void setResetIndicator(ColorTrackTextView view) {
                view.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                view.setCurrentProgress(0);
            }

            @Override
            public void setHightLightIndicator(ColorTrackTextView view) {
                view.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                view.setCurrentProgress(1);

            }

            @Override
            public View getBottomTrackView() {
                View view = new View(ColorTrackActivity.this);
                view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 3));
                view.setBackgroundColor(Color.RED);
                return view;
            }

            @Override
            public ColorTrackTextView getView(int position, ViewGroup parent) {
//                TextView trackTextView = new TextView(ColorTrackActivity.this);
                ColorTrackTextView trackTextView = new ColorTrackTextView(ColorTrackActivity.this);
                trackTextView.setTextSize(20);
                trackTextView.setGravity(Gravity.CENTER);
                trackTextView.setText(items[position]);
                mIndicators.add(trackTextView);
                return trackTextView;
            }
        }, viewPager, false);

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
