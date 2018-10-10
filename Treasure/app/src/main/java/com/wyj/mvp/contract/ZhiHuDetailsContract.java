package com.wyj.mvp.contract;

import com.wyj.mvp.entity.zhihu.NewsInfo;
import com.wyj.mvp.entity.zhihu.ZhiHuDetails;
import com.wyj.mvp.view.IView;

/**
 * @author wangyujie
 * @date 2018/10/8.14:56
 * @describe 添加描述
 */
public class ZhiHuDetailsContract {
    public interface View extends IView {
        void onDetailSuccess(ZhiHuDetails zhiHuDetails);

        void onError(String message);

        void onComplete();
    }

    public interface Presenter {
        //从网络获取新闻详情
        void getNewsDetails(String id);

        //从数据库中获取新闻
        NewsInfo getNewsInfo(String id);

        //从数据库中删除
        void deleteNews(NewsInfo newsInfo);

        //插入一条新闻到数据库中
        void insert(NewsInfo newsInfo);
    }

}
