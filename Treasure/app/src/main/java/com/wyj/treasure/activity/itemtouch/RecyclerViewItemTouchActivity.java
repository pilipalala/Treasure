package com.wyj.treasure.activity.itemtouch;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.adapter.RecycleViewHandleAdapter;
import com.wyj.treasure.mode.ViewHandleMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewItemTouchActivity extends BaseActivity implements StartDragListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecycleViewHandleAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_recycler_view_item_handle);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        tvTitle.setText("拖拽排序效果");
        toolbar.setNavigationOnClickListener(v -> finish());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecycleViewHandleAdapter(getData(), this);
        recyclerView.setAdapter(adapter);

        /*条目触摸帮助类*/
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private List<ViewHandleMode> getData() {

        List<ViewHandleMode> lsit = new ArrayList<>();
        lsit.add(new ViewHandleMode("零", "17:20", "暂无消息", R.mipmap.avatar3));
        lsit.add(new ViewHandleMode("壹", "17:20", "暂无消息", R.mipmap.avatar1));
        lsit.add(new ViewHandleMode("貳", "17:20", "暂无消息", R.mipmap.avatar2));
        lsit.add(new ViewHandleMode("叁", "13:22", "QQ天气", R.mipmap.avatar3));
        lsit.add(new ViewHandleMode("肆", "17:20", "暂无消息", R.mipmap.avatar4));
        lsit.add(new ViewHandleMode("伍", "17:20", "暂无消息", R.mipmap.avatar1));
        lsit.add(new ViewHandleMode("陆", "17:20", "暂无消息", R.mipmap.avatar3));
        lsit.add(new ViewHandleMode("柒", "17:20", "暂无消息", R.mipmap.avatar4));
        lsit.add(new ViewHandleMode("捌", "17:20", "暂无消息", R.mipmap.avatar3));
        lsit.add(new ViewHandleMode("玖", "17:20", "暂无消息", R.mipmap.avatar1));
        lsit.add(new ViewHandleMode("拾", "17:20", "暂无消息", R.mipmap.avatar2));
        lsit.add(new ViewHandleMode("拾壹", "13:22", "QQ天气", R.mipmap.avatar3));
        lsit.add(new ViewHandleMode("拾贰", "17:20", "暂无消息", R.mipmap.avatar4));
        lsit.add(new ViewHandleMode("拾叁", "17:20", "暂无消息", R.mipmap.avatar1));
        lsit.add(new ViewHandleMode("拾肆", "17:20", "暂无消息", R.mipmap.avatar3));
        lsit.add(new ViewHandleMode("拾伍", "17:20", "暂无消息", R.mipmap.avatar4));
        return lsit;
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        点击就能拖拽
        itemTouchHelper.startDrag(viewHolder);
    }


}
