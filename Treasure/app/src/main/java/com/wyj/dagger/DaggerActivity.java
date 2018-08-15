package com.wyj.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
 * 第一步 编写Module(ApiModule、ActivityModule)
 * 类需要用@Module来标明注解
 * 这里有一点规则，用@Provides注解的函数需要以provide开头，然后后面接什么内容都可以，看自己喜欢，
 * 事实上，经过我的测试，我把provideActivity()改成provideA()同样是可以注入成功的，所以大家可以知道，
 * 这里是根据返回值类型来标识的，方法名并不重要，只需要保证以provide开头即可。
 * 第二步 编写ActivityComponent(AppComponent、ActivityComponent)
 *  编写的这个Component需要用@Component注解来标识，同时声明了modules为上面编写的 (ApiModule、ActivityModule),
 *  然后提供了一个方法，叫做inject，用来在Activity中注入。(这里为什么要写一个方法叫做inject我暂时还没弄清楚，
 *  改名字是可以的，但是参数类型不能改，并且一定要指定module=ActivityModule才能注入)，
 *  这里我们暂且理解为提供一个方法来注入对象吧。
 *
 */
public class DaggerActivity extends AppCompatActivity {
    @Inject
    DaggerPresenter presenter;
    @Inject
    OkHttpClient client;
    @Inject
    Retrofit retrofit;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        inject();
        presenter.showUserName();

        LogUtil.i("client = " + (client == null ? "null" : client));
        LogUtil.i("retrofit = " + (retrofit == null ? "null" : retrofit));
    }

    private void inject() {
        AppComponent appComponent = ((MyApplication) getApplication()).getAppComponent();
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    public void showUserName(String userName) {
        text.setText(userName);
    }
}
