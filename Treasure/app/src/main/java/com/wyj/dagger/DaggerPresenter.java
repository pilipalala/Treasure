package com.wyj.dagger;

import com.wyj.treasure.utils.LogUtil;

import javax.inject.Inject;

/**
 * @author yujie
 * @date on 2018/7/27
 * @describe TODO
 **/
public class DaggerPresenter {
    DaggerActivity activity;
    DaggerUser user;
    // 关键字，标明该方法提供依赖对象
    @Inject
    public DaggerPresenter(DaggerActivity activity, DaggerUser user) {
        LogUtil.i("person create!!!");
        this.user = user;
        this.activity = activity;
    }

    public void showUserName() {
        activity.showUserName(user.getName());
    }
}
