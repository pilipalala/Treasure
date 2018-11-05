package com.wyj.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author wangyujie
 * @date 2018/11/2.15:13
 * @describe 添加描述
 */
public class RealmEntity extends RealmObject {
    @PrimaryKey
    private String id;

    private String name;
    private int age;

    private RealmList<RealmEntityModel> realList;

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

    public RealmList<RealmEntityModel> getRealList() {
        return realList;
    }

    public void setRealList(RealmList<RealmEntityModel> realList) {
        this.realList = realList;
    }
}
