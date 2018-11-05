package com.wyj.gifview;


import android.view.View;
import android.view.ViewGroup;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class GifPlayActivity extends BaseActivity {

    @BindView(R.id.gif_view)
    GifView gifView;



    @Override
    protected int getContentViewID() {
        return R.layout.activity_gif_play;
    }

    @Override
    protected void initData() {
        gifView.setGifResource(R.mipmap.gif);
        ViewGroup.LayoutParams layoutParams = gifView.getLayoutParams();
        layoutParams.width = CommonUtils.getScreenWidth(this);
        gifView.setLayoutParams(layoutParams);
        gifView.play();

    }

    @OnClick({R.id.gif_play, R.id.gif_pause})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gif_play:
                if (gifView.isPaused()) {
                    gifView.play();
                }
                break;
            case R.id.gif_pause:
                if (gifView.isPlaying()) {
                    gifView.pause();
                }
                break;
        }
    }
}
