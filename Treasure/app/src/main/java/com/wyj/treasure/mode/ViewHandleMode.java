package com.wyj.treasure.mode;

/**
 * Created by wangyujie
 * on 2017/10/27.13:16
 * TODO
 */

public class ViewHandleMode {
    private String name;
    private String time;
    private String lastMsg;

    public ViewHandleMode(String name, String time, String lastMsg, int logo) {
        this.name = name;
        this.time = time;
        this.lastMsg = lastMsg;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    private int logo;


}
