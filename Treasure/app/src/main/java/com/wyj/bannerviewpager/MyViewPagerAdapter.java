package com.wyj.bannerviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/11.
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private String TAG = GuangGaoTiaoActivity.class.getName();
    private ArrayList<ImageView> imageViewList;
    private Context context;
    private setTouchListener touchListener;
    private setClickListener clickListener;

    public interface setTouchListener {
        public void onTouch(View view, MotionEvent motionEvent);
    }

    public interface setClickListener {
        public void onClick(View view, int position);
    }

    public MyViewPagerAdapter(Context context, ArrayList<ImageView> imageViewList) {
        this.context = context;
        this.imageViewList = imageViewList;
    }

    /**
     * 相当于getView方法
     *
     * @param container viewPager自身
     * @param position  当前实例化页面的位置
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.e(TAG, "instantiateItem--->position: " + position + "--->container" + container);
        ImageView imageView = imageViewList.get(position % imageViewList.size());
        container.addView(imageView); //添加到ViewPager中
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view, position % imageViewList.size());
            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                touchListener.onTouch(view, motionEvent);
                return false;
            }
        });

        return imageView;
    }

    public void onTouchListener(setTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    public void onClickListener(setClickListener clickListener) {
        this.clickListener = clickListener;

    }


    @Override
    public int getCount() {
        return imageViewList == null ? 0 : Integer.MAX_VALUE;
    }

    /**
     * 比较view和object 是否是同一个示例
     *
     * @param view   页面
     * @param object instantiateItem方法返回的结果
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 释放资源
     *
     * @param container viewPager
     * @param position  要释放的位置
     * @param object    要释放的页面
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.e(TAG, "destroyItem--->position: " + position + "--->object" + object);
        container.removeView((ImageView) object);
    }

}
