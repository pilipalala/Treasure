package com.wyj.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wyj.dagger.component.ActivityComponent;
import com.wyj.dagger.component.AppComponent;
import com.wyj.dagger.component.DaggerActivityComponent;
import com.wyj.dagger.mode.ActivityModule;
import com.wyj.treasure.MyApplication;
import com.wyj.treasure.R;
import com.wyj.treasure.utils.LogUtil;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author wangyujie
 * @date 2018/8/15.14:41
 * @describe https://blog.csdn.net/lisdye2/article/details/51942511
 * 依赖注入（Dependency Injection）是用于实现控制反转（Inversion of Control）的最常见的方式之一
 * <p>
 * 首先我们在Activity里注入(Inject) Presenter
 * <p>
 * 第一步 编写 Module(ApiModule、ActivityModule)
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
 * <p>
 * <p>
 * <p>
 * 在使用过程出现了很多注解：
 * @Module:作为实例对象的容器。
 * @Provides:标注能够提供实例化对象的方法。
 * @Component:作为桥梁，注入对象的通道。
 * @Inject：需要注入的方法
 */
public class DaggerActivity extends AppCompatActivity {
    private static final String TAG = "DaggerActivity";

    /**
     * 将 ActivityModule 中的 provideDaggerPresenter() 方法注释，在 DaggerPresenter 中添加@Inject注解，依然能够实现。
     * 逻辑如下：
     * - 先判断 ActivityModule 中是否有提供该对象实例化的方法。
     * - 如果有则返回。结束。
     * - 如果没有，则查找该类的构造方法，是否有带有@Inject的方法。如过存在，则返回。
     */
    // 标明需要注入的对象
    // 根据成员变量的类型从 Module 中查找哪个有@Provides注解的方法返回值为当前类型。
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
        if (savedInstanceState != null) {
            String message = savedInstanceState.getString("message");
            Log.e(TAG, "DaggerActivity_63-->onCreate: " + message);
        }
        inject();
        presenter.showUserName();
        LogUtil.i("client = " + (client == null ? "null" : client));
        LogUtil.i("getApi = " + (retrofit == null ? "null" : retrofit));
    }

    //第四步，注入Activity中
    private void inject() {
        // 依赖对象　Component
        AppComponent appComponent = ((MyApplication) getApplication()).getAppComponent();
        // 子类依赖对象 ，并注入
        ActivityComponent component = DaggerActivityComponent.builder()//DaggerActivityComponent 与 ActivityModule的实例绑定
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))//activityModule 该方法名和 ActivityModule 是有关的，编译时生成
                .build();
        component.inject(this);//该方法就是 AppComponent 接口的inject(),传入当前 Activity 的引用
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("message", "onSaveInstanceState");
    }

    public void showUserName(String userName) {
        LogUtil.i("userName = " + userName);
    }
}
