package com.wyj.mvp.view;

import com.wyj.mvp.entity.girl.Girl;

import java.util.List;

/**
 * Created by wangyujie
 * on 2017/10/17.16:06
 * TODO
 */

public interface IGirlView {
    /**
     * 加载进度条
     */
    void showLoading();

    /**
     * 显示girl
     */
    void showGirls(List<Girl> girls);

}
