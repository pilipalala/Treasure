package com.wyj.piestyle;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wyj.treasure.R;

public class PieStyleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_style);
    }

    public void style1(View view) {
        startActivity(new Intent(this, Style1Activity.class));
    }

    public void style2(View view) {
        startActivity(new Intent(this, Style2Activity.class));

    }

    public void style3(View view) {
        startActivity(new Intent(this, Style3Activity.class));

    }

    public void pei1(View view) {
        startActivity(new Intent(this, PieStyle1Activity.class));

    }

    public void pei2(View view) {
        startActivity(new Intent(this, PieStyle2Activity.class));

    }

    public void pei3(View view) {
        startActivity(new Intent(this, PieStyle3Activity.class));

    }
}
