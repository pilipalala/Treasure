package com.wyj.treasure.activity;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.reflect.SonClass;
import com.wyj.treasure.utils.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReflectActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_class)
    Button btnClass;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_reflect);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_class)
    public void onViewClicked() {
//        printFields();
        printMethods();
    }

    /**
     * 通过反射获取类的所有变量
     * <p>
     * <p>
     * 调用 getFields() 方法，输出 SonClass 类以及其所继承的父类( 包括 FatherClass 和 Object )
     * 的 public 方法。注：Object 类中没有成员变量，所以没有输出。
     * 调用 getDeclaredFields() ， 输出 SonClass 类的所有成员变量，不问访问权限。
     */
    private static void printFields() {
        //1.获取并输出类的名称
        Class mClass = SonClass.class;

        LogUtil.d("类的名称:" + mClass.getName());
        //2.1获取所有public 访问权限的变量
        //包括本类声明的和父类继承的
        Field[] fields = mClass.getFields();

        //2.2 获取所有本类声明的变量（不问访问权限）
//        Field[] fields = mClass.getDeclaredFields();

        //3. 遍历变量输出变量信息
        for (Field field :
                fields) {
            // 获取访问权限并输出
            int modifiers = field.getModifiers();

//            LogUtil.d(Modifier.toString(modifiers));

            //输出变量的类型及变量名
            LogUtil.d(field.getType().getName() + "--" + field.getName());
        }
    }

    private static void printMethods() {
        //1.获取并输出类的名称
//        SonClass sonClass = new SonClass();
//        Class mClass =  sonClass.getClass();
        Class mClass = SonClass.class;

        LogUtil.d("类的名称:" + mClass.getName());

        //2.1获取所有public 访问权限的变量
        //包括本类声明的和父类继承的
        Method[] mMethods = mClass.getMethods();

        //2.2 获取所有本类的方法（不问访问权限）不包括父类的
//        Method[] mMethods = mClass.getDeclaredMethods();


        //3.遍历所有的方法
        for (Method method :
                mMethods) {
            //获取并输出方法 的访问权限(Modifiers:修饰符)
            int modifiers = method.getModifiers();
            LogUtil.d(Modifier.toString(modifiers));

            //获取并输出方法的返回值类型
            Class returenType = method.getReturnType();
            LogUtil.e(returenType.getName() + "-----" + method.getName());
            //获取并输出方法的所有参数
        }


    }
}
