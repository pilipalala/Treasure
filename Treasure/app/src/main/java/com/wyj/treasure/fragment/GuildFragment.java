package com.wyj.treasure.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyj.treasure.R;
import com.wyj.treasure.widget.CustomVideoView;

/**
 * Created by admin
 * on 2017/8/30.
 * TODO
 */

public class GuildFragment extends Fragment {
    Uri uri;
    private CustomVideoView customVideoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        customVideoView = new CustomVideoView(getContext());
        /**获取参数，根据不同的参数播放不同的视频**/
        int index = getArguments().getInt("index");
        switch (index) {
            case 0:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_1);
                break;
            case 1:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_2);
                break;
            case 2:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_3);
                break;
            case 3:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_4);
                break;
        }
        /**播放视频**/
        customVideoView.playVideo(uri);
        return customVideoView;
    }

    /**
     * 记得在销毁的时候让播放的视频终止
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (customVideoView != null) {
            customVideoView.stopPlayback();
        }
    }
}
