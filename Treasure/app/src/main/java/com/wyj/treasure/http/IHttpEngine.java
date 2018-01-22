package com.wyj.treasure.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by wangyujie
 * Date 2018/1/20
 * Time 20:45
 * TODO 引擎的规范
 */

public interface IHttpEngine {
    //get请求
    void get(Context context,String url, Map<String, Object> parmas, EngineCallBack callBack);

    //post请求
    void post(Context context,String url, Map<String, Object> parmas, EngineCallBack callBack);
    //下载文件
    //上传文件
    //https 添加证书
}
