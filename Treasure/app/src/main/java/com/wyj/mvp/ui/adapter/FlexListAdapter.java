package com.wyj.mvp.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyj.greendao.GreenDAOHelp;
import com.wyj.mvp.entity.bus.CarsInfo;
import com.wyj.mvp.entity.bus.CollectStation;
import com.wyj.mvp.entity.bus.CollectStationDao;
import com.wyj.mvp.entity.bus.StationInfo;
import com.wyj.treasure.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

/**
 * 自定义listview
 */
public class FlexListAdapter extends BaseAdapter {
    private List<StationInfo> stations;
    private Context context;
    private List<CarsInfo.CarInfo> cars;
    private int mPosition = -1;
    private final LayoutInflater mInflater;
    private OnCollectClick mListener;

    public FlexListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        if (stations != null) {
            return stations.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (stations == null) {
            return null;
        }
        return stations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        StationInfo station = stations.get(position);
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.act_stations_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CollectStationDao stationDao = GreenDAOHelp.getDaoSession().getCollectStationDao();
        List<CollectStation> collectStations = stationDao.queryBuilder()
                .where(CollectStationDao.Properties.StationId.eq(station.getId() + "_" + station.getZdmc())).list();
        Log.e(TAG, "FlexListAdapter_78-->getView: " + (collectStations != null) + "--" + collectStations.size());
        holder.mIvCollect.setSelected(collectStations.size() > 0);
        holder.mTvStationName.setText((position + 1) + " . " + station.getZdmc());
        holder.mTvStationName.setTextColor(0xFF3D8CB8);
        holder.mTvStationName.setBackgroundColor(mPosition == position ?
                context.getResources().getColor(R.color.pink) : context.getResources().getColor(R.color.white));
        holder.mIvCollect.setBackgroundColor(mPosition == position ?
                context.getResources().getColor(R.color.pink) : context.getResources().getColor(R.color.white));
        holder.mIvCollect.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onCollect(position, collectStations.size() > 0 ? collectStations.get(0) : null);
            }
        });
        return convertView;
    }

    public void setOnCollectListener(OnCollectClick listener) {
        mListener = listener;

    }

    public interface OnCollectClick {

        void onCollect(int position, CollectStation collectStation);
    }

    public void setStations(List<StationInfo> stations) {
        this.stations = stations;
    }


    public void setCars(List<CarsInfo.CarInfo> cars) {
        this.cars = cars;
    }

    public void setPosition(int position) {
        this.mPosition = position;
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @BindView(R.id.tv_station_name)
        TextView mTvStationName;
        @BindView(R.id.iv_collect)
        ImageView mIvCollect;
        @BindView(R.id.ll_root)
        LinearLayout llRoot;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
