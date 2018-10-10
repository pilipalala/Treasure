package com.wyj.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import com.wyj.mvp.entity.douban.DouBanBook;
import com.wyj.mvp.manager.DataManager;
import com.wyj.mvp.view.BookView;
import com.wyj.mvp.view.IView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/**
 * @author wangyujie
 * @date 2018/9/28.10:46
 * @describe 添加描述
 */
public class BookPresenter implements Presenter {
    private final Context mContext;
    private DataManager mDataManager;
    private BookView mBookView;
    private DouBanBook mBook;
    //CompositeSubscription

    public BookPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        mDataManager = new DataManager(mContext);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(IView view) {
        mBookView = (BookView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getSearchBook(String name, String tag, int start, int count) {
        mDataManager.getSearchBooks(name, tag, start, count)
                .subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                .subscribe(new Observer<DouBanBook>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(DouBanBook book) {
                        mBook = book;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mBookView.onError("请求失败！！" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //所有事件都完成，可以做些操作。。。
                        if (mBook != null) {
                            mBookView.onSuccess(mBook);
                        }
                    }
                });

    }
}
