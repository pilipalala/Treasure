package com.wyj.treasure.mvp.presenter;

import com.wyj.treasure.mvp.GetPhoneInfoContract;
import com.wyj.treasure.mvp.bean.PhoneInfo;
import com.wyj.treasure.mvp.model.PhoneInfoBiz;
import com.wyj.treasure.mvp.model.PhoneInfoBizIml;

/**
 * Created by wangyujie
 * Date 2018/3/10
 * Time 21:44
 * TODO
 */

public class GetPhoneInfoPresenter implements GetPhoneInfoContract.Presenter {
    private final GetPhoneInfoContract.view mGetPhoneInfoView;
    private final PhoneInfoBiz bizIml;

    public GetPhoneInfoPresenter(GetPhoneInfoContract.view view) {
        this.mGetPhoneInfoView = view;
        mGetPhoneInfoView.setPresenter(this);
        bizIml = new PhoneInfoBizIml();
    }

    @Override
    public void getTime() {
        mGetPhoneInfoView.showLoading();
        bizIml.getPhoneInfo(new PhoneInfoBiz.GetPhoneInfoCallback() {
            @Override
            public void onGetPhoneInfo(PhoneInfo phoneInfo) {
                mGetPhoneInfoView.setTime(phoneInfo.getTime());
                mGetPhoneInfoView.hideLoading();
            }
        });
    }

    @Override
    public void start() {
        getTime();
    }
}
