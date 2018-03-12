package com.wyj.treasure.mvp.model;

import android.os.Build;

import com.wyj.treasure.mvp.bean.PhoneInfo;

/**
 * Created by wangyujie
 * Date 2018/3/10
 * Time 21:51
 * TODO
 */

public class PhoneInfoBizIml implements PhoneInfoBiz {
    @Override
    public void getPhoneInfo(GetPhoneInfoCallback getPhoneInfoCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PhoneInfo phoneInfoBiz = new PhoneInfo();
                phoneInfoBiz.setTime(System.currentTimeMillis()+"");
                phoneInfoBiz.setMobileType(Build.MODEL);
                phoneInfoBiz.setMobileVer(Build.VERSION.RELEASE);
                getPhoneInfoCallback.onGetPhoneInfo(phoneInfoBiz);
            }
        }).start();
    }
}
