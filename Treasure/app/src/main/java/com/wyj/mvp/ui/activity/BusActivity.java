package com.wyj.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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

    @Override
    public boolean isStartAnimation() {
        return false;
    }


    @Override
    protected int getContentViewID() {
        return R.layout.activity_bus_main;
    }

    @Override
    protected void initData() {
//        setRightTitle("查余额", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        String[] province = Bus_Routers.Routers;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.adapter_auto_complete_item, province);
        actBusNumber.setAdapter(adapter);
        actBusNumber.setOnItemClickListener((parent, view, position, id) -> searchLine(actBusNumber.getText().toString()));
        lineService = new BusClientUtils();
        stationDao = GreenDAOHelp.getDaoSession().getCollectStationDao();
        adapterCollectBus = new AdapterCollectBus(this, R.layout.adapter_collect_bus);
        mRvCollect.setAdapter(adapterCollectBus);
        mRvCollect.setLayoutManager(new LinearLayoutManager(this));

        adapterCollectBus.setOnItemClick((view, position) -> {
            CollectLine item = adapterCollectBus.getItem(position);
            lineService.getStationCars(item.getLineName(),
                    item.getLineId(), item.getStopId(),
                    "1".equals(item.getDirection()),
                    new BaseObserver<CarsInfo>() {
                        @Override
                        protected void _onNext(CarsInfo carInfo) {
                            if (carInfo != null) {
                                LogUtil.d(carInfo.toString());
                                item.setDistance(carInfo.getCars().get(0).getDistance());
                                item.setTime(carInfo.getCars().get(0).getTime());
                                item.setStopdis(carInfo.getCars().get(0).getStopdis());
                                adapterCollectBus.notifyItemChanged(position, item);
                            }
                        }

                        @Override
                        protected void _onComplete() {
                        }

                        @Override
                        protected void _onError(String message) {
                        }
                    });
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        dealWith(stationDao.queryBuilder().list());
    }

    private void dealWith(List<CollectStation> stations) {
        List<CollectLine> collectLines = new ArrayList<>();
        for (int i = 0; i < stations.size(); i++) {
            CollectLine collectLine = new CollectLine();
            collectLine.setStation(stations.get(i));
            collectLines.add(collectLine);
        }
        adapterCollectBus.setData(collectLines);
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
