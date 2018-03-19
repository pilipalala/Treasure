package com.wyj.index;

/**
 * Created by wangyujie
 * Date 2017/6/4
 * Time 16:12
 * TODO
 */

public class Person {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
    public Person(String name) {
        this.name = name;
        this.pinyin = PinYinUtils.getPinYin(name);
    }

    private String name;
    private String pinyin;
}
