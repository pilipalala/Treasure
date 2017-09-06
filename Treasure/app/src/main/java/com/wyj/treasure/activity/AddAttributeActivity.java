package com.wyj.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.transition.TagRecycleViewActivity;
import com.wyj.treasure.adapter.AddAttrAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAttributeActivity extends AppCompatActivity {

    @BindView(R.id.rv_attr)
    RecyclerView rvAttr;
    @BindView(R.id.tv_add_attr)
    TextView tvAddAttr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attribute);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        rvAttr.setLayoutManager(new LinearLayoutManager(this));
        rvAttr.setAdapter(new AddAttrAdapter(this));
    }

    @OnClick(R.id.tv_add_attr)
    public void onViewClicked() {
        startActivity(new Intent(AddAttributeActivity.this, TagRecycleViewActivity.class));

    }
}
