package com.wyj.treasure.rxjava;

import com.wyj.treasure.rxjava.entity.LoginRequest;
import com.wyj.treasure.rxjava.entity.LoginResponse;
import com.wyj.treasure.rxjava.entity.RegisterRequest;
import com.wyj.treasure.rxjava.entity.RegisterResponse;
import com.wyj.treasure.rxjava.entity.UserBaseInfoRequest;
import com.wyj.treasure.rxjava.entity.UserBaseInfoResponse;
import com.wyj.treasure.rxjava.entity.UserExtraInfoRequest;
import com.wyj.treasure.rxjava.entity.UserExtraInfoResponse;

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

}

