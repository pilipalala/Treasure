package com.wyj.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wyj.treasure.MyApplication;
import com.wyj.treasure.R;
import com.wyj.treasure.utils.LogUtil;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author wangyujie
 * @date 2018/8/15.14:41
 * @describe 依赖注入（Dependency Injection）是用于实现控制反转（Inversion of Control）的最常见的方式之一
 * <p>
 * 首先我们在Activity里注入(Inject) Presenter
 * <p>
 * 第一步 编写Module(ApiModule、ActivityModule)
 * 类需要用@Module来标明注解
 * 这里有一点规则，用@Provides注解的函数需要以provide开头，然后后面接什么内容都可以，看自己喜欢，
 * 事实上，经过我的测试，我把provideActivity()改成provideA()同样是可以注入成功的，所以大家可以知道，
 * 这里是根据返回值类型来标识的，方法名并不重要，只需要保证以provide开头即可。
 * <p>
 * 第二步 编写ActivityComponent(AppComponent、ActivityComponent)
 * 编写的这个Component需要用@Component注解来标识，同时声明了modules为上面编写的 (ApiModule、ActivityModule),
 * 然后提供了一个方法，叫做inject，用来在Activity中注入。(这里为什么要写一个方法叫做inject我暂时还没弄清楚，
 * 改名字是可以的，但是参数类型不能改，并且一定要指定module=ActivityModule才能注入)，
 * 这里我们暂且理解为提供一个方法来注入对象吧。
 * <p>
 * 第三步：AndroidStudio -> Build -> Make Project
 * <p>
 * <p>
 * 总结
 * Inject，Component，Module，Provides是dagger2中的最基础最核心的知识点。
 * 奠定了dagger2的整个依赖注入框架。
 * <p>
 * 1、Inject主要是用来标注目标类的依赖和依赖的构造函数
 * 2、Component它是一个桥梁，一端是目标类，另一端是目标类所依赖类的实例，
 * 它也是注入器（Injector）负责把目标类所依赖类的实例注入到目标类中，同时它也管理Module。
 * 3、Module和Provides是为解决第三方类库而生的，Module是一个简单工厂模式，
 * Module可以包含创建类实例的方法，这些方法用Provides来标注
 */
public class DaggerActivity extends AppCompatActivity {
    @Inject
    DaggerPresenter presenter;
    @Inject
    OkHttpClient client;
    @Inject
    Retrofit retrofit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        inject();
        presenter.showUserName();
        LogUtil.i("client = " + (client == null ? "null" : client));
        LogUtil.i("getApi = " + (retrofit == null ? "null" : retrofit));
    }

    //第四步，注入Activity中
    private void inject() {
        AppComponent appComponent = ((MyApplication) getApplication()).getAppComponent();
        DaggerActivityComponent.builder()//DaggerMainComponent 与 ActivityModule的实例绑定
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))//activityModule 该方法名和 ActivityModule 是有关的，编译时生成
                .build()
                .inject(this);//该方法就是 AppComponent 接口的inject(),传入当前 Activity 的引用
    }

    public void showUserName(String userName) {
        LogUtil.i("userName = " + userName);
    }
}
