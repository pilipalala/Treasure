package com.wyj.treasure.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.adapter.TagAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridLayoutManagerActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private static final int MAX = 9;
    private List<TagBean> tagBeanList = new ArrayList<>();
    private TagAdapter tagAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_grid_layout_manager);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("RecyclerView通过GridLayoutManager实现多样式布局");
        toolbar.setNavigationOnClickListener(v -> finish());
        setData();
        GridLayoutManager layoutManage = new GridLayoutManager(this, 2);
        layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (tagBeanList.get(position).getTag_name().length() > MAX)
                    return 2;
                return 1;
            }
        });
        recycle.setLayoutManager(layoutManage);
        tagAdapter = new TagAdapter(tagBeanList);
        recycle.setAdapter(tagAdapter);
    }

    private void setData() {
        tagBeanList.add(new TagBean("1", "准时"));
        tagBeanList.add(new TagBean("2", "非常绅士"));
        tagBeanList.add(new TagBean("3", "非常有礼貌"));
        tagBeanList.add(new TagBean("4", "很会照顾女生"));
        tagBeanList.add(new TagBean("5", "我的男神是个大暖男哦"));
        tagBeanList.add(new TagBean("6", "谈吐优雅"));
        tagBeanList.add(new TagBean("7", "送我到楼下"));
        tagBeanList.add(new TagBean("9", "迟到"));
        tagBeanList.add(new TagBean("10", "态度恶劣"));
        tagBeanList.add(new TagBean("11", "有不礼貌行为"));
        tagBeanList.add(new TagBean("12", "有侮辱性语言有暴力倾向"));
        tagBeanList.add(new TagBean("13", "人身攻击"));
        tagBeanList.add(new TagBean("14", "临时改变行程"));
        tagBeanList.add(new TagBean("15", "客户迟到并无理要求延长约会时间"));
    }

    public class TagBean {

        public TagBean(String tag_id, String tag_name) {
            this.tag_id = tag_id;
            this.tag_name = tag_name;
        }

        /**
         * tag_id : 55
         * tag_name : 准时
         */


        private String tag_id;
        private String tag_name;

        public String getTag_id() {
            return tag_id;
        }

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }
    }
}
