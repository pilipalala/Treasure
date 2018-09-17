package com.wyj.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyj.treasure.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyujie
 * on 2018/3/17.13:51
 * TODO
 */

class MyBluetoothAdapter extends RecyclerView.Adapter<MyBluetoothAdapter.MyViewHolder> {
    private List<BluetoothDevice> mLeDevices;
    private OnItemClick click;

    public MyBluetoothAdapter() {
        mLeDevices = new ArrayList<BluetoothDevice>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bluetooth, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BluetoothDevice bluetoothDevice = mLeDevices.get(position);
        holder.text.setText("设备 ：" + bluetoothDevice.getName() == null ?
                "Unknown device" : bluetoothDevice.getName() + "\n MAC:" + bluetoothDevice.getAddress());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.itemClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mLeDevices == null ? 0 : mLeDevices.size();
    }


    public void setDeviceData(List<BluetoothDevice> devices) {
        mLeDevices = devices;
        notifyDataSetChanged();
    }

    public BluetoothDevice getItem(int mSelectedPosition) {
        return mLeDevices.get(mSelectedPosition);
    }

    public interface OnItemClick {
        void itemClick(int position);
    }

    public void setOnItemClick(OnItemClick click) {
        this.click = click;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_bluetooth);
        }
    }

}
