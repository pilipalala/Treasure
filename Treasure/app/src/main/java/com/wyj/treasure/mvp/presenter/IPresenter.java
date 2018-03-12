package com.wyj.treasure.mvp.presenter;

import com.wyj.treasure.mvp.model.IMode;
import com.wyj.treasure.mvp.view.IView;

/**
 * @author wangyujie
 *         on 2018/3/12.9:59
 *         TODO
 */

public interface IPresenter<V extends IView> {
    /**
     * @param view 绑定
     */
    void attachView(V view);


    /**
     * 防止内存的泄漏,清楚presenter与activity之间的绑定
     */
    void detachView();


    /**
     * @return 获取View
     */
    IView getIView();

    IMode getiModel();
}
