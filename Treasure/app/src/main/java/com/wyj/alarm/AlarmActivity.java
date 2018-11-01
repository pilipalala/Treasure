package com.wyj.alarm;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AlarmActivity extends BaseActivity {

    private static final String TAG = "AlarmActivity";

    @BindView(R.id.tv_select_time)
    TextView mTvSelectTime;
    @BindView(R.id.tv_repeat)
    TextView mTvRepeat;
    @BindView(R.id.tv_remind)
    TextView mTvRemind;
    private TimePickerView build;
    private OptionsPickerView pvOptions;
    private AlertDialog dialog;
    private int mRemindPosition;
    private String mSelectTime;

    private int mWhich = 0;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    protected int initView() {
        return R.layout.activity_alarm;
    }

    @Override
    protected void initData() {
        String message = getIntent().getStringExtra("msg");
        int flag = getIntent().getIntExtra("flag", 0);
        if (!TextUtils.isEmpty(message)) {
            showDialogInBroadcastReceiver(message, flag);
        }

        //时间选择器
        build = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mSelectTime = CommonUtils.getTime(date);
                mTvSelectTime.setText(CommonUtils.getTime(date));
            }
        }).isCenterLabel(true)
                .isCyclic(false)
                .setType(new boolean[]{false, false, false, true, true, false})
                .setOutSideCancelable(false)
                .build();

        List<String> options1Items = new ArrayList<>();
        options1Items.add("震动");
        options1Items.add("铃声");
        options1Items.add("震动+铃声");
        //返回的分别是三个级别的选中位置
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                mRemindPosition = options1;
                //返回的分别是三个级别的选中位置
                mTvRemind.setText(options1Items.get(options1));
            }
        }).build();
        pvOptions.setPicker(options1Items);
        dialog = createRepeatDialog(SING_CHOICE_DIALOG);

    }

    private void showDialogInBroadcastReceiver(String message, int flag) {
        if (flag == 1 || flag == 2) {
            mediaPlayer = MediaPlayer.create(this, R.raw.in_call_alarm);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        //数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        //第二个参数为重复次数，-1为不重复，0为一直震动
        if (flag == 0 || flag == 2) {
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("闹钟提醒")
                .setCancelable(false)
                .setMessage(CommonUtils.getTime(new Date()) + message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (flag == 1 || flag == 2) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                        if (flag == 0 || flag == 2) {
                            vibrator.cancel();
                        }
                        dialog.dismiss();
                    }
                })
                .create().show();

    }

    @OnClick({R.id.tv_select_time, R.id.ll_repeat, R.id.ll_remind, R.id.btn_alarm_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_time:
                if (!build.isShowing()) {
                    build.show();
                }
                break;
            case R.id.ll_repeat:
                if (!dialog.isShowing()) {
                    dialog.show();
                }
                break;
            case R.id.ll_remind:
                if (!pvOptions.isShowing()) {
                    pvOptions.show();
                }
                break;
            case R.id.btn_alarm_sure:
                if (!TextUtils.isEmpty(mSelectTime)) {
                    String[] times = mSelectTime.split(":");
                    if (mWhich == 0) {//只响一次
                        AlarmManagerUtil.setAlarm(this, mWhich, Integer.parseInt(times[0]), Integer.parseInt
                                (times[1]), 0, 0, "只响一次的闹钟", mRemindPosition);

                    } else if (mWhich == 1) {//每天都响
                        AlarmManagerUtil.setAlarm(this, mWhich, Integer.parseInt(times[0]), Integer.parseInt
                                (times[1]), 0, 0, "每天都响的闹钟~~~", mRemindPosition);
                    } else {
                        AlarmManagerUtil.setAlarm(this, 2, Integer.parseInt(times[0]), Integer
                                .parseInt(times[1]), 0, (mWhich - 1), "重复闹钟响了", mRemindPosition);
                    }
                    ToastUtil.show("设置成功");
                }
                break;
        }
    }

    private final int SING_CHOICE_DIALOG = 0;
    private final int MULTI_CHOICE_DIALOG = 1;

    public AlertDialog createRepeatDialog(int type) {
        ChoiceOnClickListener choiceOnClickListener = new ChoiceOnClickListener();
        String[] items = getResources().getStringArray(R.array.alarm_remind);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //设置对话框的图标
        builder.setIcon(R.mipmap.ai_4)
                //设置对话框的标题
                .setTitle("选择提醒时间");
        switch (type) {
            case SING_CHOICE_DIALOG:
                builder.setSingleChoiceItems(items, 0, choiceOnClickListener)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int choiceWhich = choiceOnClickListener.getWhich();
                                mTvRepeat.setText(items[choiceWhich]);
                            }
                        });
                break;
            case MULTI_CHOICE_DIALOG:
                boolean[] flags = new boolean[]{true, false, false, false, false, false, false, false, false};//初始复选情况
                builder.setMultiChoiceItems(items, flags, (dialog, which, isChecked) -> {
                    flags[which] = isChecked;
                    String result = "您选择了：";
                    for (int i = 0; i < flags.length; i++) {
                        if (flags[i]) {
                            result = result + items[i] + "、";
                        }
                    }
                    ToastUtil.show(result);
                })
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null);
                break;
        }
        return builder.create();
    }

    private class ChoiceOnClickListener implements DialogInterface.OnClickListener {


        @Override
        public void onClick(DialogInterface dialog, int which) {
            mWhich = which;
        }

        public int getWhich() {
            return mWhich;
        }
    }
}