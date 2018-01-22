package com.wyj.treasure.http;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wangyujie
 * Date 2018/1/20
 * Time 20:55
 * TODO okhttp 网络引擎
 */

public class OkHttpEngine implements IHttpEngine {
    private static OkHttpClient mOkHttpClict = new OkHttpClient();

    @Override
    public void get(Context context, String url, Map<String, Object> parmas, EngineCallBack callBack) {
        url = HttpUtils.joinParms(url, parmas);
        Request.Builder requestBulider = new Request.Builder().url(url).tag(context);
        requestBulider.method("GET", null);
        Request request = requestBulider.build();
        mOkHttpClict.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onError(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack != null) {
                    callBack.onSuccess(response.body().string());
                }
            }
        });
    }

    /**
     * 组装post请求参数body
     *
     * @param params
     * @return
     */
    protected RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    /**
     * 添加参数
     */
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key) + "");
                Object value = params.get(key);
                if (value instanceof File) {
                    //处理文件
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(guessMineType(file.getAbsolutePath())), file));
                } else if (value instanceof List) {
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (int i = 0; i < listFiles.size(); i++) {
                            /*获取文件*/
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key + i, file.getName(), RequestBody.create(MediaType.parse(guessMineType(file.getAbsolutePath())), file));
                        }
                    } catch (Exception e) {

                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }

            }
        }
    }

    /**
     * 猜测文件类型
     */
    private String guessMineType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;

    }


    @Override
    public void post(Context context, String url, Map<String, Object> parmas, EngineCallBack callBack) {
        String joinUrl = HttpUtils.joinParms(url, parmas);

        RequestBody requestBody = appendBody(parmas);
        Request request = new Request.Builder()
                .url(url).tag(context).post(requestBody).build();
        mOkHttpClict.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                /*该回调不在主线程中*/
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body().toString());
                }

            }
        });
    }
}
