package com.wyj.mvp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.wyj.mvp.Bus_Routers;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.CommonUtils;

import butterknife.BindView;

/**
 * @author wangyujie
 * @date 2018/9/27.12:00
 * @describe 添加描述
 * 余额查询 http://220.248.75.36/handapp/PGcardAmtServlet?arg1=34315751630&callback=yue&_=1538015235122
 */
public class BusActivity extends BaseActivity {

    @BindView(R.id.act_bus_number)
    AutoCompleteTextView actBusNumber;

    private Intent intent = new Intent();


    @Override
    protected int initView() {
        return R.layout.activity_bus_main;
    }

    @Override
    protected void initData() {
        setTitle("上海公交");
        String[] province = Bus_Routers.Routers;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.adapter_auto_complete_item, province);
        actBusNumber.setAdapter(adapter);
        actBusNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchLine(actBusNumber.getText().toString());
            }
        });
    }

    /**
     * 查询按钮点击事件响应
     *
     * @param v
     */
    public void searchLineClick(View v) {
        String lineName = actBusNumber.getText().toString();
        if (TextUtils.isEmpty(lineName)) {
            Toast.makeText(BusActivity.this, "请输入要查询的公交路线", Toast.LENGTH_SHORT).show();
            return;
        }
        // 校验输入的完整性
        if (CommonUtils.isNumeric(lineName)) {
            lineName = lineName + "路";
        }
        searchLine(lineName);


    }

    private void searchLine(String lineName) {
        // 检查网络
        if (!CommonUtils.checkNet(BusActivity.this)) {
            Toast.makeText(BusActivity.this, R.string.network_tip, Toast.LENGTH_LONG).show();
            return;
        }
        // 切换Activity
        intent.setClass(BusActivity.this, ResultActivity.class);
        intent.putExtra("lineName", lineName);
        startActivity(intent);
    }

}
