package com.wyj.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class BluetoothActivity extends BaseActivity {
    final static int TASK_TYPE_CONNECT = 1;
    final static int TASK_TYPE_PRINT = 2;
    Handler mHandler = new Handler();
    @BindView(R.id.rv_bluetooth)
    RecyclerView rvBluetooth;
    @BindView(R.id.btn_search)
    Button btnSearch;
    private ProgressDialog mProgressDialog;
    private AsyncTask<BluetoothDevice, Integer, BluetoothSocket> mConnectTask;
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 0x0123;
    private UUID[] serviceUuids = new UUID[]{};
    private long SCAN_PERIOD = 30 * 1000;
    private boolean mScanning;
    private MyBluetoothAdapter adapter;
    private List<BluetoothDevice> mBoundDevicesList = new ArrayList<>();
    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    LogUtil.d("onReceive===" + "BluetoothAdapter.ACTION_STATE_CHANGED");

                    break;
                case BluetoothDevice.ACTION_FOUND:
                    LogUtil.d("onReceive===" + "BluetoothDevice.ACTION_FOUND");
                    //广播的 intent 里包含了一个 BluetoothDevice 对象
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    // 定义一个装载蓝牙设备名字和地址的Map
                    Map<String, String> deviceMap = new HashMap<>();
                    // 过滤已配对的和重复的蓝牙设备
                    mBoundDevicesList.add(device);
                    if ((device.getBondState() != BluetoothDevice.BOND_BONDED) && isSingleDevice(device)) {
                    }
                    // 显示发现的蓝牙设备列表
                    // 加载设备
                    adapter.setDeviceData(mBoundDevicesList);
                    btnSearch.setText("搜索中...");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    btnSearch.setText("搜索完毕");
                    break;
            }

        }
    };
    private BluetoothSocket mSocket;
    //4、连接蓝牙设备
    private BluetoothStateReceiver mBluetoothStateReceiver;
    private int mSelectedPosition;



    private String TAG = "BluetoothGattCallback";
    //    状态改变
    BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            Log.e(TAG, "onConnectionStateChange: thread "
                    + Thread.currentThread() + " status " + newState);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                if (status != BluetoothGatt.GATT_SUCCESS) {
                    String err = "Cannot connect device with error status: " + status;
                    // 当尝试连接失败的时候调用 disconnect 方法是不会引起这个方法回调的，所以这里
                    //   直接回调就可以了。
                    gatt.close();
                    Log.e(TAG, err);
                    return;
                }

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.e(TAG, "connect--->success" + newState + "," + gatt.getServices().size());

                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.e(TAG, "Disconnected from GATT server.");

                    Log.e(TAG, "connect--->failed" + newState);
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.e(TAG, "onServicesDiscovered received:  SUCCESS");
                try {
                    Thread.sleep(200);//延迟发送，否则第一次消息会不成功
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "onServicesDiscovered error falure " + status);
            }

        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.e(TAG, "onCharacteristicWrite status: " + status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.e(TAG, "onDescriptorWrite status: " + status);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.e(TAG, "onDescriptorRead status: " + status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            Log.e(TAG, "onCharacteristicRead status: " + status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            Log.e(TAG, "onCharacteristicChanged characteristic: " + characteristic);
        }

    };
    /**
     * 判断此设备是否存在
     */
    private boolean isSingleDevice(BluetoothDevice device) {
        if (mBoundDevicesList == null) {
            return true;
        }
        for (BluetoothDevice bluetoothDevice : mBoundDevicesList) {
            if ((device.getAddress()).equals(bluetoothDevice.getAddress())) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected int initView() {
        return R.layout.activity_bluetooth;
    }

    @Override
    protected List<ItemInfo> getListData() {
        return null;
    }

    @Override
    protected void initData() {
        setTitle("蓝牙");
        //1、首先获取 BluetoothManager
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //2、获取 BluetoothAdapter
        mBluetoothAdapter = bluetoothManager.getAdapter();
        adapter = new MyBluetoothAdapter();
        rvBluetooth.setLayoutManager(new LinearLayoutManager(this));
        rvBluetooth.setAdapter(adapter);
        adapter.setOnItemClick(new MyBluetoothAdapter.OnItemClick() {
            @Override
            public void itemClick(int position) {
                mSelectedPosition = position;
            }
        });


    }

    /**
     * 设置一段时间之后关闭蓝牙扫
     */
    private void scanLeDevice(boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
//                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
            mScanning = true;
            // 定义一个回调接口供扫描结束处理
//            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
//            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {// YES 用户允许


            } else if (resultCode == RESULT_CANCELED) {// NO 用户取消

            }
        }

    }

    @OnClick({R.id.btn_open, R.id.btn_close, R.id.btn_search, R.id.btn_print})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                //2、如果检测到蓝牙没有开启，尝试开启蓝牙。
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                }

                break;
            case R.id.btn_close:
                if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.disable();
                }
                break;
            case R.id.btn_search:
                //3、扫描蓝牙设备

                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                } else {
                    initReceiver();
                    // 设置广播信息过滤
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(BluetoothDevice.ACTION_FOUND);//每搜索到一个设备就会发送一个该广播
                    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
                    filter.setPriority(Integer.MAX_VALUE);//设置优先级
                    registerReceiver(mReceiver, filter);// 注册蓝牙搜索广播接收者，接收并处理搜索结果

//                    initLeScan();
                    mBoundDevicesList.clear();
                    mBluetoothAdapter.startDiscovery();
                }


                break;
            case R.id.btn_print:
                BluetoothDevice device = adapter.getItem(mSelectedPosition);
                break;
        }
    }

    private void initReceiver() {
        mBluetoothStateReceiver = new BluetoothStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBluetoothStateReceiver, filter);
    }

    /**
     * 蓝牙状态发生变化时回调
     */
    private void onBluetoothStateChanged(Intent intent) {

    }



    protected void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 监听蓝牙状态变化的系统广播
     */
    class BluetoothStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_ON:
                    ToastUtil.show("蓝牙已开启");
                    break;

                case BluetoothAdapter.STATE_TURNING_OFF:
                    ToastUtil.show("蓝牙已关闭");
                    break;
            }
            onBluetoothStateChanged(intent);
        }
    }

}
