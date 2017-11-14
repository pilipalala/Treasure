package com.wyj.treasure.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;

import com.wyj.treasure.R;
import com.wyj.treasure.permission.PermissionHelper;
import com.wyj.treasure.permission.PermissionSuccess;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

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
    Transition fade;
    /*滑动效果*/
    Transition slide;
    public final int PERMISSION_REQUEST_CODE = 0x110;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
            fade = TransitionInflater.from(this).inflateTransition(R.transition.fade);
            slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
            //退出时使用
            getWindow().setExitTransition(fade);
            //第一次进入时使用
            getWindow().setEnterTransition(explode);
            //再次进入时使用
            getWindow().setReenterTransition(fade);
            //如果想让前者完全运行完后者再进来
            getWindow().setAllowEnterTransitionOverlap(false);
            getWindow().setAllowReturnTransitionOverlap(false);

            getWindow().setSharedElementExitTransition(explode);
            getWindow().setSharedElementEnterTransition(explode);
            getWindow().setSharedElementReenterTransition(explode);
            getWindow().setSharedElementReturnTransition(explode);
        }
        try {
            initView();
            initData();
        } catch (Exception e) {
            ToastUtil.show("出现异常");
            LogUtil.e(e.toString());

        }
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


    /**
     * @param context
     */
    public void requestPermission(Activity context) {

        PermissionHelper.with(this)
                .requestCode(PERMISSION_REQUEST_CODE)
                .requestPermission(Manifest.permission.CALL_PHONE)
                .request();

    }

    @PermissionSuccess(requestCode = PERMISSION_REQUEST_CODE)
    public void callPhone() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
