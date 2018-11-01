package com.wyj.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * @author yujie
 * @date on 2018/7/27
 * @describe 来源 https://blog.csdn.net/u012943767/article/details/51897247
 **/
@Module
public class ActivityModule {

    ///////////////////////////////////////////////////////////////////////////
    // Module的作用是用来提供生成依赖对象的，
    //      比如我要注入DaggerPresenter，
    //      那么这个Module的作用就是需要生成一个DaggerPresenter的对象，
    //      来让Dagger2注入到DaggerActivity中
    ///////////////////////////////////////////////////////////////////////////
    private DaggerActivity activity;

    public ActivityModule(DaggerActivity activity) {
        this.activity = activity;
    }

    //用@Provides注解的函数需要以provide开头，后面接什么内容都可以
    @Provides
    public DaggerActivity provideActivity() {
        return activity;
    }

    @Provides
    public DaggerPresenter provideDaggerPresenter(DaggerActivity activity, DaggerUser user) {
        return new DaggerPresenter(activity, user);
    }
}
