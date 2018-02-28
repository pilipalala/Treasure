package com.wyj.baseadapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> data;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_recycle_view);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        getData();
        CategoryListAdapter adapter = new CategoryListAdapter(this, data, R.layout.adapter_layout);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setOnItemClick(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtil.show(data.get(position));
            }
        });
        adapter.setOnItemLongClick(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                ToastUtil.show(data.get(position)+"Long");
            }
        });
    }

    public void getData() {
        data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("" + i);
        }
    }
}
