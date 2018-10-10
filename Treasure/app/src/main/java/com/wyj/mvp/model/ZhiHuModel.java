package com.wyj.mvp.model;


import com.wyj.greendao.GreenDAOHelp;
import com.wyj.mvp.entity.zhihu.NewsInfo;
import com.wyj.mvp.entity.zhihu.NewsInfoDao;
import com.wyj.mvp.entity.zhihu.ZhiHuDaily;
import com.wyj.mvp.entity.zhihu.ZhiHuDetails;
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
public class ZhiHuModel implements IMode {

    private final ApiService api;
    private Observable<ZhiHuDetails> observable;
    private Observable<ZhiHuDaily> observerNews;
    private NewsInfoDao newsInfoDao;

    public ZhiHuModel() {
        api = ZhiHuClient.getApi();
        newsInfoDao = GreenDAOHelp.getDaoSession().getNewsInfoDao();
    }

    public void getNewsDetails(String id, BaseObserver<ZhiHuDetails> observer) {
        observable = api.getNewsDetails(id);
        api.getNewsDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getStories(BaseObserver<ZhiHuDaily> observer) {
        observerNews = api.getObserverNews();
        observerNews
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> observer.showProgressDialog())
                .subscribe(observer);
    }
    public NewsInfo getNewsInfo(String newsId) {
        return newsInfoDao.queryBuilder().where(NewsInfoDao.Properties.NewsId.eq(newsId)).build().unique();
    }

    public void deleteNews(NewsInfo newsInfo) {
        newsInfoDao.delete(newsInfo);
    }

    public void insert(NewsInfo newsInfo) {
        newsInfoDao.insert(newsInfo);
    }
    @Override
    public void requestCancel() {
        if (observable != null) {
            observable.unsubscribeOn(Schedulers.io());
        }
        if (observerNews != null) {
            observerNews.unsubscribeOn(Schedulers.io());
        }
    }
}
