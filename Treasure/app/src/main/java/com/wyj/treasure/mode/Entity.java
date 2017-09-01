package com.wyj.treasure.mode;

/**
 * Created by admin
 * on 2017/8/31.
 * TODO 时间多选mode
 */

public class Entity {
    private String time;
    private boolean isSelect;

    public Entity(String time, boolean isSelect) {
        this.time = time;
        this.isSelect = isSelect;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }
}
