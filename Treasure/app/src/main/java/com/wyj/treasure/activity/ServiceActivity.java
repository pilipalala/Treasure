package com.wyj.treasure.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.service.JobWakeUpService;
import com.wyj.treasure.service.LocalService;
import com.wyj.treasure.service.MyService;
import com.wyj.treasure.service.RemoteService;

import butterknife.OnClick;

public class ServiceActivity extends BaseActivity {
    private MyService.DownloadBinder binder;

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


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void initData() {
        setTitle("后台服务");
        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startService(new Intent(this, JobWakeUpService.class));
        }
    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_bind, R.id.btn_unbind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                if (null != connection) {
                    unbindService(connection);
                }
                break;
        }
    }
}
