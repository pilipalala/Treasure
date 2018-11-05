package com.wyj.treasure.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DongTaiActivity extends BaseActivity {

    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.bt_del)
    Button btDel;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.ll_dongtai_main)
    LinearLayout llDongtaiMain;
    private ArrayList<ViewHolder> ls_vh;
    private LayoutInflater inflater;
    private View childView;
    private int mark = 0;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_dong_tai;
    }

    @Override
    protected void initData() {
        setTitle("动态添加布局");
        String data = getIntent().getStringExtra("data");
        String str = getIntent().getStringExtra("str");
        LogUtil.d("data--->" + data);
        LogUtil.d("str---->" + str);
        ls_vh = new ArrayList<ViewHolder>();// 保存View的实例
        /*布局选择器*/
        inflater = LayoutInflater.from(this);
        childView = inflater.inflate(R.layout.child_view, null);
        // 默认添加一条
        llDongtaiMain.addView(childView, mark);
        saveViewInstance(childView);// 实例化View
    }

    private void saveViewInstance(View inflatView) {
        ViewHolder vh = new ViewHolder();
        vh.setId(mark);
        TextView tv_target = inflatView.findViewById(R.id.et_target);
        EditText et_work = inflatView.findViewById(R.id.et_work);
        EditText et_count = inflatView.findViewById(R.id.et_count);
        // 注册监听事件
        tv_target.setOnClickListener(targetListener);
        et_work.setOnClickListener(workListener);

        vh.setTv_target(tv_target);
        vh.setEt_work(et_work);
        vh.setEt_count(et_count);
        ls_vh.add(vh);

    }

    private String[] targetAry = {"目标1", "目标2", "目标3"};
    private String[] workAry = {"工作说明1", "工作说明2", "工作说明3"};

    View.OnClickListener targetListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // View parentView = (View) v.getParent().getParent();
            // int vid = parentView.getId();
            TextView tv = (TextView) v;
            showDialogList("请选择目标", tv, targetAry);
        }
    };
    View.OnClickListener workListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // View parentView = (View) v.getParent().getParent();
            TextView tv = (TextView) v;
            showDialogList("请选择工作说明", tv, workAry);

            // for (int i = 0; i < myLinearLayout.getChildCount(); i++) {
            // if(vid==ls_vh.get(i).getId()){
            // EditText et = ls_vh.get(i).getEt_work();
            // showDialogList("请选择目标", et, targetAry);
            // break;
            // }
            // }
        }
    };

    public void showDialogList(final String tittle, final TextView tv,
                               final String[] array) {
        AlertDialog.Builder builder = null;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DongTaiActivity.this, android.R.layout.simple_list_item_1,
                array);
        // if (Build.VERSION.SDK_INT >= 14) {
        // builder = new AlertDialog.Builder(this, R.style.style_date_picker);
        // }
        builder = new AlertDialog.Builder(this);

        builder.setTitle(tittle).setNeutralButton("取消", null)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        tv.setText(array[which]);
                        // mTvBelongProvince.setTag(mProvinceList.get(which).areaId);
                    }
                }).show();
    }

    @OnClick({R.id.bt_add, R.id.bt_del, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_add:
                mark++;
                // 新增布局必须每次获取一下布局，否则不能作区分
                childView = inflater.inflate(R.layout.child_view, null);
                llDongtaiMain.addView(childView, mark);
                saveViewInstance(childView);// 实例化View

                break;
            case R.id.bt_del:
                if (llDongtaiMain.getChildCount() > 0) {
                    // 删除动态生成View的最后一条
                    llDongtaiMain.removeViewAt(llDongtaiMain.getChildCount() - 1);
                    mark--;
                    if (ls_vh.size() > 0) {
                        // 删除集合中最后一条
                        ls_vh.remove(ls_vh.size() - 1);
                    }
                }
                break;
            case R.id.bt_save:

                if (ls_vh.size() == 0) {
                    ToastUtil.show("请增加一条数据");
                    break;
                }
                StringBuffer s = new StringBuffer();
                String str = "";
                for (int i = 0; i < ls_vh.size(); i++) {
                    ViewHolder v = ls_vh.get(i);
                    s.append("达成目标:").append(v.getTv_target().getText().toString())
                            .append("\n").append("工作说明:")
                            .append(v.getEt_work().getText().toString())
                            .append("\n").append("拜访次数:")
                            .append(v.getEt_count().getText().toString())
                            .append("\n");
                    str = s.toString();
                }
                str.trim();
                if (!str.equals("")) {
                    ToastUtil.show(str);
                    finish();
                }

                break;
        }
    }


    /**
     * 保存动态生成的View的实例
     */
    private class ViewHolder implements Serializable {

        public int id;
        public TextView tv_target;
        public EditText et_work;
        public EditText et_count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TextView getTv_target() {
            return tv_target;
        }

        public void setTv_target(TextView tv_target) {
            this.tv_target = tv_target;
        }

        public EditText getEt_work() {
            return et_work;
        }

        public void setEt_work(EditText et_work) {
            this.et_work = et_work;
        }

        public EditText getEt_count() {
            return et_count;
        }

        public void setEt_count(EditText et_count) {
            this.et_count = et_count;
        }

    }

    public static void actionStart(Context context, String data, String str) {
        Intent intent = new Intent(context, DongTaiActivity.class);
        intent.putExtra("param1", data);
        intent.putExtra("param2", str);
        context.startActivity(intent);
    }
}
