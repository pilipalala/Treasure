package com.wyj.treasure.reflect;

import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

/**
 * Created by admin
 * on 2017/8/22.
 * TODO
 */

public class SonClass extends FatherClass {


    public int mSonAge = 10;
    private String mSonName = "pilipalaKing";

    public void printSonMsg() {
        ToastUtil.show("Son Msg - name:" + mSonName + "; age:" + mSonAge);
        LogUtil.e("Son Msg - name:" + mSonName + "; age:" + mSonAge);
    }

    private String getmSonName() {
        return mSonName;
    }

    private void setmSonName(String mSonName) {
        this.mSonName = mSonName;
    }


    private int getmSonAge() {
        return mSonAge;
    }

    private void setmSonAge(int mSonAge) {
        LogUtil.e("Son Msg - setmSonAge:" + mSonName + "; age:" + mSonAge);
        this.mSonAge = mSonAge;
    }
}
