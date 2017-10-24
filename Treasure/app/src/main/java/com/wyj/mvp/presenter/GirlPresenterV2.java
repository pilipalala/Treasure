package com.wyj.mvp.presenter;

import com.wyj.mvp.bean.Girl;
import com.wyj.mvp.model.GirlModeImpV2;
import com.wyj.mvp.model.IGirlModel;
import com.wyj.mvp.view.IGirlView;

import java.util.List;

/**
 * Created by wangyujie
 * on 2017/10/17.17:13
 * TODO
 */

public class GirlPresenterV2 extends BasePresenter<IGirlView> {


    IGirlView mGirlView;
    IGirlModel mGirlModel = new GirlModeImpV2();
    public GirlPresenterV2(IGirlView mGirlView) {
        this.mGirlView = mGirlView;
    }
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
