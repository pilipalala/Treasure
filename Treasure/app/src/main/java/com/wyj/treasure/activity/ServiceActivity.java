package com.wyj.treasure.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.service.MyService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MyService.DownloadBinder binder;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
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
        tvTitle.setText("后台服务");
        toolbar.setNavigationOnClickListener(v -> finish());
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
                if (null != connection)
                    unbindService(connection);
                break;
        }
    }
}
