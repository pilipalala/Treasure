package com.wyj.mvp.ui.adapter;

import android.content.Context;

import com.wyj.baseadapter.RecyclerCommonAdapter;
import com.wyj.baseadapter.ViewHolder;
import com.wyj.mvp.entity.bus.CollectLine;
import com.wyj.mvp.entity.bus.CollectStation;
import com.wyj.treasure.R;
import com.wyj.treasure.utils.CommonUtils;

/**
 * @author wangyujie
 * @date 2018/9/17.16:05
 * @describe 添加描述
 */
public class AdapterCollectBus extends RecyclerCommonAdapter<CollectLine> {

    public AdapterCollectBus(Context context, int mLayoutId) {
        super(context, mLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, CollectLine data, int position) {

        String time = data.getTime();
        if (CommonUtils.isNumeric(time)) {
            try {
                int totalTime = Integer.parseInt(time);
                int needTime = 1;
                if (totalTime < 60) {
                    // 小于1分钟
                    if (data.getStopdis() <= 0 || totalTime < 30) {
                        time = "即将到站";
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


        holder.setText(R.id.tv_collect_station, data.getLineName() + "-" + data.getStationName())
                .setText(R.id.tv_start_end, data.getStartStation() + "-" + data.getEndStation())
                .setText(R.id.tv_station_distance, String.format("还有%d站%s米", data.getStopdis(), data.getDistance()))
                .setText(R.id.tv_station_time, String.format("距离到站:%s", time))
        ;
    }
}
