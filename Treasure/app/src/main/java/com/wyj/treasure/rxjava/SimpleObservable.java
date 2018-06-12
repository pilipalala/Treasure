package com.wyj.treasure.rxjava;

import java.util.Observable;

/**
 *创建一个被观察者
 */
public class SimpleObservable extends Observable {

    private int data = 0;

    public void setData(int data) {
        if (this.data != data) {
            this.data = data;
            setChanged();//发生改变
            notifyObservers();//通知观察者，表示状态发生改变
        }


    }

    public int getData() {
        return data;

    }
}
