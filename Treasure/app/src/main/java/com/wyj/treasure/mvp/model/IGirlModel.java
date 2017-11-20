package com.wyj.treasure.mvp.model;

import com.wyj.treasure.mvp.bean.Girl;

import java.util.List;

/**
 * Created by wangyujie
 * on 2017/10/17.16:01
 * TODO
 */

public interface IGirlModel {

    /**
     * 加载数据
     *
     * @param loadListener
     */
    void loadGirl(GirlOnLoadListener loadListener);

    /**
     * 加载完成的监听
     */
    interface GirlOnLoadListener {
        void onComplete(List<Girl> girls);
    }
}
