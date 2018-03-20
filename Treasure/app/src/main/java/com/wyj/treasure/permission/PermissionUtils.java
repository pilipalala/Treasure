package com.wyj.treasure.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.wyj.treasure.utils.LogUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyujie
 *         on 2017/11/14.14:17
 *         TODO
 */

public class PermissionUtils {

    /*这个类里面所有的都是静态方法*/

    private PermissionUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * @return 是否是6.0版本
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.M;
    }

    /**
     * @param reflectObject
     * @param requestCode
     */
    public static void executeSuccessMethod(Object reflectObject, int requestCode) {
        /**
         * 获取class中所有方法  ---  遍历 --- 找到我们打了标记的方法 --- 并且请求码一致
         * */
        Method[] methods = reflectObject.getClass().getDeclaredMethods();
        //遍历
        for (Method method : methods) {
            LogUtil.e(method.getName());
            //获取该方法上面有没有打这个成功的标记
            PermissionSuccess successMethed = method.getAnnotation(PermissionSuccess.class);
            if (successMethed != null) {
                //代表该方法打了标记
                //并且我们的请求码必须和requestCode一样
                int methedCode = successMethed.requestCode();
                if (methedCode == requestCode) {
                    LogUtil.e("找到了方法:" + method);
                    executeMethod(reflectObject, method);
                }
            }
        }

    }

    /**
     * @param reflectObject
     * @param method
     */
    private static void executeMethod(Object reflectObject, Method method) {
        /**
         * 反射执行方法
         * 参数一 属于哪个类
         * 参数二 传参数
         * */
        try {
            method.setAccessible(true);//允许执行私有方法
            method.invoke(reflectObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取没有授权的权限
     *
     * @param mObject
     * @param requestPermissions
     * @return 没有授权的权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static List<String> getDeniedPermissions(Object mObject, String[] requestPermissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String requestPermission : requestPermissions) {
            /*把没有授予过的权限加入集合*/
            if (ContextCompat.checkSelfPermission(getActivity(mObject), requestPermission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(requestPermission);
                if (!getActivity(mObject).shouldShowRequestPermissionRationale(requestPermission)) {
                    // TODO 待解决
                }
            }
        }
        return deniedPermissions;

    }

    /**
     * 获取上下文
     *
     * @param object
     * @return
     */
    public static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        }
        return null;
    }


    /**
     * 执行失败的
     *
     * @param reflectObject
     * @param requestCode
     */
    public static void executeFailMethod(Object reflectObject, int requestCode) {

/**
 * 获取class中所有方法  ---  遍历 --- 找到我们打了标记的方法 --- 并且请求码一致
 * */
        Method[] methods = reflectObject.getClass().getDeclaredMethods();
        //遍历
        for (Method method : methods) {
            LogUtil.e(method.getName());
            //获取该方法上面有没有打这个失败的标记
            PermissionFail failMethed = method.getAnnotation(PermissionFail.class);
            if (failMethed != null) {
                //代表该方法打了标记
                //并且我们的请求码必须和requestCode一样
                int methedCode = failMethed.requestCode();
                if (methedCode == requestCode) {
                    LogUtil.e("找到了失败的方法:" + method);
                    executeMethod(reflectObject, method);
                }
            }
        }
    }
}
