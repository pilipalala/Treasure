package com.wyj.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;

import com.wyj.greendao.GreenDAOHelp;
import com.wyj.mvp.contract.ZhiHuContract;
import com.wyj.mvp.entity.zhihu.NewsInfo;
import com.wyj.mvp.entity.zhihu.NewsInfoDao;
import com.wyj.mvp.entity.zhihu.ZhiHuDaily;
import com.wyj.mvp.presenter.ZhiHuPresenter;
import com.wyj.mvp.ui.activity.ZhiHuDetailActivity;
import com.wyj.mvp.ui.adapter.AdapterStories;
import com.wyj.treasure.R;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/1/3.
 */
@SuppressLint("ValidFragment")
public class ZhiHuFragment extends MVPBaseFragment<ZhiHuPresenter> implements ZhiHuContract.View {

    //0 日报 1 头条 2 收藏
    private final int num;
    private RecyclerView myRecyclerView;
    private AdapterStories adapterStories;

    private boolean isPrepared = false;

    private CallBackValue callBackValue;

    @SuppressLint("ValidFragment")
    public ZhiHuFragment(int num) {
        this.num = num;
    }

    /**
     * fragment 与 activity 产生关联是 回调这个方法
     * fragment 向 activity 发数据
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        callBackValue = (CallBackValue) getActivity();
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_my_item, null);
        myRecyclerView = view.findViewById(R.id.myRecyclerView);
        return view;
    }


    @Override
    protected ZhiHuPresenter createPresenter() {
        return new ZhiHuPresenter();
    }

    @Override
    protected void initData() {
        adapterStories = new AdapterStories(mContext, R.layout.adapter_new_layout);
        myRecyclerView.setAdapter(adapterStories);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapterStories.setOnItemClick((view, position) -> {
            NewsInfo item = adapterStories.getItem(position);
            startTransition(view, item);
        });
    }

    public void startTransition(final View v, final NewsInfo newsInfo) {
        Transition ts = null;
        Bundle bundle = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            View pic = v.findViewById(R.id.iv_image);
            View title = v.findViewById(R.id.tv_title);
            new Pair<>(pic, newsInfo.getNewsId() + "pic");
            bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                    Pair.create(pic, newsInfo.getNewsId() + "pic"),
                    Pair.create(title, newsInfo.getNewsId() + "title")).toBundle();
        }
        ZhiHuDetailActivity.start(getActivity(), newsInfo.getNewsId(), bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {
            switch (num) {
                case 0:
                case 1:
                    getStories();
                    break;
                case 2:
                    getCollect();
                    break;
            }
        }
    }

    public void getStories() {
        mPresenter.getStories();
    }

    public void getCollect() {
        NewsInfoDao newsInfoDao = GreenDAOHelp.getDaoSession().getNewsInfoDao();
        if (newsInfoDao != null) {
            List<NewsInfo> list = newsInfoDao.queryBuilder().list();
            adapterStories.setData(list);
        } else {
            ToastUtil.show("你没有收藏");
        }
    }

    @Override
    public void onSuccess(ZhiHuDaily value) {
        List<ZhiHuDaily.StoriesBean> stories = value.stories;
        List<ZhiHuDaily.TopStoriesBean> topStories = value.top_stories;
        List<NewsInfo> newsInfos = new ArrayList<>();
        if (num == 0) {
            for (int i = 0; i < stories.size(); i++) {
                ZhiHuDaily.StoriesBean storiesBean = stories.get(i);
                newsInfos.add(new NewsInfo(storiesBean.id, storiesBean.images.get(0), storiesBean.title));
            }
        } else if (num == 1) {
            for (int i = 0; i < topStories.size(); i++) {
                ZhiHuDaily.TopStoriesBean topStoriesBean = topStories.get(i);
                newsInfos.add(new NewsInfo(topStoriesBean.id, topStoriesBean.image, topStoriesBean.title));
            }
        }
        adapterStories.setData(newsInfos);
        if (callBackValue != null) {
            callBackValue.SendMessageValue(value.date);
        }
    }

    @Override
    public void onError(String message) {
        LogUtil.e(message);
    }

    @Override
    public void onComplete() {

    }

    public interface CallBackValue {
        void SendMessageValue(String strValue);
    }

}
