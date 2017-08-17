package com.wyj.treasure.activity.transition;

import android.content.Context;

import com.wyj.treasure.utils.ResourceUtil;

/**
 * Created by admin
 * on 2017/8/17.
 * TODO
 */

public class Beauty {
    public String name;

    public String picName;

    public String works;

    public String role;

    public String[] pics;

    public String[] getPics() {
        return pics;
    }

    public void setPics(String[] pics) {
        this.pics = pics;
    }

    public Beauty(String name, String picName, String works, String role, String[] pics) {
        this.name = name;
        this.picName = picName;
        this.works = works;
        this.role = role;
        this.pics = pics;
    }

    public int getImageResourceId(Context context, String picName) {
        try {
            return ResourceUtil.getMipmapId(context, picName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getImageResourceId(Context context) {
        try {
            return ResourceUtil.getMipmapId(context, this.picName);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
