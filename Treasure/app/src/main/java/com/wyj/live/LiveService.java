package com.wyj.live;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author wangyujie
 * @date 2019/5/10.19:03
 * @describe 添加描述
 */
public interface LiveService {
    @GET("json.txt")
    Observable<LivePingTai> getLivePingTai();
}
