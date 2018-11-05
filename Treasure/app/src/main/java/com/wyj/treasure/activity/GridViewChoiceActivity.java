package com.wyj.treasure.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.wyj.treasure.R;
import com.wyj.treasure.adapter.MultipleChoiceAdapter;
import com.wyj.treasure.mode.Entity;
import com.wyj.treasure.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

public class GridViewChoiceActivity extends BaseActivity {

    @BindView(R.id.mgv_multiple_Choice)
    GridView mgvMultipleChoice;
    private ArrayList<Entity> timeList;
    private String[] times;
    private MultipleChoiceAdapter choiceAdapter;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_grid_view_choice;
    }

    @Override
    protected void initData() {
        timeList = new ArrayList<>();
        times = getResources().getStringArray(R.array.time);
        for (int i = 0; i < times.length; i++) {
            //把每个item的状态和时间都添加到集合中
            timeList.add(new Entity(times[i], false));
        }
        //设置适配器
        choiceAdapter = new MultipleChoiceAdapter(this, timeList);
        mgvMultipleChoice.setAdapter(choiceAdapter);
        /*多选点击事件*/
        mgvMultipleChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (timeList.get(i).isSelect()) {
                    /*判断第i个item是不是被选中，若是被选中则把这个item的状态改为false*/
                    timeList.get(i).setIsSelect(false);
                } else {
                     /*反之 则把这个item的状态改为true*/
                    timeList.get(i).setIsSelect(true);
                }
                ToastUtil.show(times[i]);
                /*刷新适配器*/
                choiceAdapter.notifyDataSetChanged();
            }
        });
        mgvMultipleChoice.setFocusable(false);
    }
}
