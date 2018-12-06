package com.wyj.dagger.component;

import com.wyj.dagger.DaggerActivity;
import com.wyj.dagger.PerActivity;
import com.wyj.dagger.mode.ActivityModule;

import dagger.Component;

/**
 * @author yujie
 * @date on 2018/7/27
 * @describe 编写的这个 Component 需要用 @Component 注解来标识，
 * 同时声明了 modules 为上面编写的 ActivityComponent ,然后提供了一个方法，
 * 叫做 inject，用来在 Activity 中注入
 * <p>
 * dependencies 属性就是依赖方式的具体实现<>添加了上层依赖需要依赖 APPComponent 的 DaggerUser</>
 * <p>
 * 搭建@Inject和@Module的桥梁,从@Module中获取依赖并将依赖注入给@Inject。
 **/
@PerActivity
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(DaggerActivity daggerActivity);
}
