package com.wyj.treasure.rxjava;


import android.util.Log;

import com.wyj.treasure.rxjava.entity.UserBaseInfoRequest;
import com.wyj.treasure.rxjava.entity.UserBaseInfoResponse;
import com.wyj.treasure.rxjava.entity.UserExtraInfoRequest;
import com.wyj.treasure.rxjava.entity.UserExtraInfoResponse;
import com.wyj.treasure.rxjava.entity.UserInfo;
import com.wyj.treasure.utils.LogUtil;

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
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * @author wangyujie
 * @date 2018/8/15.17:01
 * @describe RxJava 操作符
 */
public class RxUtils {
    /**
     * 其他操作符
     * sample操作符, 简单做个介绍, 这个操作符每隔指定的时间就从上游中取出一个事件发送给下游.
     * */


    /**
     * create 第一种方式
     */
    public static void createObservable() {
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
                LogUtil.i("onError" + e);
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

    /**
     * create 第二种方式
     */
    public static void createPrint() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            /**
             *
             * @param emitter 1、发射器的意思这个就是用来发出事件的，
             *                它可以发出三种类型的事件，
             *                通过调用emitter的onNext(T value)、onComplete()
             *                和onError(Throwable onError)就可以分别发出next事件、
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
                LogUtil.i("onError" + e);
            }

            @Override
            public void onComplete() {
                LogUtil.i("onComplete");
            }
        });
    }

    /**
     * 使用在被观察者 返回的对象  一般都是数值类型
     */
    public static void from() {
        Integer[] items = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Observable.fromArray(items)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i("from" + integer);
                    }
                });
    }

    /**
     * 间隔
     * 指定某一时刻进行数据发送
     * 从0开始, 每隔指定的时间就把数字加1并发送出来
     */
    public static void interval() {
        Observable.interval(0, 1, TimeUnit.SECONDS)//每隔一秒 发送一次数据
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtil.i("interval" + aLong);
                    }
                });
    }

    /**
     * 处理数组集合
     */
    public static void just() {
        Integer[] items1 = {1, 2, 3, 4, 5, 6};
        Integer[] items2 = {3, 5, 2, 7, 8, 9};
        Observable.just(items1, items2)
                .subscribe(new Observer<Integer[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer[] value) {
                        for (Integer integer : value) {
                            LogUtil.e("just--->" + "onNext: " + integer);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("just--->" + "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e("just--->" + "onComplete: ");

                    }
                });
    }

    /**
     * 使用范围数据，指定输出数据的范围
     */
    public static void range() {
        Observable.range(1, 50)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i("range--->" + integer);
                    }
                });
    }

    /**
     * 使用过滤功能
     */
    public static void filter() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return 0 < 5;
                    }
                }).observeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i("filter-->" + integer);
                    }
                });
    }

    /**
     * 切换线程
     */
    public static void RxJavaForThread() {
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
         *
         * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
         * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
         * Schedulers.newThread() 代表一个常规的新线程
         * AndroidSchedulers.mainThread() 代表Android的主线程

         * */
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    /**
     * 通过Map, 可以将上游发来的事件转换为任意的类型, 可以是一个Object, 也可以是一个集合
     * 它的作用就是对上游发送的每一个事件应用一个函数, 使得每一个事件都 按照指定 的函数去变化
     */
    public static void RxJavaForMap() {
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
     * FlatMap 将一个发送事件的上游Observable变换为多个发送事件的Observables，
     * 然后将它们发射的事件合并后放进一个单独的Observable里.
     */
    public static void RxJavaFlatMap() {
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

    /**
     * concatMap, 它和flatMap的作用几乎一模一样, 只是它的结果是严格按照上游发送的顺序来发送的
     */
    public static void RxJavaConcatMap() {
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

    /**
     * 组合的过程是分别从 两根水管里各取出一个事件 来进行组合, 并且一个事件只能被使用一次, 组合的顺序是严格
     * 按照事件发送的顺利 来进行的, 也就是说不会出现圆形1 事件和三角形B 事件进行合并,
     * 也不可能出现圆形2 和三角形A 进行合并的情况.
     * 最终下游收到的事件数量 是和上游中发送事件最少的那一根水管的事件数量 相同. 这个也很好理解,
     * 因为是从每一根水管 里取一个事件来进行合并, 最少的 那个肯定就最先取完 , 这个时候其他的水管尽管还有事件 ,
     * 但是已经没有足够的事件来组合了, 因此下游就不会收到剩余的事件了.
     * <p>
     * 两根水管要是运行在同一个线程里， 执行代码会有先后顺序
     */
    public static void RxJavaForZip() {
        /**
         * 比如一个界面需要展示用户的一些信息, 而这些信息分别要从两个服务器接口中获取,
         * 而只有当两个都获取到了之后才能进行展示, 这个时候就可以用Zip了:
         * */
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
                LogUtil.i("RxJavaForZip--->" + value);
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

    /**
     * Flowable中 buffersize 大小 为 128
     */
    public static void RxJavaForFlowable() {
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
                /**
                 * request当做是一种能力, 当成下游处理事件的能力, 下游能处理几个就告诉上游我要几个
                 * 只有当下游调用request时, 才从水缸里取出事件发给下游
                 * */
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

    /**
     * 上游每发送一个next事件之后，requested就减一
     * 注意是next事件，complete和error事件不会消耗requested，当减到0时，则代表下游没有处理能力了，
     * 这个时候你如果继续发送事件，会发生什么后果呢？当然是MissingBackpressureException啦
     * <p>
     * 当上下游在同一个线程中的时候，在下游调用request(n)就会直接改变上游中的requested的值，
     * 多次调用便会叠加这个值，而上游每发送一个事件之后便会去减少这个值，
     * 当这个值减少至0的时候，继续发送事件便会抛异常了。
     */
    private static Subscription mSubscription;

    public static void RxJavaForRequested() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                LogUtil.d("before emit, requested = " + emitter.requested());

                LogUtil.d("emit 1");
                emitter.onNext(1);
                LogUtil.d("after emit 1, requested = " + emitter.requested());

                LogUtil.d("emit 2");
                emitter.onNext(2);
                LogUtil.d("after emit 2, requested = " + emitter.requested());

                LogUtil.d("emit 3");
                emitter.onNext(3);
                LogUtil.d("after emit 3, requested = " + emitter.requested());

                LogUtil.d("emit complete");
                emitter.onComplete();

                LogUtil.d("after emit complete, requested = " + emitter.requested());

            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        LogUtil.d("onSubscribe");
                        mSubscription = s;
                        s.request(10);
//                        s.request(100); //再给我一百个！ 这时候emitter.requested() = 110
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.d("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtil.d("onError: " + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d("onComplete");
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
}
