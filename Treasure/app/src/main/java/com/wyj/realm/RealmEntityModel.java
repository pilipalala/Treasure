package com.wyj.realm;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author wangyujie
 * @date 2018/11/2.17:26
 * @notes
 * 除了直接继承于 RealmObject 来声明 Realm 数据模型之外，
 * 还可以通过实现 RealmModel 接口并添加 @RealmClass 修饰符来声明。
 */
@RealmClass
public class RealmEntityModel implements RealmModel {

    //@PrimaryKey——表示该字段是主键
    @PrimaryKey
    private String id;

    //@Required——表示该字段非空
    //使用@Required可用于用于强行要求其属性不能为空，
    //只能用于Boolean, Byte, Short, Integer, Long, Float, Double, String, byte[] 和 Date
    @Required
    private String name;

    //@Ignore——表示忽略该字段
    //被添加@Ignore标签后，存储数据时会忽略该字段。
    @Ignore
    private int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
