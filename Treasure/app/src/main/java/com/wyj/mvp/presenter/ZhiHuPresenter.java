package com.wyj.mvp.presenter;


import com.wyj.mvp.contract.ZhiHuContract;
import com.wyj.mvp.entity.zhihu.ZhiHuDaily;
import com.wyj.mvp.model.ZhiHuModel;
import com.wyj.mvp.service.retrofit.BaseObserver;

/**
 * @author wangyujie
 * @date 2018/9/28.18:02
 * @describe 添加描述
 */
public class ZhiHuPresenter extends BasePresenter<ZhiHuModel, ZhiHuContract.View> implements ZhiHuContract.Presenter {
    @Override
    public ZhiHuModel loadMode() {
        return new ZhiHuModel();
    }

    @Override
    public void getStories() {
        getIModel().getStories(new BaseObserver<ZhiHuDaily>() {
            @Override
            protected void _onNext(ZhiHuDaily zhiHuDaily) {
                getIView().onSuccess(zhiHuDaily);
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
}
