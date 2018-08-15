package com.wyj.treasure.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isStartAnimation()) {
            startAnimation();
        }
        mContext = this;
        if (initView() != EMPTY_VIEW) {
            setContentView(contentView(initView() == DEFATE_VIEW ? R.layout.activity_drawer_layout_main : initView()));
        } else {
            setContentView(contentView());
        }
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        try {
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

    private RecyclerView mBaseRv;
    private FrameLayout mFlContent;
    public Toolbar mToolbar;
    private TextView mRightTitle;

    private View contentView(@LayoutRes int layoutResID) {
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
    protected abstract int initView();

    protected List<ItemInfo> getListData() {
        return mData;
    }


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

    @PermissionFail(requestCode = PERMISSION_REQUEST_CODE)
    public void callPhoneFail() {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.requestPermissionsResult(this, requestCode, permissions);
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
