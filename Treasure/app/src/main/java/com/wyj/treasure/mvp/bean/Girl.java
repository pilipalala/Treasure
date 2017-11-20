package com.wyj.treasure.mvp.bean;

/**
 * Created by wangyujie
 * on 2017/10/17.16:11
 * TODO
 */

public class Girl {
    private String name;
    private String info;
    private int photo;

    public Girl(String name, String info, int photo) {
        this.name = name;
        this.info = info;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
