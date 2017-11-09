package com.wyj.treasure.fragment;

import com.wyj.mdcustom.splash.SplashActivity;
import com.wyj.mvp.UserLoginActivity;
import com.wyj.rxjava.RxJavaActivity;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.GridViewChoiceActivity;
import com.wyj.treasure.activity.IncludeMergeViewStubActivity;
import com.wyj.treasure.activity.MultipleChoiceActivity;
import com.wyj.treasure.activity.NotifyAttrActivity;
import com.wyj.treasure.activity.ReflectActivity;
import com.wyj.treasure.activity.RegularExpressionActivity;
import com.wyj.treasure.activity.StaggeredGridActivity;
import com.wyj.treasure.activity.SuspensionBarActivity;
import com.wyj.treasure.activity.SwipeDeleteActivity;
import com.wyj.treasure.mdcustom.behaviorcustom.BehaviorActivity;
import com.wyj.treasure.mode.ItemInfo;

public class DashboardFragment extends BaseCardViewFragment {

    @Override
    protected void processData() {
        mData.add(new ItemInfo("Java 反射机制", ReflectActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Recycleview 滑动删除", SwipeDeleteActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("GridView多选", GridViewChoiceActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("RecyclerView悬浮条", SuspensionBarActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("商户端添加属性", NotifyAttrActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("正则表达式", RegularExpressionActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("多选列表", MultipleChoiceActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("瀑布流", StaggeredGridActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("MVP", UserLoginActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("RxJava", RxJavaActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("布局优化", IncludeMergeViewStubActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("自定义Behavior玩转特效--滑动的卡片", BehaviorActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("过渡加载特效", SplashActivity.class, R.mipmap.ic_launcher));
        notifyDataChanged();

    }

}
