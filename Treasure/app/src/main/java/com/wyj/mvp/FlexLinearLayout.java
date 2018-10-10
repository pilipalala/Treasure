package com.wyj.mvp;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyj.mvp.entity.bus.CarsInfo;
import com.wyj.mvp.entity.bus.StationInfo;
import com.wyj.treasure.R;
import com.wyj.treasure.utils.CommonUtils;

import java.util.List;

public class FlexLinearLayout extends LinearLayout {
    private LayoutInflater mInflater;

    private LinearLayout layout;
    private RelativeLayout llCards;

    private TextView tvStationName;
    private ImageView ivXialaIco;
    private ImageView ivXiatopIco;
    private TextView tvCardName;

    /**
     * 创建一个带有伸缩效果的LinearLayout
     *
     * @param context       上下文对象
     * @param station       内容详细
     * @param position      该列所在列表的位置
     */
    public FlexLinearLayout(final Context context, final StationInfo station,
                            final int position, List<CarsInfo.CarInfo> cars) {
        super(context);

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (LinearLayout) mInflater.inflate(R.layout.act_stations_list, null);
        init();

        this.addView(layout);
        setWorkTitleLayout(station, position, cars);
    }

    /**
     * 设置该列的状态及样式
     *
     * @param station       内容详细
     * @param position      该列所在列表的位置
//     * @param isCurrentItem 是否为伸展
     */
    public void setWorkTitleLayout(final StationInfo station, final int position,
                                   List<CarsInfo.CarInfo> cars) {
        init();

        int lineindex = position + 1;
        tvStationName.setText(lineindex + " . " + station.getZdmc());
        tvStationName.setTextColor(0xFF3D8CB8);

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
            tvCardName.setText(text);
        } else {
            tvCardName.setText(R.string.no_cars);
        }
    }

    private void init() {
//        if (llCards == null) {
//            llCards = (RelativeLayout) layout.findViewById(R.id.ll_cards);
//        }
//        if (tvStationName == null) {
//            tvStationName = (TextView) findViewById(R.id.tv_station_name);
//        }
//        if (ivXialaIco == null) {
//            ivXialaIco = (ImageView) findViewById(R.id.iv_xiala_ico);
//        }
//        if (ivXiatopIco == null) {
//            ivXiatopIco = (ImageView) findViewById(R.id.iv_xiatop_ico);
//        }
//        if (tvCardName == null) {
//            tvCardName = (TextView) findViewById(R.id.tv_card_name);
//        }
    }
}
