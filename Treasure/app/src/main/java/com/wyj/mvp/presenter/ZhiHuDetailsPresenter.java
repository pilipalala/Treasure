package com.wyj.mvp.presenter;


import android.text.TextUtils;

import com.wyj.mvp.contract.ZhiHuDetailsContract;
import com.wyj.mvp.entity.zhihu.NewsInfo;
import com.wyj.mvp.entity.zhihu.ZhiHuDetails;
import com.wyj.mvp.model.ZhiHuModel;
import com.wyj.mvp.service.retrofit.BaseObserver;

/**
 * @author wangyujie
 * @date 2018/9/28.18:02
 * @describe 添加描述
 */
public class ZhiHuDetailsPresenter extends BasePresenter<ZhiHuModel, ZhiHuDetailsContract.View> implements ZhiHuDetailsContract.Presenter {



    public ZhiHuDetailsPresenter() {

    }

    @Override
    public ZhiHuModel loadMode() {
        return new ZhiHuModel();
    }

    /**
     * 获取日报详情
     *
     * @param id
     */
    @Override
    public void getNewsDetails(String id) {
        if (TextUtils.isEmpty(id)) {
            getIView().onError("Details id is null");
            return;
        }
        getIModel().getNewsDetails(id, new BaseObserver<ZhiHuDetails>() {
            @Override
            protected void _onNext(ZhiHuDetails zhiHuDetails) {
                if (zhiHuDetails != null) {
                    getIView().onDetailSuccess(zhiHuDetails);
                } else {
                    getIView().onError("Details is null");
                }
            }

            @Override
            protected void _onComplete() {
                getIView().onComplete();
            }

            @Override
            protected void _onError(String message) {
                getIView().onError(message);
            }
        });
    }

    @Override
    public NewsInfo getNewsInfo(String newsId) {
        return getIModel().getNewsInfo(newsId);
    }

    @Override
    public void deleteNews(NewsInfo newsInfo) {
        getIModel().deleteNews(newsInfo);
    }

    @Override
    public void insert(NewsInfo newsInfo) {
        getIModel().insert(newsInfo);
    }
}
