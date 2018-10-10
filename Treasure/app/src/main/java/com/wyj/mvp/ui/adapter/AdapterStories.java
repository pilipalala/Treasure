package com.wyj.mvp.ui.adapter;

import android.content.Context;

import com.wyj.baseadapter.ImageLoad;
import com.wyj.baseadapter.RecyclerCommonAdapter;
import com.wyj.baseadapter.ViewHolder;
import com.wyj.mvp.entity.zhihu.NewsInfo;
import com.wyj.treasure.R;

/**
 * @author wangyujie
 * @date 2018/9/17.16:05
 * @describe 添加描述
 */
public class AdapterStories extends RecyclerCommonAdapter<NewsInfo> {

    public AdapterStories(Context context, int mLayoutId) {
        super(context, mLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, NewsInfo data, int position) {
        holder.setText(R.id.tv_title, data.getTitle())
                .setImageResource(R.id.iv_image, new ImageLoad(data.getImagePath()));
    }
}
