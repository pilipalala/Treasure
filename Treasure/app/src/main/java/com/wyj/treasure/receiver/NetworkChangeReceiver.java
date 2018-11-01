package com.wyj.treasure.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.wyj.treasure.Constants;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.ToastUtil;

/**
 * 不要在onReceive()方法中添加过多的逻辑或者进行任何好事操作，因为在 广播接收器中是不允许开启线程的，
 * 当onReceive()方法运行了较长时间而没有结束时吗，程序就会报错。
 * 因此广播接收器更多的时扮演一种打开程序其他组件的角色，比如创建一条状态栏通知，或启动一个服务等
 * <p>
 * <p>
 * 定义广播接收器只需要继承BroadcastReceiver 并重写 onReceive 方法即可
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        mContext = context;
        Log.e("NetworkChangeReceiver", "NetworkChangeReceiver_25-->onReceive: " + action);
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            netWorkChange(context);
        }  else if (Constants.ACTION_NETWORK_CHANGE_OFF.equals(action)) {
            ToastUtil.show("网络不可用");
        } else if (Constants.ACTION_NETWORK_CHANGE_ON.equals(action)) {
            ToastUtil.show("网络可用");
        }
        /*onReceive 函数不能做耗时的事情，参考值：10s以内*/
        checkNetWork();

    }

    private void checkNetWork() {
        if (CommonUtils.isWiFiEnable(mContext)) {
//            ToastUtil.show("你是wifi连接");
        } else if (CommonUtils.isMobileEnable(mContext)) {
//            ToastUtil.show("您是数据网络连接");
        }
    }

    private void netWorkChange(Context context) {
        /**
         * ConnectivityManager 系统服务管理网络连接
         * 调用他的getActiveNetworkInfo()方法可以得到NetworkInfo的实例
         * 接着调用NetworkInfo的isAvailable()方法，就乐意判断出当前是否有网络了
         * 需要ACCESS_NETWORK_STATE权限
         * */
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            ToastUtil.show("网络可用");
        } else {
            ToastUtil.show("网络不可用");
        }
    }
}
