package com.wyj.treasure.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.ToastUtil;

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
    private LayoutInflater inflater;

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


        /*布局选择器*/
        inflater = LayoutInflater.from(this);
        // 默认添加一条
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.child_attr_view, null);
        layout.setLayoutParams(params);
        final LinearLayout item = (LinearLayout) layout.getChildAt(0);
        EditText etAttr = (EditText) item.findViewById(R.id.et_attr);
        item.findViewById(R.id.tv_detele_attr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAttr.removeView(layout);
            }
        });
        llAttr.addView(layout);
    }


    @OnClick({R.id.tv_right_title, R.id.tv_add_attr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_title:
                if (TextUtils.isEmpty(etAttrName.getText().toString().trim())) {
                    ToastUtil.show("请填写属性名");
                    return;
                }
                StringBuffer s = new StringBuffer();
                s.append(etAttrName.getText().toString().trim() + ":");
                for (int i = 0; i < llAttr.getChildCount(); i++) {
                    LinearLayout item = (LinearLayout) llAttr.getChildAt(i);
                    EditText et1 = (EditText) item.findViewById(R.id.et_attr);
                    if (TextUtils.isEmpty(et1.getText().toString().trim())) {
                        ToastUtil.show("请填写剩余的字段名");
                        return;
                    }
                    if (i == 0) {
                        s.append(et1.getText().toString().trim());
                    } else {
                        s.append("-").append(et1.getText().toString().trim());
                    }
                }
                break;
            case R.id.tv_add_attr:
                for (int i = 0; i < llAttr.getChildCount(); i++) {
                    LinearLayout item = (LinearLayout) llAttr.getChildAt(llAttr.getChildCount() - 1);
                    EditText et1 = (EditText) item.findViewById(R.id.et_attr);
                    if (TextUtils.isEmpty(et1.getText().toString().trim())) {
                        ToastUtil.show("请填写字段名");
                        return;
                    }
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.child_attr_view, null);
                layout.setLayoutParams(params);
                LinearLayout item = (LinearLayout) layout.getChildAt(0);
                final TextView tvDeteleAttr = (TextView) item.findViewById(R.id.tv_detele_attr);
                tvDeteleAttr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        llAttr.removeView(layout);
                    }
                });
                llAttr.addView(layout);

                break;
        }
    }
}
