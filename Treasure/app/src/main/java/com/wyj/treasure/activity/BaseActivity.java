package com.wyj.treasure.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.adapter.BaseActivityAdapter;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.treasure.permission.PermissionFail;
import com.wyj.treasure.permission.PermissionHelper;
import com.wyj.treasure.permission.PermissionSuccess;
import com.wyj.treasure.utils.ActivityCollector;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/7/31
 * Time 21:08
 * TODO
 */

public abstract class BaseActivity extends AppCompatActivity {
    public final int PERMISSION_REQUEST_CODE = 0x110;
    public final int DEFATE_VIEW = -1;
    public final int EMPTY_VIEW = 0;
    /*爆炸效果*/
    Transition explode;
    /*淡化效果*/
    Transition fade;
    /*滑动效果*/
    Transition slide;
    private BaseActivityAdapter adapter;
    protected List<ItemInfo> mData;
    protected Context mContext;
    private boolean startAnimation = true;
    private PermissionListener mListener;
    private Activity topActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        if (isStartAnimation()) {
            startAnimation();
        }
        mContext = this;
        if (getContentViewID() != EMPTY_VIEW) {
            setContentView(contentView(getContentViewID() == DEFATE_VIEW ? R.layout.activity_drawer_layout_main : getContentViewID()));
        } else {
            setContentView(contentView());
        }
        ButterKnife.bind(this);
        try {
//            initView(savedInstanceState);
            initData();
        } catch (Exception e) {
            ToastUtil.show("出现异常");
            LogUtil.e(e.toString());
        }
    }

    private void startAnimation() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);//启用transition api
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
    }


    protected int contentView() {
        return 0;
    }

    private void initRecycleView() {
        mData = new ArrayList<>();
        List<ItemInfo> listData = getListData();
        if (listData != null && listData.size() > 0) {
            mBaseRv.setVisibility(View.VISIBLE);
            adapter = new BaseActivityAdapter(this);
            adapter.setData(listData);
            mBaseRv.setAdapter(adapter);
            mBaseRv.setLayoutManager(new LinearLayoutManager(this));
            adapter.setOnClickListener(position -> {
                Intent intent = new Intent(BaseActivity.this, listData.get(position).getClz());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(BaseActivity.this).toBundle());
                } else {
                    startActivity(intent);
                }
            });
        } else {
            mBaseRv.setVisibility(View.GONE);
        }
    }
    protected void setStatusBar() {
    }
    private RecyclerView mBaseRv;
    private FrameLayout mFlContent;
    public Toolbar mToolbar;
    private TextView mRightTitle;

    private View contentView(@LayoutRes int layoutResID) {
        /*Toolbar布局中不能有LinearLayout等布局*/
        View baseView = LayoutInflater.from(mContext).inflate(R.layout.activity_base, null);
        mFlContent = (FrameLayout) baseView.findViewById(R.id.fl_content);
        mBaseRv = (RecyclerView) baseView.findViewById(R.id.rv_base);
        mToolbar = (Toolbar) baseView.findViewById(R.id.toolbar);
        mRightTitle = (TextView) baseView.findViewById(R.id.tv_right_title);
        View contentView = LayoutInflater.from(this).inflate(layoutResID, mFlContent, false);
        mFlContent.removeAllViews();
        mFlContent.addView(contentView);
        initRecycleView();
        setBack();
        return baseView;
    }

    public void setBack() {
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    public void setRightTitle(String title, View.OnClickListener listener) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        mRightTitle.setVisibility(View.VISIBLE);
        mRightTitle.setText(title);
        mRightTitle.setOnClickListener(listener);
    }


    /**
     * 布局
     *
     * @return
     */
    protected abstract int getContentViewID();

    protected List<ItemInfo> getListData() {
        return mData;
    }


    /**
     * 加载数据
     */
    protected abstract void initData();
//    protected abstract void initView(@Nullable Bundle savedInstanceState);

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

    @PermissionFail(requestCode = PERMISSION_REQUEST_CODE)
    public void callPhoneFail() {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.requestPermissionsResult(this, requestCode, permissions);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    List<String> unRationaleList = new ArrayList<String>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);

                            //返回的是true,代表的是提示，表示我们可以动态去申请权限。
                            //返回的是false代表的是禁止，我们就不可以去动态申请权限，此时则只能去通过用户，去手动更改权限
                            //如果设置中权限是禁止的则返回false;如果是提示咋返回的是true
                            boolean isRationale = ActivityCompat.shouldShowRequestPermissionRationale(topActivity,
                                    permission);
                            LogUtil.d("BaseActivity_275-->requestRuntimePermission: " + isRationale);
                            if (!isRationale) {//被禁止不再提示的权限
                                unRationaleList.add(permission);
                            }
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions, unRationaleList);
                    }
                }
                break;
        }
    }

    public interface PermissionListener {
        void onGranted();

        void onDenied(List<String> deniedPermissions, List<String> unRationalePermissions);
    }

    /**
     * 请求6.0权限
     *
     * @param permissions
     * @param listener
     */
    public void requestRuntimePermission(String[] permissions, PermissionListener listener) {
        topActivity = ActivityCollector.getTopActivity();
        if (topActivity == null) {
            return;
        }

        mListener = listener;
        //拒绝权限的集合
        ArrayList<String> permissionList = new ArrayList<String>();

        for (String permission : permissions) {
            //权限有三种状态（1、允许  2、提示  3、禁止）
            //有些手机<金立> 设置中明明是提示或者禁止 但是用 checkSelfPermission检测的 却是 PERMISSION_GRANTED
            //checkSelfPermission 检查是否拥有这个权限
            if (ContextCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                // 2、提示  3、禁止
                permissionList.add(permission);
                LogUtil.d("BaseActivity_275-->requestRuntimePermission: 没有权限");
            }

        }
        if (!permissionList.isEmpty()) {
            //requestPermissions 请求权限，一般会弹出一个系统对话框，询问用户是否开启这个权限。
            ActivityCompat.requestPermissions(topActivity,
                    permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public boolean isStartAnimation() {
        return startAnimation;
    }

}
