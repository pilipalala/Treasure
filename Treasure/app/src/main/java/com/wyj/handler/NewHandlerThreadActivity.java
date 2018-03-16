package com.wyj.handler;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewHandlerThreadActivity extends BaseActivity implements Handler.Callback {

    @BindView(R.id.tv_start_msg)
    TextView tvStartMsg;
    @BindView(R.id.tv_finish_msg)
    TextView tvFinishMsg;
    @BindView(R.id.btn_start_download)
    Button mBtnStartDownload;
    private DownloadThread downloadThread;
    private Handler mUiHandler;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_new_handler_thread);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mUiHandler = new Handler(this);
        downloadThread = new DownloadThread("下载线程");
        downloadThread.setUIHander(mUiHandler);
        downloadThread.setDownloadUrls("http://pan.baidu.com/s/1qYc3EDQ",
                "http://bbs.005.tv/thread-589833-1-1.html",
                "http://list.youku.com/show/id_zc51e1d547a5b11e2a19e.html?");

    }

    @OnClick(R.id.btn_start_download)
    public void onViewClicked() {
        downloadThread.start();
        mBtnStartDownload.setText("正在下载");
        mBtnStartDownload.setEnabled(false);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case DownloadThread.TYPE_START:
                tvStartMsg.setText(tvStartMsg.getText().toString() + "\n" + msg.obj);
                break;
            case DownloadThread.TYPE_FINISHED:
                tvFinishMsg.setText(tvFinishMsg.getText().toString() + "\n" + msg.obj);
                break;
        }

        return true;
    }
}
