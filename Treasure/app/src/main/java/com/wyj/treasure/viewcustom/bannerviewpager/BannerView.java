package com.wyj.treasure.viewcustom.bannerviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyj.treasure.R;

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
     * 底部容器
     */
    private RelativeLayout mBottomView;
    /**
     * 宽高比
     * */
    private float mWithProportion = 4;
    private float mHeightProportion = 3;
    private int width;

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
        getMeasuredWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeightProportion == 0 || mWithProportion == 0) {
            return;
        }

        width = MeasureSpec.getSize(widthMeasureSpec);

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
        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor, mBottomColor);
        mWithProportion = array.getFloat(R.styleable.BannerView_withProportion, mWithProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion, mHeightProportion);
        array.recycle();
    }
    /**
     * 点的大小 点的颜色 点的位置 点的间距
     * */

    /**
     * 初始化view
     */
    private void initView() {
        mBannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        mBannerDescTv = (TextView) findViewById(R.id.tv_banner_desc);
        mDotContainerView = (LinearLayout) findViewById(R.id.dot_container);
        mBottomView = (RelativeLayout) findViewById(R.id.rl_bottom);
        mBottomView.setBackgroundColor(mBottomColor);
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
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                /**
                 * 监听当前选中的位置
                 * */
                pageSelect(position);
            }
        });
        getMeasuredWidth();
        int height = (int) ( width * mHeightProportion / mWithProportion);
        //指定宽高
        getLayoutParams().height = height;
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
}
