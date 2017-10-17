package com.wyj.treasure.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;

import com.wyj.treasure.R;

/**
 * Created by wangyujie
 * Date 2017/7/31
 * Time 21:08
 * TODO
 */

public abstract class BaseActivity extends AppCompatActivity {
    /*爆炸效果*/
    Transition explode;
    /*淡化效果*/
    Transition fade ;
    /*滑动效果*/
    Transition slide ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            explode =TransitionInflater.from(this).inflateTransition(R.transition.explode);
            fade = TransitionInflater.from(this).inflateTransition(R.transition.fade);
            slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
            //退出时使用
            getWindow().setExitTransition(fade);
            //第一次进入时使用
            getWindow().setEnterTransition(explode);
            //再次进入时使用
            getWindow().setReenterTransition(fade);
            /*如果想让前者完全运行完后者再进来*/
            getWindow().setAllowEnterTransitionOverlap(false);
            getWindow().setAllowReturnTransitionOverlap(false);

           /* getWindow().setSharedElementExitTransition(explode);
            getWindow().setSharedElementEnterTransition(explode);
            getWindow().setSharedElementReenterTransition(explode);
            getWindow().setSharedElementReturnTransition(explode);*/
        }
        initView();
        initData();
    }


    /**
     * 布局
     *
     * @return
     */
    protected abstract void initView();


    /**
     * 加载数据
     */
    protected abstract void initData();
}
