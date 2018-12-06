package com.wyj.treasure.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.LogUtil;

/**
 * @author wangyujie
 * @date 2018/11/9.16:32
 * @describe 该Activity的View只要设置为1像素然后设置在Window对象上即可。
 * 在Activity的onDestroy周期中进行保活服务的存活判断从而唤醒服务
 */
public class SinglePixelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attrParams = mWindow.getAttributes();
        attrParams.x = 0;
        attrParams.y = 0;
        attrParams.height = 1;
        attrParams.width = 1;
        mWindow.setAttributes(attrParams);
        ScreenManager.getInstance(this).setSingleActivity(this);
    }

    @Override
    protected void onDestroy() {
        if (!CommonUtils.isServiceWork(ProtectedService.class.getName())) {
            LogUtil.i("SinglePixelActivity onDestroy");
            Intent intentAlive = new Intent(this, ProtectedService.class);
            startService(intentAlive);
        }
        super.onDestroy();
    }
}
