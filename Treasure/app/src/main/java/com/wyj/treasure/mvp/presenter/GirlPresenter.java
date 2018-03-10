package com.wyj.treasure.mvp.presenter;

import com.wyj.treasure.mvp.bean.Girl;
import com.wyj.treasure.mvp.model.GirlModeImp;
import com.wyj.treasure.mvp.model.IGirlModel;
import com.wyj.treasure.mvp.view.IGirlView;

import java.util.List;

/**
 * Created by wangyujie
 * on 2017/10/17.16:17
 * TODO
 */

public class GirlPresenter {
    /*view interface*/
    IGirlView mGirlView;
    /*model interface*/
    IGirlModel mGirlModel = new GirlModeImp();

    /**
     * 通过构造方法实例化mGirlView
     *
     * @param mGirlView
     */
    public GirlPresenter(IGirlView mGirlView) {
        this.mGirlView = mGirlView;
    }

    /**
     * bind view and model
     */
    public void fetch() {
        /*显示进度条*/
        mGirlView.showLoading();
        /*让model load data */
        if (mGirlModel != null) {
            mGirlModel.loadGirl(new IGirlModel.GirlOnLoadListener() {
                @Override
                public void onComplete(List<Girl> girls) {
                    /*给view显示*/
                    mGirlView.showGirls(girls);
                }
            });
        }


    }


}
