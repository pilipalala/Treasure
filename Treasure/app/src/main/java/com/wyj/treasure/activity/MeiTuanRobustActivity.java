package com.wyj.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.meituan.robust.Patch;
import com.meituan.robust.PatchExecutor;
import com.meituan.robust.RobustCallBack;
import com.wyj.treasure.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeiTuanRobustActivity extends AppCompatActivity {


    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.btn_hotfix)
    Button btnHotfix;
    @BindView(R.id.tvShow2)
    TextView tvShow2;
    @BindView(R.id.tvShow3)
    TextView tvShow3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mei_tuan_robust);
        ButterKnife.bind(this);
        File file = getDir();//创建文件夹  /mnt/sdcard/HotFix
    }


    @OnClick({R.id.btn_go, R.id.btn_hotfix})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                Intent it = new Intent(MeiTuanRobustActivity.this, HotFixActivity.class);
                startActivity(it);
                break;
            case R.id.btn_hotfix://执行修复
                new PatchExecutor(getApplicationContext(),
                        new PatchManipulateImp(),
                        new RobustCallBack() {
                            @Override
                            public void onPatchListFetched(boolean result, boolean isNet) {
                                Log.e("error-hot", "打印 onPatchListFetched：" + "isNet=" + isNet);
                            }

                            @Override
                            public void onPatchFetched(boolean result, boolean isNet, Patch patch) {
                                Log.e("error-hot", "打印 onPatchFetched：" + "result=" + result + "isNet=" + isNet + "--->" + "patch=" + patch);
                            }

                            @Override
                            public void onPatchApplied(boolean result, Patch patch) {
                                Log.e("error-hot", "打印 onPatchApplied：" + "result=" + result + "--->" + "patch=" + patch);
                            }

                            @Override
                            public void logNotify(String log, String where) {
                                Log.e("error-hot", "打印 logNotify：" + "log=" + log + "--->" + "where=" + where);
                            }

                            @Override
                            public void exceptionNotify(Throwable throwable, String where) {
                                Log.e("error-hot", "打印 exceptionNotify：" + "throwable=" + throwable.toString() + "--->" + "where=" + where);
                            }
                        }).start();
                break;
        }
    }

    int count = 0;

    private File getDir() {
        StringBuilder path = new StringBuilder();
        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory()
                    .getPath());
            path.append(File.separator);// '/'
            path.append("HotFix");// /mnt/sdcard/HotFix

            Log.e("error-hotfix", "如果SD卡可用就在SD卡创建");
            tvShow3.setText("SD卡可用就在sd创建");
        } else {
            //如果SD卡不可用就在内存创建
            File filesDir = getApplication().getCacheDir();    //  cache  getFileDir file
            path.append(filesDir.getAbsolutePath());

            tvShow3.setText("SD卡不可用就在内存创建");
            Log.e("error-hotfix", "SD卡不可用就在内存创建");
        }
        File file = new File(path.toString());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();// 创建文件夹
            count += 10;
        }
        Toast.makeText(this, "file=" + file, Toast.LENGTH_SHORT).show();
        Log.e("error-hotfix", count + " ==>file地址=" + file.toString() + "-->" + file.getAbsolutePath());
        tvShow2.setText(file.toString() + "\n" + file.getAbsolutePath());
        return file;

    }

    private boolean isSDAvailable() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "sd 有效", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }


}
