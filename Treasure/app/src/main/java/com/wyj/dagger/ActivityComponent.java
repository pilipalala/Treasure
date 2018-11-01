package com.wyj.dagger;

import dagger.Component;

/**
 * @author yujie
 * @date on 2018/7/27
 * @describe 编写的这个 Component 需要用 @Component 注解来标识，
 * 同时声明了 modules 为上面编写的 ActivityComponent ,然后提供了一个方法，
 * 叫做 inject，用来在 Activity 中注入
 * <p>
 * dependencies属性就是依赖方式的具体实现
 * <p>
 * 搭建@Inject和@Module的桥梁,从@Module中获取依赖并将依赖注入给@Inject。
 **/
@PerActivity
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(DaggerActivity daggerActivity);
}
