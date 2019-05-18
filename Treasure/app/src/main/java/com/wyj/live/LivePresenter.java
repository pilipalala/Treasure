package com.wyj.live;


import com.wyj.mvp.presenter.BasePresenter;
import com.wyj.mvp.service.retrofit.BaseObserver;

/**
 * @author wangyujie
 * @date 2018/9/28.18:02
 * @describe 添加描述
 */
public class LivePresenter extends BasePresenter<LiveModel, LiveContract.View> implements LiveContract.Presenter {
    @Override
    public LiveModel loadMode() {
        return new LiveModel();
    }

    @Override
    public void getLivePingTai() {
        getIModel().getLivePingTai(new BaseObserver<LivePingTai>() {
            @Override
            protected void _onNext(LivePingTai livePingTai) {
                getIView().onSuccess(livePingTai);
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
