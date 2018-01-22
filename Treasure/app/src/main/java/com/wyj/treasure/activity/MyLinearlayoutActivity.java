package com.wyj.treasure.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wyj.treasure.R;
import com.wyj.treasure.http.DisCoverListResult;
import com.wyj.treasure.http.HttpCallBack;
import com.wyj.treasure.http.HttpUtils;
import com.wyj.treasure.utils.LogUtil;

public class MyLinearlayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_linearlayout);
        HttpUtils.with(this)
                .url("https://is.snssdk.com/neihan/stream/mix/v1/")
                .addParma("iid", "24029435664")
                .addParma("aid", "7")

                .execeute(new HttpCallBack<DisCoverListResult>() {

                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onSuccess(DisCoverListResult result) {
                        LogUtil.d(result.message);
                    }
                });
    }
}
