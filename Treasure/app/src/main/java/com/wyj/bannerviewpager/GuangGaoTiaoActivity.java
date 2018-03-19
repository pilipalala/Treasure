package com.wyj.bannerviewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wyj.treasure.R;
import com.wyj.treasure.widget.MyToggleButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuangGaoTiaoActivity extends AppCompatActivity {

    // 图片资源ID
    private final int[] imageIds = {
            R.mipmap.ai_1, R.mipmap.ai_2,
            R.mipmap.ai_3, R.mipmap.ai_4,
            R.mipmap.ai_5};
    // 图片标题集合
    private final String[] imageDescriptions = {
            "壹!",
            "贰!",
            "叁!",
            "肆!",
            "伍!"
    };
    @BindView(R.id.gg_viewpager)
    ViewPager ggViewpager;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.ll_point)
    LinearLayout llPoint;
    MyViewPagerAdapter adapter;
    @BindView(R.id.toggleButton)
    MyToggleButton toggleButton;
    private ArrayList<ImageView> imageViewList;
    private int prePostion = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = ggViewpager.getCurrentItem() + 1;
            ggViewpager.setCurrentItem(item);

            /*延迟发消息*/
            handler.sendEmptyMessageDelayed(0, 4000);

        }
    };
    private boolean isDragging = false;


    /**
     * ViewPager的使用
     * 1、在布局文件中定义viewPager
     * 2、在代码中实例化ViewPager
     * 3、准备数据
     * 4、设置适配器（PagerAdapter）
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guang_gao_tiao);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        imageViewList = new ArrayList<ImageView>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);


        for (int i = 0; i < imageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(imageIds[i]);
            //添加数据
            imageViewList.add(image);

            //添加点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            if (i == 0) {
                point.setEnabled(true);//显示红色
            } else {
                point.setEnabled(false);//显示灰色
                params.leftMargin = 15;
            }
            point.setLayoutParams(params);
            llPoint.addView(point);
        }
        adapter = new MyViewPagerAdapter(this, imageViewList);
        //设置适配器（PagerAdapter）
        ggViewpager.setAdapter(adapter);

        /*设置中间位置*/
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViewList.size();//要保证是图片个数
        ggViewpager.setCurrentItem(item);

        text.setText(imageDescriptions[prePostion]);

        /*设置viewPager页面监听*/
        ggViewpager.addOnPageChangeListener(new MyViewPagerListener());

        adapter.onClickListener(new MyViewPagerAdapter.setClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(GuangGaoTiaoActivity.this, imageDescriptions[position], Toast.LENGTH_SHORT).show();

            }
        });

        adapter.onTouchListener(new MyViewPagerAdapter.setTouchListener() {
            @Override
            public void onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        Log.e("onTouchListener", "onTouch: " + "按下");
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_UP://离开
                        Log.e("onTouchListener", "onTouch: " + "离开");
                        handler.removeCallbacksAndMessages(null);
                        handler.sendEmptyMessageDelayed(0, 3000);
                        break;
                    case MotionEvent.ACTION_MOVE://移动
                        Log.e("onTouchListener", "onTouch: " + "移动");
                        break;
                    case MotionEvent.ACTION_CANCEL://取消
                        Log.e("onTouchListener", "onTouch: " + "取消");
//                        handler.removeCallbacksAndMessages(null);
//                        handler.sendEmptyMessageDelayed(0, 3000);
                        break;
                }
            }
        });

        /*发消息*/
        handler.sendEmptyMessageDelayed(0, 3000);


        toggleButton.setOnChangeListener(new MyToggleButton.OnChangeListener() {
            @Override
            public void OnChangeListener(boolean isOpen) {
                Toast.makeText(GuangGaoTiaoActivity.this, isOpen + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private class MyViewPagerListener implements ViewPager.OnPageChangeListener {
        /**
         * 当页面滚动了的时候回调这个方法
         *
         * @param position             当前页面的位置
         * @param positionOffset       滑动页面的百分比
         * @param positionOffsetPixels 在屏幕上滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当页面被选中了的时候
         *
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            //设置对应页面的文本信息
            text.setText(imageDescriptions[position % imageViewList.size()]);
            /*当前的设置为高亮*/
            llPoint.getChildAt(position % imageViewList.size()).setEnabled(true);
            /*把上一个设置为灰色*/
            llPoint.getChildAt(prePostion).setEnabled(false);

            prePostion = position % imageViewList.size();
        }

        /**
         * 当页面滚动状态变化的时候回调
         * 静止-->滑动
         * 滑动-->静止
         * 静止-->拖拽
         *
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {//拖拽
                handler.removeCallbacksAndMessages(null);
                Log.e("onTouchListener", "onTouch: " + "/拖拽");
                isDragging = true;
            } else if (state == ViewPager.SCROLL_STATE_SETTLING) {//滑动
                Log.e("onTouchListener", "onTouch: " + "/滑动");

            } else if (state == ViewPager.SCROLL_STATE_IDLE && isDragging) {//静止
                isDragging = false;
                Log.e("onTouchListener", "onTouch: " + "/静止");
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(0, 3000);
            }

        }
    }
}
