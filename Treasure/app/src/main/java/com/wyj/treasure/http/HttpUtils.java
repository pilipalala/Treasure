package com.wyj.treasure.http;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.ArrayMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by wangyujie
 * Date 2018/1/20
 * Time 20:47
 * TODO
 */

public class HttpUtils {

    /*api*/
    private String mUrl;
    /*请求方式*/
    private int mType = GET_TYPE;
    private static final int POST_TYPE = 0x0011;
    private static final int GET_TYPE = 0x0012;
    private Map<String, Object> mParmas;
    private Context mContext;

    public HttpUtils(Context context) {
        mContext = context;
        mParmas = new ArrayMap<>();
    }

    @NonNull
    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    /**
     * get请求
     *
     * @return
     */
    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    /**
     * post请求
     *
     * @return
     */
    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    /**
     * 添加参数
     */
    public HttpUtils addParma(String key, Object value) {
        mParmas.put(key, value);
        return this;
    }

    /**
     * 添加参数
     */
    public HttpUtils addParmas(Map<String, Object> parmas) {
        mParmas.clear();
        mParmas.putAll(parmas);
        return this;
    }

    /**
     * 回调
     */
    public HttpUtils execeute(EngineCallBack callBack) {

        if (callBack == null) {
            callBack = EngineCallBack.ENGINE_CALL_BACK;
        }
        callBack.onPreExecute(mContext, mParmas);
        //判断执行方法
        if (mType == POST_TYPE) {
            post(mUrl, mParmas, callBack);
        } else if (mType == GET_TYPE) {
            get(mUrl, mParmas, callBack);
        }
        return this;
    }

    private static IHttpEngine mHttpEngine = new OkHttpEngine();

    /**
     * 在Application中初始化引擎
     *
     * @param httpEngine
     */
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    /**
     * 自己设置引擎
     *
     * @param httpEngine
     */
    public void exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    private void get(String url, Map<String, Object> parmas, EngineCallBack callBack) {
        mHttpEngine.get(mContext, url, parmas, callBack);
    }

    private void post(String url, Map<String, Object> parmas, EngineCallBack callBack) {
        mHttpEngine.post(mContext, url, parmas, callBack);
    }

    /**
     * 拼接参数
     */
    public static String joinParms(String url, Map<String, Object> parmas) {
        if (parmas == null || parmas.size() <= 0) {
            return url;
        }
        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else if (!url.endsWith("?")) {
            stringBuffer.append("&");
        }
        for (Map.Entry<String, Object> entry : parmas.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();

    }

    public static Class<?> analysisClazzInfo(Object object) {
        Type type = object.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) type).getActualTypeArguments();
            return (Class<?>) params[0];
        }
        return null;
    }

}
