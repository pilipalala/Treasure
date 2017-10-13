package com.wyj.rxjava;

import com.wyj.rxjava.entity.LoginRequest;
import com.wyj.rxjava.entity.LoginResponse;
import com.wyj.rxjava.entity.RegisterRequest;
import com.wyj.rxjava.entity.RegisterResponse;
import com.wyj.rxjava.entity.UserBaseInfoRequest;
import com.wyj.rxjava.entity.UserBaseInfoResponse;
import com.wyj.rxjava.entity.UserExtraInfoRequest;
import com.wyj.rxjava.entity.UserExtraInfoResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wangyujie
 * on 2017/9/27.11:21
 * TODO
 */

public interface RxApi {
    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @GET
    Observable<UserBaseInfoResponse> getUserBaseInfo(@Body UserBaseInfoRequest request);

    @GET
    Observable<UserExtraInfoResponse> getUserExtraInfo(@Body UserExtraInfoRequest request);

    @GET("v2/movie/top250")
    Observable<Response<ResponseBody>> getTop250(@Query("start") int start, @Query("count") int count);
}

