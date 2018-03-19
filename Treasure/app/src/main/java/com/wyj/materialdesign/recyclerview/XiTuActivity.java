package com.wyj.materialdesign.recyclerview;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wyj.treasure.R;
import com.wyj.treasure.adapter.FragmentViewPagerAdapter;
import com.wyj.treasure.fragment.XiTuFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class XiTuActivity extends AppCompatActivity {
    @BindView(R.id.head_iv)
    ImageView headIv;
    @BindView(R.id.one)
    LinearLayout one;
    @BindView(R.id.two)
    LinearLayout two;
    @BindView(R.id.three)
    LinearLayout three;
    @BindView(R.id.head_layout)
    LinearLayout headLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.main_vp_container)
    ViewPager mainVpContainer;
    @BindView(R.id.root_layout)
    CoordinatorLayout rootLayout;
    @BindView(R.id.nestedscrollview)
    NestedScrollView nestedscrollview;
    private ArrayList<Fragment> fragments;
    private String[] tabTitles = new String[]{"", "分享", "收藏", "关注", "关注者"};
    private GestureDetector detector;
    private CollapsingToolbarLayoutState state;
    private boolean isClose = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xi_tu);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {

        /*2、实例化手势识别器*/
        detector = new GestureDetector(XiTuActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(XiTuActivity.this, "长按", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(XiTuActivity.this, "双击", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }

            /**
             * @param e1
             * @param e2
             * @param distanceX 在X轴滑动了的距离
             * @param distanceY 在Y轴滑动了的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Toast.makeText(XiTuActivity.this, "onScroll", Toast.LENGTH_SHORT).show();
                /**
                 * X 要在X轴平移的距离
                 * Y 要在Y轴移动的距离
                 */
                return true;
            }
        });
        fragments = new ArrayList<>();
        for (int i = 0; i < tabTitles.length; i++) {
            fragments.add(new XiTuFragment(tabTitles[i], 5 + i));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                onBackPressed();
            }
        });
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight() / 2) {
                    collapsingToolbar.setTitle("噼里啪啦");
                } else {
                    collapsingToolbar.setTitle("");
                }

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;
                        isClose = false;
                        Toast.makeText(XiTuActivity.this, "展开", Toast.LENGTH_SHORT).show();
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        state = CollapsingToolbarLayoutState.COLLAPSED;
                        isClose = true;
                        Toast.makeText(XiTuActivity.this, "折叠", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });


        nestedscrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                boolean result = false;
                detector.onTouchEvent(event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isClose) {
                            result = true;
                        }
                        break;
                }
                return true;

            }
        });
        mainVpContainer.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, tabTitles));
        //tablayout和viewpager建立联系为什么不用下面这个方法呢？自己去研究一下，可能收获更多
//        toolbarTab.setupWithViewPager(mainVpContainer);

        mainVpContainer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbarTab));
        toolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mainVpContainer));


        loadBlurAndSetStatusBar();
        Glide.with(this).load(R.mipmap.head).bitmapTransform(new RoundedCornersTransformation(this, 90, 0)).into(headIv);

    }

    /**
     * 设置毛玻璃效果和沉浸栏
     */
    private void loadBlurAndSetStatusBar() {
        Glide.with(this).load(R.mipmap.my).bitmapTransform(new BlurTransformation(this, 100)).into(new SimpleTarget<GlideDrawable>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                headLayout.setBackground(resource);
                rootLayout.setBackground(resource);
            }

        });
        Glide.with(this).load(R.mipmap.my).bitmapTransform(new BlurTransformation(this, 100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        collapsingToolbar.setContentScrim(resource);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return true;
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }
}
