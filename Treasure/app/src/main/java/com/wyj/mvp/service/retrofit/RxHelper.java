package com.wyj.mvp.service.retrofit;


import android.annotation.SuppressLint;

import com.wyj.treasure.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyujie
 * @date 2018/9/19.15:23
 * @describe 添加描述
 */
public class RxHelper {

    public static <T> ObservableTransformer<HttpResult<T>, T> handleResult() {
        return new ObservableTransformer<HttpResult<T>, T>() {

            @SuppressLint("CheckResult")
            @Override
            public ObservableSource<T> apply(Observable<HttpResult<T>> upstream) {
                return upstream.flatMap(new Function<HttpResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(HttpResult<T> result) throws Exception {
                        LogUtil.e(result.getCode() + "");
                        if (result.getCode() == 0) {
                            return createData(result.getData());
                        } else {
                            return Observable.error(new ApiException(result.getCode()));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());


            }
        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> observableEmitter) throws Exception {
                try {
                    observableEmitter.onNext(data);
                    observableEmitter.onComplete();
                } catch (Exception e) {
                    observableEmitter.onError(e);
                }

            }
        });
    }

}
