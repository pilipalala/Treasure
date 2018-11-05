package com.wyj.spannable;


import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.BindView;

import static android.text.style.DynamicDrawableSpan.ALIGN_BASELINE;

/**
 * @author wangyujie
 * @date 2018/10/16.11:52
 * @describe
 * https://www.cnblogs.com/qynprime/p/8026672.html
 * https://blog.csdn.net/baidu_31956557/article/details/78339071
 * https://blog.csdn.net/snowdream86/article/details/6776629
 */
public class SpannableStringActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView mTvResult;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_spannable_string;
    }


    /**
     * setSpan(Object what, int start, int end, int flags)方法需要用户输入四个参数，
     * what表示设置的格式是什么，可以是前景色、背景色也可以是可点击的文本等等，
     * start表示需要设置格式的子字符串的起始下标，索引从0开始
     * 同理end表示终了下标，
     * flags属性就有意思了，共有四种属性：
     * <p>
     * Spanned.SPAN_INCLUSIVE_EXCLUSIVE 从起始下标到终了下标，包括起始下标
     * Spanned.SPAN_INCLUSIVE_INCLUSIVE 从起始下标到终了下标，同时包括起始下标和终了下标
     * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 从起始下标到终了下标，但都不包括起始下标和终了下标
     * Spanned.SPAN_EXCLUSIVE_INCLUSIVE 从起始下标到终了下标，包括终了下标
     */
    @Override
    protected void initData() {


    }

    /**
     * 1.ForegroundColorSpan
     * 为文本设置前景色，效果和TextView的setTextColor(View view)类似
     */
    public void ForegroundColor(View view) {
        SpannableString spannableString = new SpannableString("设置文字的前景色为淡蓝色");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        spannableString.setSpan(colorSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(spannableString);
    }

    /**
     * 2.BackgroundColorSpan
     * 为文本设置背景色，效果和TextView的setBackground()类似
     */
    public void BackgroundColor(View view) {
        SpannableString spannableString = new SpannableString("设置文字的背景色为淡绿色");
        BackgroundColorSpan backgroundColor = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
        ForegroundColorSpan foregroundColor = new ForegroundColorSpan(Color.GRAY);
        spannableString.setSpan(backgroundColor, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColor, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(spannableString);
    }

    /**
     * 3.RelativeSizeSpan 相对大小 （相对值,单位：像素） 参数表示为默认字体大小的多少倍
     * 设置文字相对大小，在 TextView 原有的文字大小的基础上，相对设置文字大小
     */
    public void RelativeSize(View view) {
        SpannableString spannableString = new SpannableString("万丈高楼平地起");

        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(1.2f);
        RelativeSizeSpan sizeSpan02 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpan03 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan04 = new RelativeSizeSpan(1.8f);
        RelativeSizeSpan sizeSpan05 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan06 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpan07 = new RelativeSizeSpan(1.2f);

        spannableString.setSpan(sizeSpan01, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan02, 1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan03, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan04, 3, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan05, 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan06, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan07, 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(spannableString);

    }

    /**
     * AbsoluteSizeSpan 绝对大小  (绝对值,单位：像素）
     * 第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
     */
    public void AbsoluteSizeSpan(View view) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("万丈高楼平地起");

        AbsoluteSizeSpan sizeSpan01 = new AbsoluteSizeSpan(20);
        AbsoluteSizeSpan sizeSpan02 = new AbsoluteSizeSpan(20, true);

        stringBuilder.setSpan(sizeSpan01, 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(sizeSpan02, 4, stringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(stringBuilder);
    }

    /**
     * 设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍
     */
    public void ScaleXSpan(View view) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("为文字设置宽度");
        //2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变
        ScaleXSpan scaleXSpan = new ScaleXSpan(2.0f);
        stringBuilder.setSpan(scaleXSpan, 5, stringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * 4.StrikethroughSpan
     * 为文本设置中划线，也就是常说的删除线
     */
    public void StrikethroughSpan(View view) {
        SpannableString spannableString = new SpannableString("为文字设置删除线");
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(spannableString);
    }

    /**
     * 5.UnderlineSpan
     * 为文本设置下划线
     */
    public void Underline(View view) {
        SpannableString spannableString = new SpannableString("为文字设置下划线");
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(spannableString);
    }

    /**
     * 6.SuperscriptSpan
     * 设置上标
     */
    public void Superscript(View view) {
        SpannableString spannableString = new SpannableString("为文字设置上标");
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        spannableString.setSpan(superscriptSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(spannableString);
    }

    /**
     * 7.SubscriptSpan
     * 设置下标，功能与设置上标类似
     */
    public void Subscript(View view) {
        SpannableString spannableString = new SpannableString("为文字设置下标");
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        spannableString.setSpan(subscriptSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(spannableString);
    }

    /**
     * 8.StyleSpan
     * 为文字设置风格（粗体、斜体），和TextView属性textStyle类似
     */
    public void StyleSpan(View view) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("为文字设置正常粗体斜体粗斜体风格");
        StyleSpan styleSpan_N = new StyleSpan(Typeface.NORMAL);//正常
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);//粗体
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);//斜体
        StyleSpan styleSpan_BI = new StyleSpan(Typeface.BOLD_ITALIC);//粗斜体
        stringBuilder.setSpan(styleSpan_N, 5, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(styleSpan_B, 7, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(styleSpan_I, 9, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(styleSpan_BI, 11, 14, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(stringBuilder);
    }

    /**
     * 9.ImageSpan
     * 设置文本图片
     * 这一个是不是很炫酷？再加一个解析算法，将文本中特定的文本转换成特定的表情图片，分分钟实现聊天表情显示效果有木有啊朋友们！
     */
    public void ImageSpan(View view) {
        SpannableString spannableString = new SpannableString("在文本中添加表情");
        Drawable drawable = getResources().getDrawable(R.mipmap.avatar4);
        drawable.setBounds(0, 0, 30, 30);
        ImageSpan imageSpan = new ImageSpan(drawable, ALIGN_BASELINE);
        spannableString.setSpan(imageSpan, 6, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(spannableString);
    }


    /**
     * 10.ClickableSpan
     * 设置可点击的文本，设置这个属性的文本可以相应用户点击事件，至于点击事件用户可以自定义
     * 注意：使用ClickableSpan的文本如果想真正实现点击作用，必须为TextView设置setMovementMethod方法，
     * 否则没有点击相应，至于setHighlightColor方法则是控制点击是的背景色。
     */
    public void ClickableSpan(View view) {
        SpannableString spannableString = new SpannableString("为文字设置点击事件");
        MyClickableSpan clickableSpan = new MyClickableSpan("https://www.jianshu.com/u/e911b256a8cf");
        spannableString.setSpan(clickableSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setMovementMethod(LinkMovementMethod.getInstance());
        mTvResult.setHighlightColor(Color.parseColor("#36969696"));
        mTvResult.setText(spannableString);
    }

    /**
     * 11.URLSpan
     * 设置超链接文本，
     * new URLSpan("tel:4155551212")    //电话
     * new URLSpan("mailto:wangyujiew@sina.com")    //邮件
     * new URLSpan("https://www.jianshu.com/u/e911b256a8cf")    //网络
     * new URLSpan("sms:4155551212")    //短信   使用sms:或者smsto:
     * new URLSpan("mms:4155551212")    //彩信   使用mms:或者mmsto
     * new URLSpan("geo:38.899533,-77.036476")  //地图
     */
    public void URLSpan(View view) {
        SpannableString spannableString = new SpannableString("为文字设置超链接");
        URLSpan urlSpan = new URLSpan("https://www.jianshu.com/u/e911b256a8cf");
        spannableString.setSpan(urlSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setMovementMethod(LinkMovementMethod.getInstance());
        mTvResult.setHighlightColor(Color.parseColor("#36969696"));
        mTvResult.setText(spannableString);
    }

    /**
     * 12.MaskFilterSpan
     * 可以实现模糊和浮雕效果
     */
    public void MaskFilterSpan(View view) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("为文字设置模糊和浮雕效果");
        MaskFilter filter = new BlurMaskFilter(4.0f, BlurMaskFilter.Blur.OUTER);
        MaskFilterSpan maskFilterSpan = new MaskFilterSpan(filter);
        stringBuilder.setSpan(maskFilterSpan, 5, stringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvResult.setText(stringBuilder);
    }


    class MyClickableSpan extends ClickableSpan {

        private String content;

        public MyClickableSpan(String content) {
            this.content = content;
        }

        /**
         * ds.setUnderlineText()控制是否让可点击文本显示下划线
         */
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            ToastUtil.show(content);
        }
    }
}
