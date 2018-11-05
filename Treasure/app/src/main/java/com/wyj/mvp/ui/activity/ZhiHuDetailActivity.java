package com.wyj.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyj.mvp.JavascriptInterface;
import com.wyj.mvp.contract.ZhiHuDetailsContract;
import com.wyj.mvp.entity.zhihu.NewsInfo;
import com.wyj.mvp.entity.zhihu.ZhiHuDetails;
import com.wyj.mvp.presenter.ZhiHuDetailsPresenter;
import com.wyj.treasure.MyApplication;
import com.wyj.treasure.R;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.GlideUtils;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.BindView;

public class ZhiHuDetailActivity extends MVPBaseActivity<ZhiHuDetailsPresenter> implements ZhiHuDetailsContract.View {
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.wv_detail)
    WebView wvDetail;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_image_source)
    TextView tvImageSource;
    @BindView(R.id.head_layout)
    RelativeLayout headLayout;
    @BindView(R.id.cv_zhihu)
    CardView cvZhiHu;
    @BindView(R.id.fab_zhihu)
    FloatingActionButton fabZhihu;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private static String PARAM_ID = "PARAM_ID";

    private String newsId;
    private NewsInfo newsInfo;
    private String title;
    private boolean isCollect;
    private String imagePath;

    @Override
    public boolean isStartAnimation() {
        return false;
    }

    @Override
    protected int getContentViewID() {
        return EMPTY_VIEW;
    }

    @Override
    protected int contentView() {
        return R.layout.activity_zhi_hu_detail;
    }

    @Override
    protected void initData() {
        newsId = getIntent().getStringExtra(PARAM_ID);
        if (TextUtils.isEmpty(newsId)) {
            ToastUtil.show("newsInfo == null");
            return;
        }

        newsInfo = mPresenter.getNewsInfo(newsId);
        if (newsInfo != null) {
            isCollect = true;
            fabZhihu.setImageResource(R.mipmap.icon_collect_select);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivDetail.setTransitionName(newsId + "pic");
            tvTitle.setTransitionName(newsId + "title");
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_top_back);
        }
        toolbar.setNavigationOnClickListener(v -> close());
        initWebView();

        initToolbarLayout();
        getNewsDetails(newsId);
    }

    @Override
    protected ZhiHuDetailsPresenter createPresenter() {
        return new ZhiHuDetailsPresenter();
    }


    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        wvDetail.getSettings().setJavaScriptEnabled(true);
        wvDetail.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
        wvDetail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String url) {
                webView.getSettings().setJavaScriptEnabled(true);
                super.onPageFinished(webView, url);
                addImageClickListener();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageStarted(view, url, favicon);
            }
        });
        wvDetail.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
        wvDetail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setVisibility(newProgress == 100 ? View.INVISIBLE : View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
        });
    }


    // 注入js函数监听
    private void addImageClickListener() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，
        //函数的功能是在图片点击的时候调用本地java接口并传递url过去
        wvDetail.loadUrl("javascript:(function(){" +
                "var imgs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<imgs.length;i++)  " +
                "{"
                + "    imgs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    public void getNewsDetails(String id) {
        mPresenter.getNewsDetails(id);
    }

    private void initToolbarLayout() {
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
        ViewGroup.LayoutParams params = appBar.getLayoutParams();
        params.width = CommonUtils.getScreenWidth(this);
        params.height = (CommonUtils.getScreenWidth(this) * 3) / 4;
        appBar.setLayoutParams(params);
        appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset <= -headLayout.getHeight() / 2) {
                collapsingToolbar.setTitle(title);
            } else {
                collapsingToolbar.setTitle("");
            }
        });
        fabZhihu.setOnClickListener(v -> {
            isCollect = !isCollect;
            fabZhihu.setImageResource(isCollect ? R.mipmap.icon_collect_select : R.mipmap.icon_collect_normal);
            if (!isCollect) {
                mPresenter.deleteNews(newsInfo);
            } else {
                NewsInfo dao = new NewsInfo(null, newsId, imagePath, title);
                mPresenter.insert(dao);
            }
            ToastUtil.show(isCollect ? "收藏成功" : "取消收藏");
        });
    }

    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wvDetail.destroy();
    }


    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    public static void start(Context context, String newsId, Bundle bundle) {
        Intent intent = new Intent(context, ZhiHuDetailActivity.class);
        intent.putExtra(PARAM_ID, newsId);
        ActivityCompat.startActivity(context, intent, bundle);
    }

    @Override
    public void onDetailSuccess(ZhiHuDetails zhiHuDetails) {
        title = zhiHuDetails.getTitle();
        imagePath = zhiHuDetails.getImage();
        tvTitle.setText(title);
        wvDetail.loadDataWithBaseURL(null, getHtmlData(zhiHuDetails.getBody()),
                "text/html", "UTF-8", null);
        tvImageSource.setText("图片:" + zhiHuDetails.getImage_source());
        GlideUtils.loadImageUrl(MyApplication.getContext(), imagePath, ivDetail);
        LogUtil.d("onNext");
    }

    @Override
    public void onComplete() {
        //完成时
        LogUtil.d("onComplete");
        cvZhiHu.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        //异常时
        LogUtil.d(message);
        ToastUtil.show(message);
    }
}
