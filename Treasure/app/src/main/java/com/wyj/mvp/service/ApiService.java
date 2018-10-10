package com.wyj.mvp.service;

import com.wyj.mvp.entity.MoveBean;
import com.wyj.mvp.entity.WeatherInfo;
import com.wyj.mvp.entity.bus.CarsInfo;
import com.wyj.mvp.entity.bus.LineInfo;
import com.wyj.mvp.entity.bus.LineStationInfo;
import com.wyj.mvp.entity.douban.DouBanBook;
import com.wyj.mvp.entity.zhihu.ZhiHuDaily;
import com.wyj.mvp.entity.zhihu.ZhiHuDetails;
import com.wyj.mvp.service.retrofit.HttpMoveResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author wangyujie
 * @date 2018/9/10.17:10
 * @describe 添加描述
 */
public interface ApiService {
    // ==============豆瓣查书Api===start==============//
    //https://api.douban.com/v2/book/search?q=%E9%87%91%E7%93%B6%E6%A2%85&tag=&start=0&count=1
    @GET("book/search")
    Observable<DouBanBook> getSearchBook(@Query("q") String name,
                                         @Query("tag") String tag,
                                         @Query("start") int start,
                                         @Query("count") int count);

    // ==============豆瓣查书Api===end==============//

    // ==============知乎Api===start==============//
    //http://news-at.zhihu.com/api/4/news/latest
    //RxJava 写法
    @GET("latest")
    Observable<ZhiHuDaily> getObserverNews();

    //https://news-at.zhihu.com/api/4/news/9695994
    @GET("{newsId}")
    Observable<ZhiHuDetails> getNewsDetails(@Path("newsId") String newsId);

    // ==============知乎Api===end==============//

    //http://api.douban.com/v2/movie/top250?start=0&count=10
    //RxJava 写法
    @GET("top250")
    Observable<HttpMoveResult<List<MoveBean.SubjectsBean>>> getTopMove(@Query("start") int start, @Query("count") int count);

    //TODO===============api.laifudao.com===start==============//
    //http://api.laifudao.com/open/xiaohua.json
    @GET("xiaohua.json")
    Observable<HttpMoveResult<List<MoveBean.SubjectsBean>>> getXiaoHua();

    //http://api.laifudao.com/open/tupian.json
    @GET("tupian.json")
    Observable<HttpMoveResult<List<MoveBean.SubjectsBean>>> getTuPian();
    //===============api.laifudao.com===end==============//


    //TODO==============公交api===start==============//
    //http://xxbs.sh.gov.cn:8080/weixinpage/HandlerBus.ashx?action=One&name=189路
    @GET("HandlerBus.ashx")
    Observable<LineInfo> getLineInfo(@Query("action") String action, @Query("name") String lineName);

    //http://apps.eshimin.com/traffic/gjc/getBusStop?name=189区间&lineid=018906
    //http://xxbs.sh.gov.cn:8080/weixinpage/HandlerBus.ashx?action=Two&name=189路&lineid=018900
    @GET("HandlerBus.ashx")
    Observable<LineStationInfo> getLineStationInfo(@Query("action") String action,
                                                   @Query("name") String lineName,
                                                   @Query("lineid") String lineId);

    //http://apps.eshimin.com/traffic/gjc/getArriveBase?name=189%E5%8C%BA%E9%97%B4&lineid=018906&stopid=1&direction=0
    //http://xxbs.sh.gov.cn:8080/weixinpage/HandlerBus.ashx?action=Three&name=189路&lineid=018900&stopid=2&direction=0
    @GET("HandlerBus.ashx")
    Observable<CarsInfo> getCarInfo(@Query("action") String action,
                                    @Query("name") String lineName,
                                    @Query("lineid") String lineId,
                                    @Query("stopid") String stopId,
                                    @Query("direction") int direction);
    //===============公交api===end==============//


    //===============其他api==============//
    //http://www.weather.com.cn/
    @GET("adat/sk/{cityId}.html")
    Call<WeatherInfo> getWeather(@Path("cityId") String cityId);


    // Query
    // 如果链接是http://ip.taobao.com/service/getIpInfo.php?ip=202.202.33.33
    @GET("http://ip.taobao.com/service/getIpInfo.php")
    Call<ResponseBody> query(@Query("ip") String ip);

    @POST("/form")
    @Multipart
    Call<MoveBean> updateUser(
            @Part MultipartBody.Part photo,
            @Part("name") RequestBody name);
    //Body
    //这是针对POST方式，如果参数是json格式
    //    {
    //        "apiInfo":{
    //        "apiName":"WuXiaolong",
    //                "apiKey":"666"
    //        }
    //    }
//    @POST("client/shipper/getCarType")
//    Call<ResponseBody> getCarType(@Body ApiInfo apiInfo);

    //单独的给请求增加请求头
    @Headers("apikey:b86c2269fe6588bbe3b41924bb2f2da2")
    @GET("/student/login")
    Observable<ResponseBody> login(@Query("phone") String phone, @Query("password") String psw);


}
