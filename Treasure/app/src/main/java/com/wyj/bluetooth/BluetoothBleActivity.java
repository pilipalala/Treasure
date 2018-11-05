package com.wyj.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class BluetoothBleActivity extends BaseActivity {


    private BluetoothAdapter.LeScanCallback mLeScanCallback;
    //是否扫描中
    private boolean mScanning;
    private BluetoothGatt mBluetoothGatt;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_bluetooth_ble;
    }

    Handler mHandler = new Handler();
    @BindView(R.id.rv_bluetooth)
    RecyclerView rvBluetooth;
    @BindView(R.id.btn_search)
    Button btnSearch;
    private ProgressDialog mProgressDialog;
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 0x0123;
    //扫描时间
    private long SCAN_PERIOD = 30 * 1000;
    private MyBluetoothAdapter adapter;
    private List<BluetoothDevice> mBoundDevicesList = new ArrayList<>();
    private BluetoothStateReceiver mBluetoothStateReceiver;

    private String TAG = "BluetoothGattCallback";

    @Override
    protected List<ItemInfo> getListData() {
        return null;
    }

    public boolean check() {
        return (null != mBluetoothAdapter && mBluetoothAdapter.isEnabled() && !mBluetoothAdapter.isDiscovering());
    }

    @Override
    protected void initData() {
        setTitle("蓝牙");
        checkBleEnable();
        initReceiver();
        //初始化 Bluetooth adapter, 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上和版本)
        //1、首先获取 BluetoothManager
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //2、获取 BluetoothAdapter
        mBluetoothAdapter = bluetoothManager.getAdapter();
        // 检查设备上是否支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        adapter = new MyBluetoothAdapter();
        rvBluetooth.setLayoutManager(new LinearLayoutManager(this));
        rvBluetooth.setAdapter(adapter);
        adapter.setOnItemClick(position -> {
            BluetoothDevice device = adapter.getItem(position);
            if (device != null) {
                ToastUtil.show(device.getName() + "连接中...");
                connectDevice(device);
            }


        });
        //3、创建BluetoothAdapter.LeScanCallback
        initLeScan();
    }

    /**
     * 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
     */
    public void checkBleEnable() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    /**
     * 设置一段时间之后关闭蓝牙扫
     */
    private void scanLeDevice(boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (enable) {
                mBoundDevicesList.clear();
                //30s后停止扫描
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScanning = false;
                        btnSearch.setText("搜索完毕");
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    }
                }, SCAN_PERIOD);
                mScanning = true;
                // 定义一个回调接口供扫描结束处理
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            } else {
                mScanning = false;
                btnSearch.setText("搜索完毕");
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }

    }

    /**
     * 连接设备
     */
    private void connectDevice(BluetoothDevice device) {
        if (!check()) return;  // 检测蓝牙
        if (null != mBluetoothAdapter) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        if (mBluetoothGatt != null && mBluetoothGatt.connect()) {  // 已经连接了其他设备
            // 如果是先前连接的设备，则不做处理
            if (TextUtils.equals(device.getAddress(), mBluetoothGatt.getDevice().getAddress())) {
                return;
            } else {
                disconnect();  // 否则断开连接
            }
        }
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        if (null != mBluetoothGatt) {
            mBluetoothGatt.disconnect();
        }
        Log.e(TAG, "disconnect");
    }

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }


        /**
         * 连接状态改变(连接成功或失败)时回调该接口
         */
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            switch (newState) {
                case BluetoothDevice.BOND_BONDED://已配对

                    Log.e(TAG, "BluetoothBleActivity_198-->onConnectionStateChange: ");
                    break;
                case BluetoothDevice.BOND_BONDING://配对中
                    Log.e(TAG, "BluetoothBleActivity_201-->onConnectionStateChange: ");
                    break;
                case BluetoothDevice.BOND_NONE://未配对
                    Log.e(TAG, "BluetoothBleActivity_204-->onConnectionStateChange: ");
                    break;
            }
            if (newState == BluetoothGatt.STATE_CONNECTED) {   // 连接成功
                ToastUtil.show("连接成功");
            } else {   // 连接失败
                ToastUtil.show("连接失败");
            }
        }

        /**
         * 发现设备的服务(Service)回调，需要在这里处理订阅事件。
         */
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        /**
         * 发送消息结果回调
         */
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {// YES 用户允许


            } else if (resultCode == RESULT_CANCELED) {// NO 用户取消

            }
        }

    }

    @OnClick({R.id.btn_open, R.id.btn_close, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                //2、如果检测到蓝牙没有开启，尝试开启蓝牙。
                //第一种 直优雅的践行开启并且有弹框进行提示，隐式启动Intent:
//                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(intent, REQUEST_ENABLE_BT);
//                }
                //第二种 直接简单暴力不给用户进行提示
                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                }

                break;
            case R.id.btn_close:
                if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.disable();
                }
                break;
            case R.id.btn_search:
                //扫描蓝牙设备
                scanLeDevice(true);
                break;
        }
    }

    /**
     * 蓝牙状态发生变化时回调
     */
    private void onBluetoothStateChanged(Intent intent) {

    }

    /**
     * 创建BluetoothAdapter.LeScanCallback
     */
    private void initLeScan() {
        mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

            /**
             * @param device 蓝牙设备
             * @param rssi 蓝牙的信号强弱指标
             * @param scanRecord 蓝牙广播出来的广告数据
             */
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                if (!TextUtils.isEmpty(device.getName())) {
                    LogUtil.d("蓝牙设备:" + device.getName() + "\n getAddress" + device.getAddress() + "\n信号强度" + rssi + "\n  uuid:" + device.getUuids());
                    String struuid = CommonUtils.bytes2HexString(CommonUtils.reverseBytes(scanRecord)).replace("-", "").toLowerCase();
                    Log.d(TAG, "run: " + struuid);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            btnSearch.setText("搜索中...");
                            if (device != null) {
                                if (!mBoundDevicesList.contains(device)) {
                                    mBoundDevicesList.add(device);
                                }
                                adapter.setDeviceData(mBoundDevicesList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        };
        //开始搜索设备。
        //第一个方法可以指定只扫描含有特定 UUID Service 的蓝牙设备
//      mBluetoothAdapter.startLeScan(serviceUuids, callback);
        //第二个方法则是扫描全部蓝牙设备
//        mBluetoothAdapter.startLeScan(callback);
        //停止蓝牙扫描
//      mBluetoothAdapter.stopLeScan(callback);
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

    private void initReceiver() {
        mBluetoothStateReceiver = new BluetoothStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBluetoothStateReceiver, filter);
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

    //与蓝牙模块的通信一般都是采用16进制，byte[]传输，因此需提供几个格式转换的方法。
    // byte转十六进制字符串
    public static String bytes2HexString(byte[] bytes) {
        String ret = "";
        for (byte item : bytes) {
            String hex = Integer.toHexString(item & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase(Locale.CHINA);
        }
        return ret;
    }

    // 将16进制的字符串转换为字节数组
    public static byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }

}
