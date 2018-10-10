package com.wyj.mvp.view;

import com.wyj.mvp.entity.douban.DouBanBook;

/**
 * @author wangyujie
 * @date 2018/9/28.10:39
 * @describe 添加描述
 */
public interface BookView extends IView {
    void onSuccess(DouBanBook mBook);

    void onError(String result);
}
