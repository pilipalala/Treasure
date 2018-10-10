package com.wyj.mvp.ui.activity;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.wyj.mvp.entity.douban.DouBanBook;
import com.wyj.mvp.presenter.BookPresenter;
import com.wyj.mvp.view.BookView;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author wangyujie
 * @date 2018/9/28.10:07
 * @describe https://api.douban.com/v2/book/search?q=%E9%87%91%E7%93%B6%E6%A2%85&tag=&start=0&count=1
 */
public class DouBanActivity extends BaseActivity implements BookView {
    @BindView(R.id.cet_bookName)
    ClearEditText mCetBookName;
    @BindView(R.id.tv_result)
    TextView tvResult;

    private BookPresenter mBookPresenter;

    @Override
    protected int initView() {
        return R.layout.activity_dou_ban;
    }

    @Override
    protected void initData() {
        tvResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        mBookPresenter = new BookPresenter(this);
        mBookPresenter.onCreate();
        mBookPresenter.attachView(this);

    }

    @OnClick(R.id.btn_getBook)
    public void onViewClicked() {
        String bookName = mCetBookName.getText().toString().trim();
        if (!TextUtils.isEmpty(bookName)) {
            mBookPresenter.getSearchBook(bookName, null, 0, 1);
        }
    }

    @Override
    public void onSuccess(DouBanBook book) {
        tvResult.setText(book.toString());
    }

    @Override
    public void onError(String result) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBookPresenter.onStop();
    }
}
