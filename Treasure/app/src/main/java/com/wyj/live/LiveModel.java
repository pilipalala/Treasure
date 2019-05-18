package com.wyj.live;


import com.wyj.greendao.GreenDAOHelp;
import com.wyj.mvp.entity.zhihu.NewsInfo;
import com.wyj.mvp.entity.zhihu.NewsInfoDao;
import com.wyj.mvp.entity.zhihu.ZhiHuDaily;
import com.wyj.mvp.entity.zhihu.ZhiHuDetails;
import com.wyj.mvp.model.IMode;
import com.wyj.mvp.service.ApiService;
import com.wyj.mvp.service.ZhiHuClient;
import com.wyj.mvp.service.retrofit.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyujie
 * @date 2018/9/28.18:05
 * @describe 添加描述
 */
public class LiveModel implements IMode {

    private final LiveService api;
    private Observable<LivePingTai> observable;

    public LiveModel() {
        api = LiveClient.getApi();
    }

    public void getLivePingTai(BaseObserver<LivePingTai> observer) {
        observable = api.getLivePingTai();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> observer.showProgressDialog())
                .subscribe(observer);
    }

    public void getStories(BaseObserver<ZhiHuDaily> observer) {
//        observerNews = api.getObserverNews();
//        observerNews
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable -> observer.showProgressDialog())
//                .subscribe(observer);
    }

    @Override
    public void requestCancel() {
        if (observable != null) {
            observable.unsubscribeOn(Schedulers.io());
        }
//        if (observerNews != null) {
//            observerNews.unsubscribeOn(Schedulers.io());
//        }
    }
}
