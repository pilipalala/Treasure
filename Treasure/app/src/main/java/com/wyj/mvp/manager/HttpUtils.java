package com.wyj.mvp.manager;

import android.annotation.SuppressLint;

import com.wyj.mvp.entity.MoveBean;
import com.wyj.mvp.service.Api;
import com.wyj.mvp.service.retrofit.ApiException;
import com.wyj.mvp.service.retrofit.BaseObserver;
import com.wyj.mvp.service.retrofit.BaseSubscriber;
import com.wyj.mvp.service.retrofit.HttpMoveResult;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyujie
 * @date 2018/9/19.16:02
 * @describe https://gank.io/post/56e80c2c677659311bed9841
 */
public class HttpUtils {
    @SuppressLint("CheckResult")
    public static void getTopMovie(String url, int start, int count, BaseSubscriber<List<MoveBean.SubjectsBean>> observer) {
        Api.getDefault(url)
                .getTopMove(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<List<MoveBean.SubjectsBean>>())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        observer.showProgressDialog();
                    }
                })
                .subscribe(observer);
    }

    private static class HttpResultFunc<T> implements Function<HttpMoveResult<T>, T> {
        @Override
        public T apply(HttpMoveResult<T> httpResult) throws Exception {
            if (httpResult.getCount() == 0) {
                throw new ApiException(httpResult.getTitle());
            }
            return httpResult.getSubjects();
        }
    }

}
