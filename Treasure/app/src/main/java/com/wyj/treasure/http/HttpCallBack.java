package com.wyj.treasure.http;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by wangyujie
 * Date 2018/1/22
 * Time 20:59
 * TODO
 */

public abstract class HttpCallBack<T> implements EngineCallBack {
    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {

        /*在此添加公用参数*/
        params.put("content_type", "-101");
        params.put("message_cursor", "-1");
        params.put("am_longitude", "117.235031");
        params.put("am_latitude", "31.830801");
        params.put("am_city", "%E5%90%88%E8%82%A5%E5%B8%82");
        params.put("am_loc_time", "1516513301183");
        params.put("min_time", "1516513321");
        params.put("count", "30");
        params.put("screen_width", "1080");
        params.put("double_col_mode", "0");
        params.put("enable_image_comment", "1");
        params.put("local_request_tag", "1516513566096");
        params.put("device_id", "37448271157");
        params.put("ac", "wifi");
        params.put("device_type", "OPPO+R7");
        params.put("language", "zh");
        params.put("device_brand", "OPPO");
        params.put("os_api", "22");
        params.put("os_version", "5.1");
        params.put("uuid", "867664021447151");
        params.put("openudid", "e78ffe4c50967b89");
        params.put("manifest_version_code", "674");
        params.put("mpic", "1");
        params.put("webp", "1");
        params.put("essence", "1");
        params.put("video_cdn_first", "1");
        params.put("fetch_activity", "1");
        onPreExecute();
    }

    private void onPreExecute() {

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        T json =(T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
        onSuccess(json);
    }

    public abstract void onSuccess(T result);
}
