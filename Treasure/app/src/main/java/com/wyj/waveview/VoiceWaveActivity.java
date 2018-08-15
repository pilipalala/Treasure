package com.wyj.waveview;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;

public class VoiceWaveActivity extends BaseActivity implements VoiceSinWaveView.IFade {
    @BindView(R.id.coutent)
    LinearLayout coutent;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.waveview)
    WaveView waveview;
    @BindView(R.id.remotewaveview)
    RemoteWaveView remotewaveview;
    private VoiceSinWaveView voiceSinWaveView;


    @Override
    protected int initView() {
        return R.layout.activity_voice_wave;
    }

    @Override
    protected void initData() {
        voiceSinWaveView = new VoiceSinWaveView(this);
        voiceSinWaveView.addToParent(coutent);
        voiceSinWaveView.setCallBack(this);
        voiceSinWaveView.start();
        handler.sendEmptyMessage(3);
        waveview.start();
        remotewaveview.start();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                voiceSinWaveView.updateStrength(progress);
                waveview.setmFactor(progress / 100f);
                remotewaveview.setmFactor(progress / 100f);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public float data[] = {80, 20, 80, 20, 80, 20, 80, 20, 80, 20, 80, 20, 80, 20, 80, 20, 80, 20,
            100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 10, 20, 30, 40, 50, 60, 70, 80, 90,
            100, 100, 100, 80, 70, 60, 50, 40, 30, 20, 10, 100, 100, 100, 100, 100, 100, 100, 100,
            100, 100, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 100, 100, 80, 70, 60, 50, 40, 30,
            20, 10, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 10, 20, 30, 40, 50, 60, 70,
            80, 90, 100, 100, 100, 80, 70, 60, 50, 40, 30, 20, 10};
    private int index;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 3) {
                handler.sendEmptyMessageDelayed(3, 16);
                if (index >= data.length) {
                    index = 0;
                }

                voiceSinWaveView.updateStrength(data[index]);
                waveview.setmFactor(data[index] / 100f);
                remotewaveview.setmFactor(data[index] / 100f);
                index++;
            }

        }
    };

    @Override
    public void fadeOut() {

    }

    @Override
    public void fadeToQuarter() {

    }

}
