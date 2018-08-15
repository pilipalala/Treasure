package com.wyj.treasure.activity;

import android.content.ComponentName;
import android.content.pm.PackageManager;

import com.wyj.treasure.R;

import butterknife.OnClick;

public class ChangeLaucherActivity extends BaseActivity {
    private ComponentName componentName;
    private ComponentName componentNameDefault;
    private PackageManager packageManager;

    @Override
    protected int initView() {
        return R.layout.activity_change_laucher;
    }

    @Override
    protected void initData() {
        packageManager = getApplicationContext().getPackageManager();
        //得到此activity的全限定名
        componentNameDefault = getComponentName();
        componentName = new ComponentName(
                getBaseContext(),
                "com.wyj.treasure.MainAliasActivity");
    }

    @OnClick(R.id.btn_change)
    public void onViewClicked() {
        disableComponent(componentNameDefault);
        enableComponent(componentName);
        //Intent 重启 Launcher 应用
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        List<ResolveInfo> resolves = packageManager.queryIntentActivities(intent, 0);
//        for (ResolveInfo res : resolves) {
//            if (res.activityInfo != null) {
//                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//                am.killBackgroundProcesses(res.activityInfo.packageName);
//            }
//        }
    }
    /**
     * 启动组件
     *
     * @param componentName 组件名
     */
    private void enableComponent(ComponentName componentName) {
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     *
     * @param componentName 组件名
     */
    private void disableComponent(ComponentName componentName) {
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
