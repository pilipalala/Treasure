package com.wyj.treasure.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Button;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.OnClick;

public class TinkerActivity extends BaseActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 0x11;
    @BindView(R.id.btn_permission)
    Button btnPermission;

    @Override
    protected int initView() {
        return R.layout.activity_tinker;
    }

    @Override
    protected void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.btn_permission)
    public void onViewClicked() {
        insertDummyContactWrapper();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertDummyContactWrapper() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                showMessageOKCancel("You need to allow access to Contacts",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
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
