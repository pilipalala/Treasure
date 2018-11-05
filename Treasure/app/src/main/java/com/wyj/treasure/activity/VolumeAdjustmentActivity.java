package com.wyj.treasure.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VolumeAdjustmentActivity extends BaseActivity {

    @BindView(R.id.seekBar)
    SeekBar seekBar;
    private AudioManager manager;
    private int maxVolume;
    private int barMax;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_volume_adjustment;
    }

    @Override
    protected void initData() {
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        /**
         * 2、直接设置音量值的方法:
         * public void setStreamVolume(intstreamType, intindex, intflags)
         * am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND);
         *
         * am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);//得到听筒模式的最大值
         * am.getStreamVolume(AudioManager.STREAM_VOICE_CALL);//得到听筒模式的当前值
         * */
        barMax = seekBar.getMax();
        maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBar.setProgress(getStreamVolume());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int volume = i * manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / barMax;
                seekBar.setProgress(i);
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private int getStreamVolume() {
        return manager.getStreamVolume(AudioManager.STREAM_VOICE_CALL) * barMax / maxVolume;
    }

    /**
     * 调整音量方法有两种,一种是渐进式,即像手动按音量键一样,一步一步增加或减少,另一种是直接设置音量值.
     * 1、渐进式
     * public void adjustStreamVolume(intstreamType, intdirection, intflags)
     * <p>
     * 解释一下三个参数
     * 第一个streamType是需要调整音量的类型,这里设的是媒体音量,可以是:
     * STREAM_ALARM 警报
     * STREAM_MUSIC 音乐回放即媒体音量
     * STREAM_NOTIFICATION 窗口顶部状态栏Notification,
     * STREAM_RING 铃声
     * STREAM_SYSTEM 系统
     * STREAM_VOICE_CALL 通话
     * STREAM_DTMF 双音多频
     * <p>
     * 第二个direction,是调整的方向,增加或减少,可以是:
     * ADJUST_LOWER 降低音量
     * ADJUST_RAISE 升高音量
     * ADJUST_SAME 保持不变,这个主要用于向用户展示当前的音量
     * <p>
     * 第三个flags是一些附加参数,只介绍两个常用的
     * FLAG_PLAY_SOUND 调整音量时播放声音
     * FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个
     * 0表示什么也没有
     *
     * @param view
     */
    @OnClick({R.id.btn_add, R.id.btn_cut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                manager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                break;
            case R.id.btn_cut:
                manager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP://增大系统媒体音量
                raiseMusicVolume();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN://减小系统媒体音量
                lowerMusicVolume();
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 显示系统音量面板并增加媒体音量
     */
    public void raiseMusicVolume() {
        if (manager != null) {
            //强制增大多媒体音量
            manager.adjustStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE,
                    AudioManager.FLAG_SHOW_UI);
        }
    }

    /**
     * 显示系统音量面板并降低媒体音量
     */
    public void lowerMusicVolume() {
        if (manager != null) {
            //强制降低多媒体音量
            manager.adjustStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER,
                    AudioManager.FLAG_SHOW_UI);
        }
    }

}
