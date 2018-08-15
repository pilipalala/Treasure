package com.wyj.dagger;

import dagger.Component;

/**
 * @author yujie
 * @date on 2018/7/27
 * @describe 编写的这个Component需要用@Component注解来标识，
 * 同时声明了modules为上面编写的ActivityComponent,然后提供了一个方法，
 * 叫做inject，用来在Activity中注入
 **/
@PerActivity
@Component(modules = ActivityModule.class,dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(DaggerActivity daggerActivity);
}
