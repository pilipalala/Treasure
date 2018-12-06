package com.wyj.wanandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.wyj.dagger.component.ActivityComponent;
import com.wyj.dagger.component.AppComponent;
import com.wyj.dagger.component.DaggerActivityComponent;
import com.wyj.treasure.MyApplication;
import com.wyj.treasure.utils.ActivityCollector;
import com.wyj.treasure.utils.StatusBarUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author wangyujie
 * @date 2018/11/26.15:15
 * @describe 添加描述
 */
public abstract class MVPBaseActivity<P extends BaseActivityPresenter> extends SupportActivity implements LifecycleProvider<ActivityEvent> {
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    private Context mContext;
    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        mContext = this;
        ActivityCollector.addActivity(this);

        setContentView(getContentViewID());
        ButterKnife.bind(this);
        setStatusBar();
        inject(getActivityComponent());
        initPresenter();
        initData();
        initView(savedInstanceState);
        initListener();
        initCompleted();

    }

    protected abstract int getContentViewID();

    protected abstract void initData();

    protected abstract void initView(@Nullable Bundle savedInstanceState);

    protected abstract void initListener();

    protected abstract void initCompleted();

    protected void setStatusBar() {
        StatusBarUtil.immersive(this);
    }

    protected void inject(ActivityComponent activityComponent) {
        // dagger注入
    }

    public ActivityComponent getActivityComponent() {
        AppComponent appComponent = ((MyApplication) getApplication()).getAppComponent();
        ActivityComponent build = DaggerActivityComponent.builder()//DaggerMainComponent 与 ActivityModule的实例绑定
                .appComponent(appComponent)
                .build();
        return build;
    }

    private void initPresenter() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        ActivityCollector.removeActivity(this);
    }

    @NonNull
    @Override
    public Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }
}
