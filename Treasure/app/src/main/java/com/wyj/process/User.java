package com.wyj.process;

import java.io.Serializable;

/**
 * @author wangyujie
 *         on 2018/3/1.16:47
 *         TODO
 */

public class User implements Serializable{
    private String name;
    private int age;
    private boolean sex;

    public User(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
