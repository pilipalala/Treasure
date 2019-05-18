package com.wyj.youtube;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class YoutubeActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.header)
    TextView mHeader;
    @BindView(R.id.desc)
    TextView mDesc;
    @BindView(R.id.dragLayout)
    YoutubeLayout mDragLayout;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_youtube;
    }

    @Override
    protected void initData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("item" + i);
        }
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data));
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            mHeader.setText(mListView.getAdapter().getItem(position).toString());
            mDragLayout.setVisibility(View.VISIBLE);
            mDragLayout.maximize();
        });
    }

}
