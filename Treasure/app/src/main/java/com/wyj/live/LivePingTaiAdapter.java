package com.wyj.live;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wyj.baseadapter.ImageLoad;
import com.wyj.treasure.MyApplication;
import com.wyj.treasure.R;
import com.wyj.treasure.mode.HomeItem;
import com.wyj.treasure.utils.GlideUtils;

import java.util.List;

/**
 * Created by admin
 * on 2017/8/10.
 * TODO
 */

public class LivePingTaiAdapter extends BaseQuickAdapter<LivePingTai.PingtaiBean, BaseViewHolder> {
    public LivePingTaiAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, LivePingTai.PingtaiBean item) {
        helper.setText(R.id.text, item.getTitle());

        //第三方 加载图片
        GlideUtils.loadImageUrl(MyApplication.getContext(), item.getXinimg(), helper.getView(R.id.icon));

//        helper.set(R.id.text, item.getTitle());
    }
}
