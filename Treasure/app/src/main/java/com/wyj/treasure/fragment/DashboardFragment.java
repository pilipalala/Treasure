package com.wyj.treasure.fragment;

import com.wyj.SkateMenu.SwipeDeleteActivity;
import com.wyj.bannerviewpager.BannerViewPagerActivity;
import com.wyj.colortracktextview.ColorTrackActivity;
import com.wyj.countdown.CountDownActivity;
import com.wyj.handler.AsyncTaskActivity;
import com.wyj.index.QuickIndexActivity;
import com.wyj.materialdesign.MaterialDesignActivity;
import com.wyj.touch.TouchDemoActivity;
import com.wyj.treasure.NavigationBarActivity;
import com.wyj.treasure.PxDpActivity;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.GridViewChoiceActivity;
import com.wyj.treasure.activity.IncludeMergeViewStubActivity;
import com.wyj.treasure.activity.MultipleChoiceActivity;
import com.wyj.treasure.activity.MyLinearlayoutActivity;
import com.wyj.treasure.activity.ReflectActivity;
import com.wyj.treasure.activity.RegularExpressionActivity;
import com.wyj.treasure.activity.StaggeredGridActivity;
import com.wyj.treasure.activity.SuspensionBarActivity;
import com.wyj.treasure.customcontrol.CustomControlActivity;
import com.wyj.treasure.mdcustom.behaviorcustom.BehaviorActivity;
import com.wyj.treasure.mdcustom.splash.SplashActivity;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.treasure.mvp.UserLoginActivity;
import com.wyj.treasure.popup.PopupWindowActivity;
import com.wyj.treasure.rxjava.RxJavaActivity;
import com.wyj.welcome.WelcomeActivity;

public class DashboardFragment extends BaseCardViewFragment {

    @Override
    protected void processData() {
        mData.add(new ItemInfo("Java 反射机制", ReflectActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Recycleview 滑动删除", SwipeDeleteActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("GridView多选", GridViewChoiceActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("RecyclerView悬浮条", SuspensionBarActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("正则表达式", RegularExpressionActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("多选列表", MultipleChoiceActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("瀑布流", StaggeredGridActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("MVP", UserLoginActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("RxJava", RxJavaActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("布局优化", IncludeMergeViewStubActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("自定义Behavior玩转特效--滑动的卡片", BehaviorActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("过渡加载特效", SplashActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("自动轮播图", BannerViewPagerActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("字体变色", ColorTrackActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("自定义控件三部曲", CustomControlActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("NavigationBar", NavigationBarActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("LinearlayoutActivity", MyLinearlayoutActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("PopupWindow", PopupWindowActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("平行空间效果", WelcomeActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("AsyncTask", AsyncTaskActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("TouchDemoActivity", TouchDemoActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("CountDownActivity", CountDownActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("快速索引", QuickIndexActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("PX和DIP", PxDpActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Materialdesign", MaterialDesignActivity.class, R.mipmap.ic_launcher));
        notifyDataChanged();
    }
}
