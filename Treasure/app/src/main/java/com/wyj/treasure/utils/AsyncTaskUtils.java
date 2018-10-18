package com.wyj.treasure.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author yujie
 * @date on 2018/7/18
 * @describe TODO 网络请求工具
 **/
public class AsyncTaskUtils<T> extends AsyncTask<String, Void, T> {
    private static final String TAG = "completeScale";
    private static final String PARSE_FAT_URL = "https://isapi.vtio.cn/front/parse_fat_data/";

    private final String mParam, mUrl;
    private final ScaleInfoCallBack<T> mCallBack;

    public AsyncTaskUtils(String param, String url, ScaleInfoCallBack callBack) {
        mCallBack = callBack;
        mParam = param;
        mUrl = url;
    }

    //Learn

    @Override
    protected T doInBackground(String... param) {
        try {
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) (new URL(mUrl)).openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-type", "application/json");
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数

            Log.d(TAG, "doInBackground: " + mParam);
            printWriter.write(mParam);//post的参数
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            bos.close();
            String response = bos.toString("utf-8");
            return resolve(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private T resolve(String response) {
        Log.d("completeScale", "resolve: " + response);
        Type genType = getClass().getGenericSuperclass();
        Log.e("----------", "convertSuccess: " + genType.getClass().getName());
        //这里得到的是T的类型
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        Log.e("----------", "convertSuccess: " + type.getClass().getName());
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("BaseResponse中没有传入泛型");
        }
        Gson gson = new Gson();
        T data = gson.fromJson(response, type);
        return data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if (mCallBack != null) {
            mCallBack.completeScale(t);
        }

    }

}
