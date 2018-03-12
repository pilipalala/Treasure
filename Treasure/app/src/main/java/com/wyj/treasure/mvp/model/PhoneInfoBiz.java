package com.wyj.treasure.mvp.model;

import com.wyj.treasure.mvp.bean.PhoneInfo;

/**
 * Created by wangyujie
 * Date 2018/3/10
 * Time 21:47
 * TODO
 */

public interface PhoneInfoBiz {
    public interface GetPhoneInfoCallback {

        void onGetPhoneInfo(PhoneInfo phoneInfo);
    }

    void getPhoneInfo(GetPhoneInfoCallback getPhoneInfoCallback);
}
