package com.wyj.mvp.manager;

import android.content.Context;

import com.wyj.mvp.entity.douban.DouBanBook;
import com.wyj.mvp.service.ApiService;
import com.wyj.mvp.service.DouBanClient;

import io.reactivex.Observable;


/**
 * @author wangyujie
 * @date 2018/9/28.10:28
 * @describe 添加描述
 */
public class DataManager {

    private final ApiService mService;

    public DataManager(Context context) {
        mService = DouBanClient.getInstance(context).getServer();
    }

    public Observable<DouBanBook> getSearchBooks(String name, String tag, int start, int count) {
        return mService.getSearchBook(name, tag, start, count);
    }
}
