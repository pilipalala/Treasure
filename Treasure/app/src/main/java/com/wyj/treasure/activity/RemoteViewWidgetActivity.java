package com.wyj.treasure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wyj.treasure.R;

public class RemoteViewWidgetActivity extends BaseActivity {

    /**
     * 1、定义 小部件界面
     * 新建一个res/layout  xml 文件，名称内容自定义  widget.xml
     * 2、定义小部件配置信息
     * 新建res/xml   xml文件 ，名称随意选择  appwidget_provider_info.xml
     * <p>
     * <appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
     * android:initialLayout="@layout/widget"
     * android:minHeight="360dp"
     * android:minWidth="360dp"
     * android:updatePeriodMillis="864000" />
     * <p>
     * initialLayout就是指小工具所使用的初始化布局，
     * minHeight和minWidth定义小工具的最小尺寸，
     * updatePeriodMillis定义小工具的自动更新周期，毫秒为单位，每隔一个周期，小工具的自动更新就会触发
     */
    @Override
    protected int getContentViewID() {
        return R.layout.activity_remote_view_widget;
    }

    @Override
    protected void initData() {

    }
}
