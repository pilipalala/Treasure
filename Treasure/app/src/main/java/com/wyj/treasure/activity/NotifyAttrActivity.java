package com.wyj.treasure.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotifyAttrActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_attr_name)
    EditText etAttrName;
    @BindView(R.id.ll_attr)
    LinearLayout llAttr;
    @BindView(R.id.tv_add_attr)
    TextView tvAddAttr;
    private ArrayList<ViewHolder> ls_vh;
    private LayoutInflater inflater;
    private View childView;
    private int mark = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_attr);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("添加新属性");
        tvRightTitle.setVisibility(View.VISIBLE);
        tvRightTitle.setText("保存");

        ls_vh = new ArrayList<ViewHolder>();// 保存View的实例
        /*布局选择器*/
        inflater = LayoutInflater.from(this);
        childView = inflater.inflate(R.layout.child_attr_view, null);
        // 默认添加一条
        llAttr.addView(childView, mark);
        saveViewInstance(childView);// 实例化View
    }

    private void saveViewInstance(View inflatView) {
        ViewHolder vh = new ViewHolder();
        vh.setId(mark);
        EditText etAttr = (EditText) inflatView.findViewById(R.id.et_attr);
        TextView tvDeteleAttr = (TextView) inflatView.findViewById(R.id.tv_detele_attr);
        // 注册监听事件
        tvDeteleAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("删除--->");
                if (llAttr.getChildCount() > 0) {
                    // 删除动态生成View的最后一条
                    llAttr.removeViewAt(mark);
                    mark--;
                    if (ls_vh.size() > 0) {
                        // 删除集合中最后一条
                        ls_vh.remove(ls_vh.size() - 1);
                    }
                }
            }
        });

        vh.setEt_attr(etAttr);
        vh.setTv_delete_attr(tvDeteleAttr);
        ls_vh.add(vh);

    }


    /**
     * 保存动态生成的View的实例
     */
    private class ViewHolder implements Serializable {

        public int id;
        public TextView tv_delete_attr;
        public EditText et_attr;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TextView getTv_delete_attr() {
            return tv_delete_attr;
        }

        public void setTv_delete_attr(TextView tv_delete_attr) {
            this.tv_delete_attr = tv_delete_attr;
        }

        public EditText getEt_attr() {
            return et_attr;
        }

        public void setEt_attr(EditText et_attr) {
            this.et_attr = et_attr;
        }


    }

    @OnClick({R.id.tv_right_title, R.id.tv_add_attr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_title:
                if (ls_vh.size() == 0) {
                    ToastUtil.show("请增加一条数据");
                    break;
                }
                StringBuffer s = new StringBuffer();
                String str = "";
                for (int i = 0; i < ls_vh.size(); i++) {
                    ViewHolder v = ls_vh.get(i);
                    s.append("属性:").append(v.getEt_attr().getText().toString());
                    str = s.toString();
                }
                str.trim();
                if (!str.equals("")) {
                    ToastUtil.show(str);
                }
                break;
            case R.id.tv_add_attr:
                mark++;
                // 新增布局必须每次获取一下布局，否则不能作区分
                childView = inflater.inflate(R.layout.child_attr_view, null);
                llAttr.addView(childView, mark);
                saveViewInstance(childView);// 实例化View
                break;
        }
    }
}
