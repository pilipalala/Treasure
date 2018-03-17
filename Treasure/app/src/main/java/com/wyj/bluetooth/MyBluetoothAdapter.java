package com.wyj.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyj.treasure.R;

import java.util.List;

/**
 * @author wangyujie
 *         on 2018/3/17.13:51
 *         TODO
 */

class MyBluetoothAdapter extends RecyclerView.Adapter<MyBluetoothAdapter.MyViewHolder> {
    private List<BluetoothDevice> data;
    private OnItemClick click;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bluetooth, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BluetoothDevice bluetoothDevice = data.get(position);
        holder.text.setText("设备 ：" + bluetoothDevice.getName() == null ? "null" : bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.itemClick(position);
                }
//                //是否已配对
//                if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
//                    //配对成功 连接蓝牙
//                    connect(bluetoothDevice);
//                } else {
//                    try {
//                        Method bond = bluetoothDevice.getClass().getMethod("createBond");
//                        Boolean invoke = (Boolean) bond.invoke(bluetoothDevice);
//                        if (invoke) {
//                            connect(bluetoothDevice);
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        });
    }

    private void connect(BluetoothDevice device) {

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public void setData(List<BluetoothDevice> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public BluetoothDevice getItem(int mSelectedPosition) {
        return data.get(mSelectedPosition);
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
