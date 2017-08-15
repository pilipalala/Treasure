package com.wyj.treasure.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotFixActivity extends AppCompatActivity {

    @BindView(R.id.tvShow)
    TextView tvShow;

    @Override
//    @Modify
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix);
        ButterKnife.bind(this);

        tvShow.setText(getText());//加载错误的代码
//        tvShow.setText(getInfo());//加载正确的代码
    }


    private String getText(){
        return "Hot-Fix, this just an error";
    }


//    @Add
//    public String getInfo(){
//        return "Hot-Fix, 已经对含有error的代码进行了修改！";
//    }
}
