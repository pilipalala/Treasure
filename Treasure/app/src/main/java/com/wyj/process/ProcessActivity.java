package com.wyj.process;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.wyj.treasure.IMyAidlInterface;
import com.wyj.treasure.R;
import com.wyj.treasure.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProcessActivity extends AppCompatActivity {
    private static final String TAG = "ProcessActivity";
    @BindView(R.id.bt_start)
    Button btStart;
    private BinderSer.MyBinder binder;
    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        ButterKnife.bind(this);


        startMyService();


        initData();

//        initBinder();
//        initIBinder();
        initMessenger();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);

    }

    private void initMessenger() {
        ServiceConnection messengerConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                messenger = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, MessengerSer.class);
        bindService(intent, messengerConn, Service.BIND_AUTO_CREATE);

    }

    private void initIBinder() {
        ServiceConnection binderConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IBinderSer.LocalBinder binder = (IBinderSer.LocalBinder) service;
                IBinderSer iBinderSer = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, IBinderSer.class);
        bindService(intent, binderConn, Service.BIND_AUTO_CREATE);
    }

    private void initBinder() {

        ServiceConnection binderConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (BinderSer.MyBinder) service;
                Log.i(TAG, "Service的Count值为" + binder.getCount());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, BinderSer.class);
        bindService(intent, binderConn, Service.BIND_AUTO_CREATE);

    }

    private void startMyService() {
        Intent intent = new Intent();
        intent.setClass(this, ProcessService.class);
        Bundle bundle = new Bundle();
        User user = new User("name", 12, true);
        bundle.putSerializable("data", user);
        bundle.putString("name", "wang");
        bundle.putInt("int", 21);
        intent.putExtras(bundle);
        startService(intent);
    }

    private void initData() {
        //创建ServiceConnection的匿名类
        ServiceConnection serviceConnection = new ServiceConnection() {
            //重写onServiceConnected()方法和onServiceDisconnected()方法
            //在Activity与Service建立关联和解除关联的时候调用
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtil.i(TAG + "已经连接上了");
                //使用AIDLService1.Stub.asInterface()方法获取服务器端返回的IBinder对象
                //将IBinder对象传换成了mAIDL_Service接口对象
                IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                try {
                    //通过该对象调用在MyAIDLService.aidl文件中定义的接口方法,从而实现跨进程通信
                    String haha = iMyAidlInterface.getInfor("hello,我是activity---getInfor");
                    String bookName = iMyAidlInterface.getName('c');
                    Book book = new Book("name", 21, true);
                    String bookInfo = iMyAidlInterface.getBook(book);
                    LogUtil.i(TAG + "接受到Service发过来的字符串:" + haha + "  " + bookName + "  " + bookInfo);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.i(TAG + "断开了连接");
            }
        };

//        Intent intent = new Intent(this, ProcessService.class);


        //通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定
        //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
        Intent intent = new Intent("com.wyj.treasure.IMyAidlInterface");

        //Android5.0后无法只通过隐式Intent绑定远程Service
        //需要通过setPackage()方法指定包名
        intent.setPackage("com.wyj.treasure");

        //绑定服务,传入intent和ServiceConnection对象
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);


    }

    @OnClick(R.id.bt_start)
    public void onViewClicked() {
        // 实例化一个Message对象
        Message msg = Message.obtain(null, MessengerSer.MSG_SAY_HELLO, 0, 0);
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
