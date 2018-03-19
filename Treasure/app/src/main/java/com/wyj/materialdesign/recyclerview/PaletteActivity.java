package com.wyj.materialdesign.recyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaletteActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @BindView(R.id.main_vp_container)
    ViewPager mainVpContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mainVpContainer.setAdapter(new PaletteAdapter(getSupportFragmentManager(), this));
        toolbarTab.setupWithViewPager(mainVpContainer);

        changeTopBgColor(0);

        mainVpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTopBgColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * 根据Palette提取的颜色，修改tab和toolbar以及状态栏的颜色
     *
     * @param position
     */
    private void changeTopBgColor(int position) {
            /*用来提取颜色的Bitmap*/
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), PaletteFragment.getBackgroundBitmapPosition(position));

            /*Palette部分*/
        Palette.Builder builder = Palette.from(bitmap);
        /*Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

            }
        });*/


        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                /*获取到充满活力的这种色调*/
                Palette.Swatch vibrant = palette.getVibrantSwatch();//有活力
                Palette.Swatch c = palette.getDarkVibrantSwatch();//有活力 暗色
                Palette.Swatch d = palette.getLightVibrantSwatch();//有活力 亮色
                Palette.Swatch f = palette.getMutedSwatch();//柔和
                Palette.Swatch a = palette.getDarkMutedSwatch();//柔和 暗色
                Palette.Swatch b = palette.getLightMutedSwatch();//柔和 亮色

                int color1 = vibrant.getBodyTextColor();//内容颜色
                int color2 = vibrant.getTitleTextColor();//标题颜色
                int color3 = vibrant.getRgb();//rgb颜色


                    /*根据调色板Palette获取到图片中的颜色设置到Toolbar和tab中背景*/
                if (vibrant == null) return;
                toolbarTab.setBackgroundColor(vibrant.getRgb());
                toolbarTab.setSelectedTabIndicatorColor(colorBurn(vibrant.getRgb()));
                toolbar.setBackgroundColor(vibrant.getRgb());
                if (Build.VERSION.SDK_INT > 21) {
                    Window window = getWindow();
                    window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                    window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
                }
            }
        });
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }
}
