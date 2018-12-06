package com.wyj.dagger.mode;

import android.app.Application;
import android.content.Context;

import com.wyj.dagger.qualifier.AppForApplication;
import com.wyj.dagger.qualifier.AppForContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author wangyujie
 * @date 2018/11/27.11:15
 * @describe 添加描述
 */
@Module
public class AppMode {

    private final Application mApp;

    public AppMode(Application application) {
        mApp = application;
    }

    @Provides
    @AppForApplication
    @Singleton
    Application providesApp() {
        return mApp;
    }

    @AppForContext
    @Provides
    @Singleton
    Context providesContext() {
        return mApp;
    }

}
