package com.wyj.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.wyj.greendao.GreenDAOHelp;
import com.wyj.mvp.Bus_Routers;
import com.wyj.mvp.entity.bus.CarsInfo;
import com.wyj.mvp.entity.bus.CollectLine;
import com.wyj.mvp.entity.bus.CollectStation;
import com.wyj.mvp.entity.bus.CollectStationDao;
import com.wyj.mvp.manager.BusClientUtils;
import com.wyj.mvp.service.retrofit.BaseObserver;
import com.wyj.mvp.ui.adapter.AdapterCollectBus;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.rv_collect)
    RecyclerView mRvCollect;

    private Intent intent = new Intent();
    private CollectStationDao stationDao;
    private BusClientUtils lineService;
    private AdapterCollectBus adapterCollectBus;
    private boolean isConnect = false;

    @Override
    public boolean isStartAnimation() {
        return false;
    }



    @Override
    protected int initView() {
        return R.layout.activity_bus_main;
    }

    @Override
    protected void initData() {
        String[] province = Bus_Routers.Routers;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.adapter_auto_complete_item, province);
        actBusNumber.setAdapter(adapter);
        actBusNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchLine(actBusNumber.getText().toString());
            }
        });
        lineService = new BusClientUtils();
        stationDao = GreenDAOHelp.getDaoSession().getCollectStationDao();
        adapterCollectBus = new AdapterCollectBus(this, R.layout.adapter_collect_bus);
        mRvCollect.setAdapter(adapterCollectBus);
        mRvCollect.setLayoutManager(new LinearLayoutManager(this));
        dealWith(stationDao.queryBuilder().list());
        setRightTitle("刷新", v -> dealWith(stationDao.queryBuilder().list()));
    }

    private void dealWith(List<CollectStation> list) {
        List<CollectLine> lineList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            lineService.getStationCars(list.get(i).getLineName(),
                    list.get(i).getLineId(), list.get(i).getStopId(),
                    list.get(i).getDirection().equals("1") ? true : false,
                    new BaseObserver<CarsInfo>() {
                        @Override
                        protected void _onNext(CarsInfo carInfo) {
                            if (carInfo != null) {
                                LogUtil.d(carInfo.toString());
                                CollectLine collectLine = new CollectLine();
                                collectLine.setDistance(carInfo.getCars().get(0).getDistance());
                                collectLine.setTime(carInfo.getCars().get(0).getTime());
                                collectLine.setStopdis(carInfo.getCars().get(0).getStopdis());
                                collectLine.setStation(list.get(finalI));
                                lineList.add(collectLine);
                                adapterCollectBus.setData(lineList);
                            }
                        }

                        @Override
                        protected void _onComplete() {
                            isConnect = false;
                        }

                        @Override
                        protected void _onError(String message) {
                            isConnect = false;
                        }
                    });
        }
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
