package com.wyj.mvp.manager;


import com.wyj.mvp.entity.bus.CarsInfo;
import com.wyj.mvp.entity.bus.LineInfo;
import com.wyj.mvp.entity.bus.LineStationInfo;
import com.wyj.mvp.service.Api;
import com.wyj.mvp.service.ApiService;
import com.wyj.mvp.service.retrofit.BaseObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class BusClientUtils {

    private String apiUrl = "http://xxbs.sh.gov.cn:8080/weixinpage/";
    private final ApiService apiService;

    public BusClientUtils() {
        apiService = Api.getDefault(apiUrl);
    }

    /**
     * 获取路线信息
     */
    public void getLineInfo(String lineName, BaseObserver<LineInfo> observer) {
        apiService.getLineInfo("One", lineName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        observer.showProgressDialog();
                    }
                })
                .subscribe(observer);

    }

    /**
     * 获取站点信息
     */
    public void getLineStation(String lineName, String lineId, BaseObserver<LineStationInfo> observer) {
        apiService.getLineStationInfo("Two", lineName, lineId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        observer.showProgressDialog();
                    }
                })
                .subscribe(observer);
    }

    /**
     * 获取车辆到站信息
     */
    public void getStationCars(String lineName, String lineId, String stopId, boolean direction, BaseObserver<CarsInfo> observer) {
        apiService.getCarInfo("Three", lineName, lineId, stopId, direction ? 0 : 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        observer.showProgressDialog();
                    }
                })
                .subscribe(observer);

    }
}
