package com.wyj.bannerviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.LogUtil;

/**
 * @author wangyujie
 *         on 2017/11/20.11:45
 *         TODO 自定义bannerview
 */

public class BannerView extends RelativeLayout {
    /**
     * 轮播的viewpager
     */
    private BannerViewPager mBannerVp;
    /**
     * 轮播的描述
     */
    private TextView mBannerDescTv;
    /**
     * 底部容器
     */
    private RelativeLayout mBottomView;
    /**
     * 轮播  点 的容器
     */
    private LinearLayout mDotContainerView;
    /**
     * 自定义的banneradapter
     */
    private BannerAdapter mAdapter;
    /**
     * 选中点的效果
     */
    private Drawable mIndicatorFocusDrawable;
    /**
     * 默认点的效果
     */
    private Drawable mIndicatorNormalDrawable;

    /**
     * 当前选中的点的位置
     */
    private int mCurrentPosition = 0;
    /**
     * 点的位置
     */
    private int mDotGravity = 1;
    /**
     * 点的大小
     */
    private int mDotSize = 5;
    /**
     * 底部颜色
     */
    private int mBottomColor = Color.TRANSPARENT;
    /**
     * 点的间距
     */
    private int mDdotDistance = 8;


    private Context mContext;

    /**
     * 宽高比
     */
    private float mWidthProportion = 4, mHeightProportion = 3;
    /**
     * 是否正在滑动
     */
    private boolean isDragging = false;


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //把布局加载到该view里面
        inflate(context, R.layout.ui_banner_layout, this);
        initAttribute(attrs);
        initView();
    }

    /**
     * 初始化自定义属性
     *
     * @param attrs
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        //点的颜色
        mIndicatorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (null == mIndicatorFocusDrawable) {
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mIndicatorNormalDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (null == mIndicatorNormalDrawable) {
            mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        //点的位置
        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);
        //点的大小
        mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize, dip2px(mDotSize));
        //点的间距
        mDdotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance, dip2px(mDdotDistance));
        //底部背景颜色
        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor, mBottomColor);
        //视图宽高比
        mWidthProportion = array.getFloat(R.styleable.BannerView_withProportion, mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion, mHeightProportion);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            int height = (int) (widthMeasureSpec * mHeightProportion / mWidthProportion);
            setMeasuredDimension(widthMeasureSpec, height);
        }
        // 动态指定宽高  计算高度
        int width = getMeasuredWidth();
        // 计算高度
        int height = (int) (width * mHeightProportion / mWidthProportion);
        // 指定宽高
        getLayoutParams().height = height;
        requestLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mBannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        mBannerDescTv = (TextView) findViewById(R.id.tv_banner_desc);
        mDotContainerView = (LinearLayout) findViewById(R.id.dot_container);
        mBottomView = (RelativeLayout) findViewById(R.id.rl_bottom);
        //设置底部背景色
        mBottomView.setBackgroundColor(mBottomColor);

    }

    public void setOnBannerItemClickListener(BannerViewPager.OnItemClickListener listener) {
        mBannerVp.setOnItemClickListener(listener);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        mBannerVp.setAdapter(adapter);
        //初始化点的指示器
        initDotIndicator();
        post(new Runnable() {
            @Override
            public void run() {

            }
        });


        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                /**
                 * 监听当前选中的位置
                 * */
                pageSelect(position);
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
                    Log.e("onTouchListener", "onTouch: " + "/拖拽");
                    isDragging = true;
                    mBannerVp.stopRoll();
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {//滑动
                    Log.e("onTouchListener", "onTouch: " + "/滑动");
                } else if (state == ViewPager.SCROLL_STATE_IDLE && isDragging) {//静止
                    isDragging = false;
                    Log.e("onTouchListener", "onTouch: " + "/静止");
                    mBannerVp.startRoll();
                }
            }
        });
        mBannerVp.setOnItemTouchListener(new BannerViewPager.OnItemTouchListener() {
            @Override
            public void onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        LogUtil.i("onTouchListener" + "onTouch: " + "按下");
                        mBannerVp.stopRoll();
                        break;
                    case MotionEvent.ACTION_UP://松开
                        LogUtil.i("onTouchListener" + "onTouch: " + "松开");
                        startRoll();
                        break;
                    case MotionEvent.ACTION_CANCEL://取消
                        /**
                         * 按下并滑动一段距离后会触发取消事件
                         * */
                        Log.e("onTouchListener", "onTouch: " + "取消");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void pageSelect(int position) {
        /**
         * 1.把之前的选中的点  设置为默认
         * */
        DotIndicatorView oldIndicatorView = (DotIndicatorView) mDotContainerView.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(mIndicatorNormalDrawable);


        /**
         * 2.把当前的点  设置为选中状态
         * */
        mCurrentPosition = position % mAdapter.getCount();
        DotIndicatorView currentIndicatorView = (DotIndicatorView) mDotContainerView.getChildAt(mCurrentPosition);
        currentIndicatorView.setDrawable(mIndicatorFocusDrawable);

        /**
         * 3.设置广告位描述
         * */
        mBannerDescTv.setText(mAdapter.getBannerDesc(mCurrentPosition));


    }

    /**
     * 初始化点的指示器
     */
    private void initDotIndicator() {
        /**
         * 设置点的位置
         * */
        mDotContainerView.setGravity(getDotGravity());

        /**
         * 获取轮播图的数量
         * */
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            /**
             * 设置圆点 的大小
             * */
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            params.leftMargin = mDdotDistance;
            if (i == 0) {
                //选中位置
                indicatorView.setDrawable(mIndicatorFocusDrawable);
            } else {
                //未选中的
                indicatorView.setDrawable(mIndicatorNormalDrawable);
            }
            indicatorView.setLayoutParams(params);
            mDotContainerView.addView(indicatorView);

        }
        mBannerDescTv.setText(mAdapter.getBannerDesc(0));
    }


    /**
     * 把dip转成px
     *
     * @param dip
     * @return
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip
                , getResources().getDisplayMetrics());
    }


    /**
     * 开始滚动
     */
    public void startRoll() {
        mBannerVp.startRoll();
    }

    /**
     * 获取点的位置
     *
     * @return
     */
    public int getDotGravity() {
        switch (mDotGravity) {
            case 0:
                return Gravity.CENTER;
            case 1:
                return Gravity.RIGHT;
            case -1:
                return Gravity.LEFT;
            default:
                return Gravity.RIGHT;
        }
    }

    /**
     * 隐藏页面指示器
     */
    public void hidePageIndicator() {
        mDotContainerView.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示页面指示器
     */
    public void showPageIndicator() {
        mDotContainerView.setVisibility(View.VISIBLE);
    }
}
