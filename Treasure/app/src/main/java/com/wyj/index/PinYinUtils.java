package com.wyj.index;

import android.text.TextUtils;
import android.util.Log;

import com.github.promeg.pinyinhelper.Pinyin;
import com.wyj.treasure.utils.LogUtil;


/**
 * Created by wangyujie
 * Date 2017/6/4.
 * TODO 把汉字转换成拼音
 */

public class PinYinUtils {
    /**
     * 得到指定汉字的拼音
     * 注意:不应该被频繁调用，它消耗一定内存
     *
     * @param hanzi
     * @return
     */
    public static String getPinYin(String hanzi) {
        StringBuffer pinYin = new StringBuffer();
        if (TextUtils.isEmpty(hanzi)) {
            return pinYin.toString();
        }
        for (int i = 0; i < hanzi.length(); i++) {
            char c = hanzi.charAt(i);
            if (Pinyin.isChinese(c)){
                LogUtil.i( "中文:"+Pinyin.toPinyin(c));
                pinYin.append(Pinyin.toPinyin(c));
            }else {
                LogUtil.i( "英文:"+Pinyin.toPinyin(c));
            }
        }
        LogUtil.i("pinYin.toString() "+pinYin.toString());
        return pinYin.toString();
    }
}
