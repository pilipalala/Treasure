package com.wyj.mvp.ui.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wyj.mvp.entity.bus.CarsInfo;
import com.wyj.mvp.entity.bus.LineInfo;
import com.wyj.mvp.entity.bus.LineStationInfo;
import com.wyj.mvp.service.retrofit.BaseObserver;
import com.wyj.mvp.ui.adapter.FlexListAdapter;
import com.wyj.mvp.manager.BusClientUtils;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class ResultActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.tv_bus_result)
    TextView mTvBusResult;
    @BindView(R.id.tv_qidian)
    TextView mTvQidian;
    @BindView(R.id.tv_starTime)
    TextView mTvStarTime;
    @BindView(R.id.tv_zhongdian)
    TextView mTvZhongdian;
    @BindView(R.id.tv_stopTime)
    TextView mTvStopTime;
    @BindView(R.id.lineinfo)
    LinearLayout mLineinfo;
    @BindView(R.id.ll_tip)
    LinearLayout mLlTip;
    @BindView(R.id.list_cards)
    ListView mListCards;
    // 路线名称
    private String lineName = null;
    // 线路信息 
    private LineInfo mLineInfo = null;
    // 站点信息
    private LineStationInfo mLineStation = null;

    // 方向
    private boolean direction = true;
    private BusClientUtils lineService;


    private FlexListAdapter adapter;


    @Override
    protected int initView() {
        return R.layout.activity_bus_result;
    }

    @Override
    protected void initData() {
        lineName = getIntent().getStringExtra("lineName");
        lineService = new BusClientUtils();
        setTitle(lineName);
        searchLine(lineName);
    }

    /**
     * 查询公交线路和站点
     */
    private void searchLine(String nowLineName) {
        if (TextUtils.isEmpty(nowLineName)) {
            return;
        }
        lineService.getLineInfo(nowLineName, new BaseObserver<LineInfo>() {
            @Override
            protected void _onNext(LineInfo lineInfo) {
                LogUtil.d("lineInfo----->" + lineInfo.toString());
                mLineinfo.setVisibility(View.VISIBLE);
                mLlTip.setVisibility(View.VISIBLE);
                //线路信息
                mLineInfo = lineInfo;
                showLineInfo();
                lineService.getLineStation(mLineInfo.getLine_name(), mLineInfo.getLine_id(), new BaseObserver<LineStationInfo>() {
                    @Override
                    protected void _onNext(LineStationInfo lineStationInfo) {
                        LogUtil.d("lineStationInfo----->" + lineStationInfo.toString());
                        mLineStation = lineStationInfo;
                        // 站点信息
                        showStations(ResultActivity.this);
                    }

                    @Override
                    protected void _onComplete() {

                    }

                    @Override
                    protected void _onError(String message) {
                        LogUtil.e("lineStationInfo----->" + message);
                        ToastUtil.show(R.string.no_line);
                    }
                });
            }

            @Override
            protected void _onComplete() {

            }

            @Override
            protected void _onError(String message) {
                LogUtil.e(message);
                ToastUtil.show(R.string.no_line);
            }
        });
    }

    /**
     * 实时查询车辆信息
     */
    private void searchCarsSearchLine(String stopId) {
        lineService.getStationCars(lineName, mLineInfo.getLine_id(), stopId, direction, new BaseObserver<CarsInfo>() {
            @Override
            protected void _onNext(CarsInfo carInfo) {
                LogUtil.d(carInfo.toString());
                dealWithCar(carInfo);
                // 车辆信息
                adapter.setCars(carInfo.getCars());
                // 即时刷新
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void _onComplete() {

            }

            @Override
            protected void _onError(String message) {
                LogUtil.e("carInfo----->" + message);
                mTvBusResult.setVisibility(View.VISIBLE);
                mTvBusResult.setText(R.string.no_cars);
            }
        });
    }

    private void dealWithCar(CarsInfo carInfo) {
        mTvBusResult.setVisibility(View.VISIBLE);
        List<CarsInfo.CarInfo> cars = carInfo.getCars();
        if (cars != null && !cars.isEmpty()) {
            String text = "";
            for (CarsInfo.CarInfo car : cars) {
                if (!TextUtils.isEmpty(text)) {
                    text = text + "\n";
                }
                String time = car.getTime();
                if (CommonUtils.isNumeric(time)) {
                    try {
                        int totalTime = Integer.parseInt(time);
                        int needTime = 1;
                        if (totalTime < 60) {
                            // 小于1分钟
                            if (car.getStopdis() <= 0 || totalTime < 30) {
                                text = text + String.format("%s即将到站，距离%s米", car.getTerminal(), car.getDistance());
                                continue;
                            }
                        } else {
                            needTime = totalTime / 60;
                            if (totalTime % 60 != 0) {
                                needTime = needTime + 1;
                            }
                        }
                        time = needTime + "分钟";
                    } catch (Exception e) {
                    }
                }

                text = text + String.format("%s还有%s站，约%s，距离%s米", car.getTerminal(),
                        car.getStopdis(), time, car.getDistance());
            }
            mTvBusResult.setText(text);
        } else {
            mTvBusResult.setText(R.string.no_cars);
        }
    }


    /**
     * 交换方向
     *
     * @param v
     */
    public void switchDirectionClick(View v) {
        if (mLineInfo == null || mLineStation == null) {
            return;
        }
        mTvBusResult.setVisibility(View.GONE);
        direction = !direction;
        showLineInfo();

        showStations(this);
        // 即时刷新  
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // 检查网络
        if (!CommonUtils.checkNet(ResultActivity.this)) {
            Toast.makeText(ResultActivity.this, R.string.network_tip, Toast.LENGTH_SHORT).show();
            return;
        }
        adapter.setPosition(position);
        if (direction) {
            searchCarsSearchLine(mLineStation.getLineResults0().getStops().get(position).getId());
        } else {
            searchCarsSearchLine(mLineStation.getLineResults1().getStops().get(position).getId());
        }
    }

    /**
     * 展示线路信息
     */
    private void showLineInfo() {
        if (mLineInfo == null) {
            return;
        }
        if (direction) {
            mTvQidian.setText(mLineInfo.getStart_stop());
            mTvZhongdian.setText(mLineInfo.getEnd_stop());
            mTvStarTime.setText(mLineInfo.getStart_earlytime());
            mTvStopTime.setText(mLineInfo.getStart_latetime());
        } else {
            mTvQidian.setText(mLineInfo.getEnd_stop());
            mTvZhongdian.setText(mLineInfo.getStart_stop());
            mTvStarTime.setText(mLineInfo.getEnd_earlytime());
            mTvStopTime.setText(mLineInfo.getEnd_latetime());
        }

    }

    private void showStations(ResultActivity activity) {
        if (mLineStation == null) {
            return;
        }
        if (adapter == null) {
            adapter = new FlexListAdapter(activity);
            mListCards.setAdapter(adapter);
            mListCards.setOnItemClickListener(ResultActivity.this);
        }
        if (direction) {
            // 正向
            adapter.setStations(mLineStation.getLineResults0().getStops());
        } else {
            // 反向
            adapter.setStations(mLineStation.getLineResults1().getStops());
        }
    }

}
