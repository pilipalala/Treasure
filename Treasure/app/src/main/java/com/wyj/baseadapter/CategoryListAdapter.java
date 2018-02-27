package com.wyj.baseadapter;

import android.content.Context;

import com.wyj.treasure.R;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2018/2/27
 * Time 22:30
 * TODO
 *
 * @author wangy
 */

public class CategoryListAdapter extends RecyclerCommonAdapter<String> {

    public CategoryListAdapter(Context context, List<String> mData, int mLayoutId) {
        super(context, mData, mLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, String data, int position) {
        holder.setText(R.id.item_text, data)/*.
                setImageResource(R.id.item_text, new ImageLoad(""))*/;
    }


}
