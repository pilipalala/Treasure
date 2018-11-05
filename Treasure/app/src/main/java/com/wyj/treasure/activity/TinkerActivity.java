package com.wyj.treasure.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Button;

import com.wyj.treasure.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TinkerActivity extends BaseActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 0x11;
    @BindView(R.id.btn_permission)
    Button btnPermission;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_tinker;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_permission)
    public void onViewClicked() {
        insertDummyContactWrapper();

    }
    private String[] permission = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private void insertDummyContactWrapper() {
        requestRuntimePermission(permission, new PermissionListener() {
            @Override
            public void onGranted() {
                Log.e("TinkerActivity_52", "TinkerActivity_52-->onGranted: ");
            }

            @Override
            public void onDenied(List<String> deniedPermissions, List<String> unRationalePermissions) {
                Log.e("TinkerActivity_52", "TinkerActivity_54-->deniedPermissions: " + deniedPermissions.size());
                Log.e("TinkerActivity_52", "TinkerActivity_54-->unRationalePermissions: " + unRationalePermissions.size());

            }
        });

//        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
//        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
//            if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
//                showMessageOKCancel("You need to allow access to Contacts",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
//                                        REQUEST_CODE_ASK_PERMISSIONS);
//                            }
//                        });
//                return;
//            }
//            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
//                    REQUEST_CODE_ASK_PERMISSIONS);
//            return;
//        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(TinkerActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
