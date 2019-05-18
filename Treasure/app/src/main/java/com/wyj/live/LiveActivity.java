package com.wyj.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wyj.mvp.ui.activity.MVPBaseActivity;
import com.wyj.treasure.R;
import com.wyj.treasure.adapter.HomeAdapter;

import java.util.List;

import butterknife.BindView;


/**
 * http://api.hclyz.com:81/mf/jsonqiqi.txt
 * http://api.hclyz.com:81/mf/json.txt
 */
public class LiveActivity extends MVPBaseActivity<LivePresenter> implements LiveContract.View {

    @BindView(R.id.rv_live_item)
    RecyclerView mRvLiveItem;
    private List<LivePingTai.PingtaiBean> mDataList;
    private LivePingTaiAdapter pingTaiAdapter;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_live;
    }

    @Override
    protected void initData() {
        pingTaiAdapter = new LivePingTaiAdapter(R.layout.adapter_base_list_item);
        mRvLiveItem.setAdapter(pingTaiAdapter);
        mRvLiveItem.setLayoutManager(new GridLayoutManager(this, 2));
        mPresenter.getLivePingTai();
    }


    @Override
    public void onSuccess(LivePingTai livePingTai) {
        mDataList = livePingTai.getPingtai();
        pingTaiAdapter.setNewData(mDataList);
        pingTaiAdapter.notifyDataSetChanged();
        Log.e("LiveActivity_36", "-->onSuccess: " + livePingTai.getPingtai().size());
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    protected LivePresenter createPresenter() {
        return new LivePresenter();
    }
}
