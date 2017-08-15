package com.wyj.treasure.mode;

/**
 * Created by admin
 * on 2017/8/14.
 * TODO
 */

public class ItemInfo {
    private String item;
    private Class clz;
    private int icon;


    public ItemInfo(String item, Class clz, int icon) {
        this.item = item;
        this.clz = clz;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }




    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }

    @Override
    public String toString() {
        return item;
    }
}
