package com.wyj.treasure.rxjava;

import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.rxjava.entity.LoginRequest;
import com.wyj.treasure.rxjava.entity.LoginResponse;
import com.wyj.treasure.rxjava.entity.RegisterRequest;
import com.wyj.treasure.rxjava.entity.RegisterResponse;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    @Override
    protected int initView() {
        return R.layout.activity_rx_java;
    }

    @Override
    protected void initData() {
        setTitle("RxJava操作符");
    }


    RxApi rxApi = RetrofitProvider.get().create(RxApi.class);

    public void RxJavaForLogin() {

        rxApi.login(new LoginRequest())
                .subscribeOn(Schedulers.io())//在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())//回到主线程去处理请求结果
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show("登录失败");
                    }

                    @Override
                    public void onComplete() {
                        ToastUtil.show("登录成功");
                    }
                });
        rxApi.login(new LoginRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        ToastUtil.show("登录成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.show("登录失败");
                    }
                });
    }


    public void RxJavaForRegister() {
        rxApi.register(new RegisterRequest())//发起注册请求
                .subscribeOn(Schedulers.io())//在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())//回到主线程去处理请求注册结果
                .doOnNext(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse registerResponse) throws Exception {
                        //先根据注册的响应结果去做一些操作
                    }
                })
                .observeOn(Schedulers.io())//回到IO线程去发起登录请求
                .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
                    @Override
                    public ObservableSource<LoginResponse> apply(RegisterResponse registerResponse) throws Exception {
                        return rxApi.login(new LoginRequest());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        ToastUtil.show("登录成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.show("登录失败");
                    }
                });
    }


    @OnClick({R.id.btn_create, R.id.btn_for_thread, R.id.btn_for_from, R.id.btn_for_interval,
            R.id.btn_for_just, R.id.btn_for_range, R.id.btn_for_filter, R.id.btn_for_map,
            R.id.btn_for_flatMap, R.id.btn_for_ConcatMap, R.id.btn_for_zip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                RxUtils.createObservable();
                RxUtils.createPrint();
                break;
            case R.id.btn_for_thread:
                RxUtils.RxJavaForThread();
                break;
            case R.id.btn_for_map:
                RxUtils.RxJavaForMap();
                break;
            case R.id.btn_for_flatMap:
                RxUtils.RxJavaFlatMap();
                break;
            case R.id.btn_for_ConcatMap:
                RxUtils.RxJavaConcatMap();
                break;
            case R.id.btn_for_zip:
                RxUtils.RxJavaForZip();
                break;
            case R.id.btn_for_from:
                RxUtils.from();
                break;
            case R.id.btn_for_interval:
                RxUtils.interval();
                break;
            case R.id.btn_for_just:
                RxUtils.just();
                break;
            case R.id.btn_for_range:
                RxUtils.range();
                break;
            case R.id.btn_for_filter:
                RxUtils.filter();
                break;
        }
    }
}
