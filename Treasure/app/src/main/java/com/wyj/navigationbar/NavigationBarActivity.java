package com.wyj.navigationbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.ToastUtil;

public class NavigationBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        DefaultNavigationBar builder = new DefaultNavigationBar.Builder(this, (ViewGroup) findViewById(R.id.view_group))
                .setTitle("标题")
                .setRightTitle("右边")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show("右边");
                    }
                })
                .builder();
    }
}
