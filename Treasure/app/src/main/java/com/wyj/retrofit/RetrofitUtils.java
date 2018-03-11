package com.wyj.retrofit;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by wangyujie
 * Date 2018/3/11
 * Time 17:27
 * TODO
 */

public interface RetrofitUtils {

    //    http://news-at.zhihu.com/api/4/news/latest
    //Retrofit 写法
    @GET("latest")
    Call<NewBean> getNews();

    //RxJava 三部曲 第一步 创建Observable
    //RxJava 写法
    @GET("latest")
    Observable<NewBean> getObserverNews();

    //    http://api.douban.com/v2/movie/top250?start=0&count=10
    //RxJava 写法
    @GET("top250")
    Observable<MoveBean> getMove(@Query("start") int start, @Query("count") int count);


    @POST("/form")
    @Multipart
    Call<MoveBean> upateUser(
            @Part MultipartBody.Part photo,
            @Part("name") RequestBody name);
}
