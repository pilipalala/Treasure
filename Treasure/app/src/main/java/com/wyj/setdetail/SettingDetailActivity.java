package com.wyj.setdetail;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wyj.floatingdialog.BaymanView;
import com.wyj.floatingdialog.FloatViewHelper;
import com.wyj.floatingdialog.SettingsCompat;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingDetailActivity extends BaseActivity {
    @BindView(R.id.tv_result)
    TextView tvResult;
    private String[] permission = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_CODE_LOCATION_SETTINGS = 2;
    private BaymanView mBaymanView;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_setting_detail;
    }

    @Override
    protected void initData() {
        setTitle("6.0危险权限、特殊权限,和其他设置跳转");
        mBaymanView = new BaymanView(this);
        tvResult.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SettingsCompat.canDrawOverlays(this)) {
            FloatViewHelper.showFloatView(this, mBaymanView);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        FloatViewHelper.hideFloatView(this, mBaymanView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBaymanView != null) {
            mBaymanView.destroy();
        }
    }

    /**
     * 检测GPS是否打开
     * GPS或者AGPS开启一个就认为是开启的GPS或者NetWork开启一个就认为是开启的
     */
    public boolean isLocationEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean networkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (networkProvider || gpsProvider) return true;
        return false;
    }

    /**
     * 打开GPS设置界面
     */
    private void setLocationService() {
        Intent locationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        this.startActivityForResult(locationIntent, REQUEST_CODE_LOCATION_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_SETTINGS:
                if (isLocationEnable(this)) {
                    //定位已打开的处理
                    ToastUtil.show("GPS已打开");
                } else {
                    //定位依然没有打开的处理
                    ToastUtil.show("GPS未打开");
                }
                break;
            case SettingsCompat.TYPE_REQUEST_CODE_OVERLAYS:
                if (SettingsCompat.returnCanDrawOverlays(this)) {
                    FloatViewHelper.showFloatView(this, mBaymanView);
                }
                break;
            case SettingsCompat.TYPE_REQUEST_CODE_WRITE:
                if (SettingsCompat.canWriteSettings(this)) {
                    ToastUtil.show("系统设置修改权限已打开");
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @OnClick({R.id.btn_open_gps, R.id.btn_open_permission, R.id.btn_open_float, R.id.btn_open_Write, R.id.btn_open_build})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_gps:
                if (!isLocationEnable(this)) {
                    setLocationService();
                    ToastUtil.show("请打开GPS");
                } else {
                    ToastUtil.show("GPS已打开");
                }
                break;
            case R.id.btn_open_permission:
                /*https://www.jianshu.com/p/a51593817825*/
                requestRuntimePermission(permission, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        ToastUtil.show("权限以获取");
                    }

                    /**/
                    @Override
                    public void onDenied(List<String> deniedPermissions, List<String> unRationalePermissions) {
                        if (unRationalePermissions.size() > 0) {
                            showMessageOKCancel("should open those permission",
                                    (dialog, which) -> JumpPermissionManagement.GoToSetting(SettingDetailActivity.this));
                        }
                    }
                });
                break;
            case R.id.btn_open_float:
                if (!SettingsCompat.canDrawOverlays(this)) {
                    //启动Activity让用户授权
                    SettingsCompat.manageDrawOverlays(this);
                } else {
                    ToastUtil.show("悬浮窗改权限已打开");
                    mBaymanView.show();
                }
                break;
            case R.id.btn_open_Write:
                /**
                 * https://www.jianshu.com/p/bab716584316
                 * 需要申请 WRITE_SETTINGS权限
                 * */

                if (SettingsCompat.canWriteSettings(this)) {
                    ToastUtil.show("系统设置修改权限已打开");
                } else {
                    SettingsCompat.manageWriteSettings(this);
                }
                break;
            case R.id.btn_open_build:
                String build = readString("/system/build.prop");
                Log.e("SettingDetailActivity", "SettingDetailActivity_179-->onViewClicked: " + build);
                tvResult.setText(build);
                break;
        }
    }

    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public String readString(String file) {
        InputStream input = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            input = new FileInputStream(new File(file));
            byte[] buffer = new byte[1024 * 4];
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            output.flush();
            return output.toString("UTF-8");
        } catch (IOException e) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
