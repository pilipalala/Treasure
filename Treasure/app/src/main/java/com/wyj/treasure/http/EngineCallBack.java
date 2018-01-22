package com.wyj.treasure.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by wangyujie
 * Date 2018/1/20
 * Time 20:49
 * TODO
 */

public interface EngineCallBack {
    //开始执行，执行之前会回调的方法
    public void onPreExecute(Context context, Map<String, Object> params);

    //错误回调
    public void onError(Exception e);

    //成功回调
    public void onSuccess(String result);

    //默认回调
    public final EngineCallBack ENGINE_CALL_BACK = new EngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }

    };

}
