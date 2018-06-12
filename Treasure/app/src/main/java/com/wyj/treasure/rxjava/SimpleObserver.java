package com.wyj.treasure.rxjava;

import java.util.Observable;
import java.util.Observer;

public class SimpleObserver implements Observer {


    public  SimpleObserver(SimpleObservable observable) {
        observable.addObserver(this);
    }
    //当 被观察者 状态发生改变的时候会调用
    @Override
    public void update(Observable observable, Object object) {
        System.out.println("data is changed:"+((SimpleObservable)observable).getData());
    }
}
