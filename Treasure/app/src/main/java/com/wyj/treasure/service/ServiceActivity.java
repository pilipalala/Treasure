package com.wyj.treasure.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceActivity extends BaseActivity {
    @BindView(R.id.tv_show_time)
    TextView mTvShowTime;
    private MyService.DownloadBinder binder;
    private ProtectedService.DownloadBinder mDownloadBinder;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_service;
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.DownloadBinder) service;
            binder.startDownload();
            binder.getProgress();
            MyService myService = binder.getService();
            LogUtil.d("onServiceConnected:" + myService.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder = null;
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (ProtectedService.DownloadBinder) service;
            mDownloadBinder.setOnTimeChangeListener(time -> {
                        runOnUiThread(() -> mTvShowTime.setText(time));
                    }
            );
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            String action = intent.getAction();
            LogUtil.d("onReceive---" + action);
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                LogUtil.i(" receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS 长按关机");
            }
        }
    };

    @Override
    protected void initData() {
        setTitle("后台服务和保活");
        IntentFilter filter = new IntentFilter();
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框

        //VIVO X9 按 home 键会触发
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mBatInfoReceiver, filter);
    }

    @OnClick({R.id.btn_keep_alive, R.id.btn_start, R.id.btn_stop, R.id.btn_bind, R.id.btn_unbind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_keep_alive:
                /**
                 *
                 * 1、使用弹出通知栏
                 * 2、双进程
                 * */
                Intent intent = new Intent(this, ProtectedService.class);
                startService(intent);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                //双守护线程，优先级不一样
                startAllServices();
                break;
            case R.id.btn_start:
                Intent start = new Intent(this, MyService.class);
                startService(start);
                break;
            case R.id.btn_stop:
                Intent stop = new Intent(this, MyService.class);
                stopService(stop);
                break;
            case R.id.btn_bind:
                Intent bind = new Intent(this, MyService.class);
                bindService(bind, connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                if (null != binder) {
                    unbindService(connection);
                }
                break;
        }
    }

    private void startAllServices() {
        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startService(new Intent(this, JobWakeUpService.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != binder) {
            unbindService(connection);
        }
        if (null != mDownloadBinder) {
            unbindService(mServiceConnection);
        }
        unregisterReceiver(mBatInfoReceiver);
    }
}
