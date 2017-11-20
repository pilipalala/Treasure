package com.wyj.treasure.rxjava;

import com.wyj.treasure.rxjava.entity.LoginRequest;
import com.wyj.treasure.rxjava.entity.LoginResponse;
import com.wyj.treasure.rxjava.entity.RegisterRequest;
import com.wyj.treasure.rxjava.entity.RegisterResponse;
import com.wyj.treasure.rxjava.entity.UserBaseInfoRequest;
import com.wyj.treasure.rxjava.entity.UserBaseInfoResponse;
import com.wyj.treasure.rxjava.entity.UserExtraInfoRequest;
import com.wyj.treasure.rxjava.entity.UserExtraInfoResponse;
import com.wyj.treasure.rxjava.entity.UserInfo;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_rx_java);
    }

    @Override
    protected void initData() {
//        initRxJava();
//        RxJavaForThread();
//        RxJava();
//        RxJavaForMap();
        RxJavaForZip();
    }

    private void initRxJava() {
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.i("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                LogUtil.i("value" + value);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i("error" + e);
            }

            @Override
            public void onComplete() {
                LogUtil.i("onComplete");
            }
        };
        //建立连接
        observable.subscribe(observer);
//         只有当上游和下游建立连接之后, 上游才会开始发送事件. 也就是调用了subscribe() 方法之后才开始发送事件.
    }

    public void RxJava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            /**
             *
             * @param emitter 1、发射器的意思这个就是用来发出事件的，
             *                它可以发出三种类型的事件，
             *                通过调用emitter的onNext(T value)、onComplete()
             *                和onError(Throwable error)就可以分别发出next事件、
             *                complete事件和error事件。
             *                2、当上游发送了一个onComplete后,
             *                上游onComplete之后的事件将会继续发送,
             *                而下游收到onComplete事件之后将不再继续接收事件.
             *                3、当上游发送了一个onError后,
             *                上游onError之后的事件将继续发送,
             *                而下游收到onError事件之后将不再继续接收事件.
             *                4、上游可以不发送onComplete或onError.
             *                5、最为关键的是onComplete和onError必须唯一并且互斥,
             *                即不能发多个onComplete, 也不能发多个onError,
             *                也不能先发一个onComplete, 然后再发一个onError, 反之亦然
             * @throws Exception
             */
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                LogUtil.i("emit 1");
                emitter.onNext(2);
                LogUtil.i("emit 2");
                emitter.onNext(3);
                LogUtil.i("emit 3");
                emitter.onComplete();
                LogUtil.i("emit complete");
                emitter.onNext(4);
                LogUtil.i("emit 4");
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;
            private int i;

            /**
             * @param d
             * 调用dispose()并不会导致上游不再继续发送事件,
             * 上游会继续发送剩余的事件.
             */
            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.i("onSubscribe");
                mDisposable = d;

            }

            @Override
            public void onNext(Integer value) {
                LogUtil.i("value" + value);
                i++;
                if (i == 2) {
                    LogUtil.i("dispose");
                    mDisposable.dispose();
                    LogUtil.i("isDisposed : " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i("error" + e);
            }

            @Override
            public void onComplete() {
                LogUtil.i("onComplete");
            }
        });
    }

    /**
     *
     */
    public void RxJavaForThread() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LogUtil.i("Observable thread is : " + Thread.currentThread().getName());
                LogUtil.i("emit 1");
                emitter.onNext(1);
            }
        });
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i("Observer thread is :" + Thread.currentThread().getName());
                LogUtil.i("onNext: " + integer);
            }
        };
        /*
        * 1、subscribeOn() 指定的是上游发送事件的线程,
        * 2、observeOn() 指定的是下游接收事件的线程.
        * 3、多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略
        * 4、多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
        * */
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
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


    /**
     * 通过Map, 可以将上游发来的事件转换为任意的类型, 可以是一个Object, 也可以是一个集合
     */
    public void RxJavaForMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtil.i(s);
            }
        });
    }

    /**
     * FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，
     * 然后将它们发射的事件合并后放进一个单独的Observable里.
     */
    public void RxJavaFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                Observable<String> delay = Observable.fromIterable(list).delay(10, TimeUnit.MICROSECONDS);
                return delay;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtil.i(s);
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

    /**
     * concatMap, 它和flatMap的作用几乎一模一样, 只是它的结果是严格按照上游发送的顺序来发送的
     */
    public void RxJavaConcatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                Observable<String> delay = Observable.fromIterable(list).delay(10, TimeUnit.MICROSECONDS);
                return delay;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtil.i(s);
            }
        });
    }

    public void RxJavaForZip() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LogUtil.i("emit 1");
                emitter.onNext(1);


                LogUtil.i("emit 2");
                emitter.onNext(2);

                LogUtil.i("emit 3");
                emitter.onNext(3);

                LogUtil.i("emit 4");
                emitter.onNext(4);

                LogUtil.i("emit complete");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LogUtil.i("emit A");
                emitter.onNext("A");


                LogUtil.i("emit B");
                emitter.onNext("B");


                LogUtil.i("emit C");
                emitter.onNext("C");


                LogUtil.i("emit complete2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable, observable1, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

                LogUtil.i("onSubscribe");
            }

            @Override
            public void onNext(String value) {
                LogUtil.i(value);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i("onError");
            }

            @Override
            public void onComplete() {
                LogUtil.i("onComplete");
            }
        });
    }

    public static void practice1() {
        RxApi rxApi = RetrofitProvider.get().create(RxApi.class);
        Observable<UserBaseInfoResponse> observable1 =
                rxApi.getUserBaseInfo(new UserBaseInfoRequest()).subscribeOn(Schedulers.io());

        Observable<UserExtraInfoResponse> observable2 =
                rxApi.getUserExtraInfo(new UserExtraInfoRequest()).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2,
                new BiFunction<UserBaseInfoResponse, UserExtraInfoResponse, UserInfo>() {
                    @Override
                    public UserInfo apply(UserBaseInfoResponse baseInfo,
                                          UserExtraInfoResponse extraInfo) throws Exception {
                        return new UserInfo(baseInfo, extraInfo);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {
                        //do something;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void RxJavaForFlowable() {
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                LogUtil.i("emit 1");
                emitter.onNext(1);
                LogUtil.i("emit 2");
                emitter.onNext(2);
                LogUtil.i("emit 3");
                emitter.onNext(3);
                LogUtil.i("emit 4");
                emitter.onNext(4);
                LogUtil.i("emit complete");
                emitter.onComplete();


            }
        }, BackpressureStrategy.ERROR);
        Subscriber<Integer> downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                LogUtil.i("Subscription");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.i("onNext" + integer);

            }

            @Override
            public void onError(Throwable t) {
                LogUtil.i("Throwable" + t);
            }

            @Override
            public void onComplete() {
                LogUtil.i("onComplete");
            }
        };
        upstream.subscribe(downstream);
    }
}
