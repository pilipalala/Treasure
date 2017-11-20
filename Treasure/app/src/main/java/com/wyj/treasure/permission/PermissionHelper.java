package com.wyj.treasure.permission;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * @author wangyujie
 *         on 2017/11/14.13:45
 *         TODO 反射加注解方式
 */

public class PermissionHelper {

    /**
     * 1.Fragment or Activity
     * 2.int请求码
     * 3.需要请求的权限
     */

    private Object mObject;
    private int mRequestCode;
    private String[] mRequestPermission;

    public PermissionHelper(Object object) {
        this.mObject = object;
    }

    public static PermissionHelper with(Activity activity) {
        return new PermissionHelper(activity);
    }

    public static PermissionHelper with(Fragment fragment) {

        return new PermissionHelper(fragment);
    }

    /**
     * 处理申请权限的回调
     *
     * @param object
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissionsResult(Object object, int requestCode, String[] permissions) {
        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(object, permissions);
        if (deniedPermissions.size() == 0) {
            /*权限被同意授权 直接执行方法 反射获取执行方法*/
            PermissionUtils.executeSuccessMethod(object, requestCode);
        } else {
            /*申请的权限用户有不同意的*/
            PermissionUtils.executeFailMethod(object, requestCode);
        }
    }

    public void requestPermission(Activity activity, int requestCode, String... requestPermission) {
        PermissionHelper.with(activity).requestCode(requestCode).requestPermission(requestPermission).request();
    }

    public PermissionHelper requestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public PermissionHelper requestPermission(String... requestPermission) {
        this.mRequestPermission = requestPermission;
        return this;
    }

    /**
     * 发起请求
     */
    public void request() {

        /*1.首先判断当前版本是不是6.0以上版本*/
        if (!PermissionUtils.isOverMarshmallow()) {
        /*2.如果不是6.0以上那么直接执行方法 反射获取执行方法*/
            /**
             * 执行什么方法不确定  只能采用注解的方式给方法打一个标记
             *
             * 然后通过反射去执行
             *
             * */
            PermissionUtils.executeSuccessMethod(mObject, mRequestCode);

            return;

        }
        /*3.如果是6.0以上 首先判断 权限是否授权*/


        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(mObject, mRequestPermission);
        if (deniedPermissions.size() == 0) {
            /*4.如果授权 直接执行方法 反射获取执行方法*/
            PermissionUtils.executeSuccessMethod(mObject, mRequestCode);
        } else {
            /*5.如果没有授权 则去请求授权*/
            ActivityCompat.requestPermissions(PermissionUtils.getActivity(mObject), deniedPermissions.toArray(new String[deniedPermissions.size()]), mRequestCode);

        }

    }
}
