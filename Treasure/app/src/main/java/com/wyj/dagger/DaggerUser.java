package com.wyj.dagger;

/**
 * @author yujie
 * @date on 2018/7/27
 * @describe TODO
 **/
public class DaggerUser {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public DaggerUser(String name) {
        this.name = name;
    }
}
