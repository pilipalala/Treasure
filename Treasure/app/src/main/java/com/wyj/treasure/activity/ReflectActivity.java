package com.wyj.treasure.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
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
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_sub_title)
    LinearLayout llSubTitle;

    private void getClassObj() {

        //获取类类型的方法
        //方法一：
        Class<?> cls1 = SonClass.class;
        //方法二：
        SonClass sonClass = new SonClass();
        Class<?> cls2 = sonClass.getClass();

        //方法三：
        Class<?> cls3 = null;
        try {
            cls3 = Class.forName("com.wyj.treasure.reflect.SonClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //方法四：（不建议使用）
        Class cls4 = null;
        try {
            cls4 = getClassLoader().loadClass("com.wyj.treasure.reflect.SonClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("cls1: ").append(cls1).append("\n\n");
        sb.append("cls2: ").append(cls2).append("\n\n");
        sb.append("cls3: ").append(cls3).append("\n\n");
        sb.append("cls4: ").append(cls4);
        tvClass.setText(sb.toString());
        toolbar.setSubtitle("三种方式获得类对象");
    }

    /**
     * 通过反射获取类的所有方法
     * <p>
     * getConstructors()：获得类的public类型的构造方法。
     * getConstructor(Class[] parameterTypes)：获得类的特定构造方法。
     * <p>
     * getSuperclass()：获取某类的父类
     * getInterfaces()：获取某类实现的接口
     * <p>
     */
    private void printMethods() {
        //1.获取并输出类的名称
        Class mClass = SonClass.class;

        //2.1获取所有public 访问权限的变量
        //包括本类声明的和父类继承的
//        Method[] mMethods = mClass.getMethods();

        //2.2 获取所有本类的方法（不问访问权限）不包括父类的
        Method[] mMethods = mClass.getDeclaredMethods();

        if (mMethods == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        //3.遍历所有的方法
        for (Method method : mMethods) {
            //获取并输出方法 的访问权限(Modifiers:修饰符)
            sb.append(Modifier.toString(method.getModifiers())).append("\t");
            //获取方法的返回类型/
            sb.append(method.getReturnType()).append("\t");
            //获取方法名/
            sb.append(method.getName()).append("(");
            //获取方法所有参数类型
            Class[] parameters = method.getParameterTypes();
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    Class parameter = parameters[i];
                    sb.append(parameter.getSimpleName());
                    if (i < parameters.length - 1) {
                        sb.append(",");
                    }
                }
            }
            sb.append(")\n\n");
            tvClass.setText(sb.toString());
            toolbar.setSubtitle("获得类的所有方法信息");
        }


        try {
            Method printSonMsg = mClass.getDeclaredMethod("printSonMsg");
            //执行printSonMsg
            printSonMsg.invoke(mClass.newInstance(), new Object[0]);
            // 获取setmSonAge方法
            Method method = mClass.getDeclaredMethod("setmSonAge", int.class);
            // 如果是 private 或者 package 权限的，一定要赋予其访问权限
            method.setAccessible(true);
            // 调用setmSonAge的对象和传入setmSonAge的值
            method.invoke(mClass.newInstance(), 23);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取类的所有变量
     * <p>
     * <p>
     * 调用 getFields() 方法，输出 SonClass 类以及其所继承的父类( 包括 FatherClass 和 Object )的 public 方法。
     * 注：Object 类中没有成员变量，所以没有输出。
     * <p>
     * 调用 getDeclaredFields() ， 输出 SonClass 类的所有成员变量，不问访问权限。
     * <p>
     * getDeclaredField是可以获取一个类的所有字段.
     * getField只能获取类的public 字段.
     */
    private void printFields() {
        try {
            //1.获取并输出类的名称
            Class mClass = SonClass.class;
            //创建对象
            Object obj = mClass.newInstance();

            //2.1获取所有public 访问权限的变量
            //包括本类声明的和父类继承的
//            Field[] fields = mClass.getFields();

            //2.2 获取所有本类声明的变量（不问访问权限）
            Field[] fields = mClass.getDeclaredFields();

            if (fields == null) {
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("获得类的所有属性信息：" + fields.length + "个" + "\n\n");
            sb.append(Modifier.toString(mClass.getModifiers()) + " class " + mClass.getSimpleName() + "{\n");
            //3. 遍历变量输出变量信息
            for (Field field : fields) {
                //获取访问权限并输出
                sb.append(Modifier.toString(field.getModifiers())).append("\t");
                //输出变量的类型
                sb.append(field.getType().getSimpleName()).append("\t");
                //输出变量名
                sb.append(field.getName()).append(";");
                sb.append("\n\n");
            }
            sb.append("}\n**************修改属性值****************\n\n");


            sb.append("属性mSonAge的默认值：mSonAge = ");
            //拿到mSonAge的属性
            Field mSonAgeField = mClass.getDeclaredField("mSonAge");
            //获得属性值
            sb.append(mSonAgeField.getInt(obj)).append("\n\n");
            //修改属性值
            mSonAgeField.setInt(obj, 100000);
            sb.append("属性mSonAge修改后的值：mSonAge = ");
            sb.append(mSonAgeField.getInt(obj)).append("\n\n");
            tvClass.setText(sb.toString());
            toolbar.setSubtitle("获得所有属性信息&修改属性值");
        } catch (Exception e) {
            LogUtil.e(e.fillInStackTrace() + "-----" + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_reflect);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        llSubTitle.setVisibility(View.GONE);
        toolbar.setTitle("反射");
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @OnClick({R.id.btn_getFields, R.id.btn_getMethods, R.id.btn_getClass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_getFields:
                printFields();
                break;
            case R.id.btn_getMethods:
                printMethods();
                break;
            case R.id.btn_getClass:
                getClassObj();
                break;
        }
    }
}
