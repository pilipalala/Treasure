package com.wyj.mvp.service.retrofit;
import com.wyj.mvp.SimpleLoadDialog;
import com.wyj.treasure.utils.ActivityCollector;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author wangyujie
 * @date 2018/9/20.14:37
 * @describe 添加描述
 */
public abstract class BaseSubscriber<T> implements ProgressCancelListener, Subscriber<T> {
    private SimpleLoadDialog dialogHandler;
    private Subscription mSubscription;

    public BaseSubscriber() {
        dialogHandler = new SimpleLoadDialog(ActivityCollector.getTopActivity(), this, true);
    }

    @Override
    public void onSubscribe(Subscription s) {
        mSubscription = s;
    }

    @Override
    public void onNext(T value) {
        _onNext(value);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (false) { //这里自行替换判断网络的代码
            _onError("网络不可用");
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试..."+e.getMessage());
        }
        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.show();
        }
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog() {
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.dismiss();
            ;
            dialogHandler = null;
        }
    }

    @Override
    public void onCancelProgress() {
        if (mSubscription != null) {
            mSubscription.cancel();
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
