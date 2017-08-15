package com.wyj.treasure.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wyj.treasure.R;
import com.wyj.treasure.mode.HomeItem;

import java.util.List;

/**
 * Created by admin
 * on 2017/8/10.
 * TODO
 */

public class HomeAdapter extends BaseQuickAdapter<HomeItem,BaseViewHolder> {
    public HomeAdapter(@LayoutRes int layoutResId, @Nullable List<HomeItem> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setText(R.id.text, item.getTitle());
    }
}
