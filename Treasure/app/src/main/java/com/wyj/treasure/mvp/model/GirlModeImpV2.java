package com.wyj.treasure.mvp.model;

import com.wyj.treasure.mvp.bean.Girl;
import com.wyj.treasure.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyujie
 * on 2017/10/17.17:14
 * TODO
 */

public class GirlModeImpV2 implements IGirlModel{

    @Override
    public void loadGirl(GirlOnLoadListener loadListener) {

        List<Girl> data = new ArrayList<>();
        data.add(new Girl("1颗星", "", R.mipmap.avatar1));
        data.add(new Girl("3颗星", "", R.mipmap.avatar2));
        data.add(new Girl("5颗星", "", R.mipmap.avatar3));
        data.add(new Girl("2颗星", "", R.mipmap.taeyeon_one));
        data.add(new Girl("4颗星", "", R.mipmap.taeyeon_two));
        data.add(new Girl("5颗星", "", R.mipmap.taeyeon_three));
        /*回调监听*/
        loadListener.onComplete(data);

    }
}
