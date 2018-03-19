package com.wyj.materialdesign.toolbar;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.adapter.FragmentViewPagerAdapter;
import com.wyj.treasure.fragment.XiTuFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabLayoutMainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager tabViewpager;
    private ArrayList<XiTuFragment> fragments;
    private FragmentViewPagerAdapter adapter;
    /**
     * 1、为了ToolBar可以滚动，CoordinatorLayout里面,放一个带有可滚动的View.本例,放的是ViewPager,而ViewPager里面是放了RecylerView的,即是可以滚动的View。
     * <p>
     * 2、CoordinatorLayout中的android:fitsSystemWindows="true"是必写的,不然你的Toolbar会与状态栏重叠在一起；
     * <p>
     * 3、CoordinatorLayout包含的子视图中带有滚动属性的View需要设置app:layout_behavior属性，如ViewPager控件；
     * <p>
     * 4、app:layout_behavior="@string/appbar_scrolling_view_behavior"
     * <p>
     * android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
     * <p>
     * app:popupTheme="@style/ThemeOverlay.AppCompat.Light"
     * <p>
     * 5、为了使得Toolbar有滑动效果，必须做到如下三点:
     * 1、CoordinatorLayout作为布局的父布局容器。
     * 2、给需要滑动的组件(本身没有滑动功能)设置 app:layout_scrollFlags=”scroll|enterAlways” 属性。
     * 3、给滑动的组件(本身有滑动功能)设置app:layout_behavior属性
     */
    private int TotalCount = 0;
    private int gcCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_main);
        ButterKnife.bind(this);
        initView();
    }

    private void count() {
        TotalCount++;
        if (TotalCount >= gcCount) {
            System.gc();
            TotalCount = 0;
        }
    }

    /*注意TabLayout中的tabMode属性可选scrollable或fixed：

    scrollable可以滑动，向左对齐，如今日头条，网易新闻就是scrollable，但是在Tab选项卡较少时会无法填满TabLayout栏。
    fixed则无法滑动，每个选项卡平均分配空间，适合较少Tab选项卡的情况，当选项卡较多时，会出现每个选项卡内容无法显示完整的情况，请大家大家根据情况选择。
    也可在代码中使用以下方法设置。

    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
或
tabLayout.setTabMode(TabLayout.MODE_FIXED);



还有另一个比较类似的属性是app:tabGravity可选fill或centre：

当选项卡很少需要置于中心时，就需要用到centre，可以参考简书手机App首页标题栏“文章”，“专题”两个选项卡的居中效果。
当选项卡需要填满TabLayout布局时，则用到fill。

    */
    private void initView() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count();//提醒回收垃圾
                Snackbar.make(view, "退出？", Snackbar.LENGTH_SHORT).setAction("退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).show();
            }
        });
        fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragments.add(new XiTuFragment("标题" + i, 6 + 1));
        }
        adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments);
        tabViewpager.setAdapter(adapter);
        /*关联viewPager*/
        tabLayout.setupWithViewPager(tabViewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
