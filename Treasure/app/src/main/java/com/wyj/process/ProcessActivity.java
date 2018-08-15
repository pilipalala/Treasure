package com.wyj.process;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProcessActivity extends BaseActivity {
    public static final int MSG_FROM_SERVICE = 0x1000;
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 0x1001;
    private static final String TAG = "ProcessActivity";
    @BindView(R.id.bt_start)
    Button btStart;
    private BinderSer.MyBinder binder;
    private Messenger serviceMessenger;
    private MessengerHandler handler;
    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void IOnNewBookArrived(Book book) throws RemoteException {
            handler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }

    };
    private ServiceConnection aidlConnection;
    private IBookManager mRemoteBookManager;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            LogUtil.d("binder died. tname:" + Thread.currentThread().getName());
            if (mRemoteBookManager == null) {
                return;
            }
            mRemoteBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mRemoteBookManager = null;
            // TODO:这里重新绑定远程Service
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int initView() {
        return R.layout.activity_process;
    }

    @Override
    protected void initData() {
        handler = new MessengerHandler();
//        initBundle();
//        initMessenger();
        initAIDL();
//        initBinder();
//        initIBinder();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);

    }

    private void initMessenger() {
        Messenger clientMessenger = new Messenger(handler);
        ServiceConnection messengerConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //2 绑定成功后 用服务端返回的 IBinder 对象创建一个 Messenger
                serviceMessenger = new Messenger(service);

                // 实例化一个Message对象
                Message msg = Message.obtain(null, MessengerSer.MSG_SAY_HELLO, 0, 0);
                Bundle bundle = new Bundle();
                bundle.putString("client", "this is client!");
                msg.setData(bundle);
                msg.replyTo = clientMessenger;
                try {
                    //通过 Messenger 就可以向服务端发送消息了
                    serviceMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        //1 绑定服务端的service
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

    private void initBundle() {
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

    private void initAIDL() {
        //创建ServiceConnection的匿名类
        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用
        //使用AIDLService1.Stub.asInterface()方法获取服务器端返回的IBinder对象
        //将IBinder对象传换成了mAIDL_Service接口对象
        //通过该对象调用在MyAIDLService.aidl文件中定义的接口方法,从而实现跨进程通信
        aidlConnection = new ServiceConnection() {
            //重写onServiceConnected()方法和onServiceDisconnected()方法
            //在Activity与Service建立关联和解除关联的时候调用
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //使用AIDLService1.Stub.asInterface()方法获取服务器端返回的IBinder对象
                //将IBinder对象传换成了mAIDL_Service接口对象
                IBookManager iBookManager = IBookManager.Stub.asInterface(service);
                mRemoteBookManager = iBookManager;
                try {
                    mRemoteBookManager.asBinder().linkToDeath(mDeathRecipient, 0);
                    //通过该对象调用在MyAIDLService.aidl文件中定义的接口方法,从而实现跨进程通信
                    String haha = iBookManager.getInfor("hello,我是activity---getInfor");
                    String bookName = iBookManager.getName('c');
                    List<Book> bookList = iBookManager.getBookList();
                    LogUtil.i("bookList---:" + bookList.toString());
                    Book book = new Book("Book3", 3, true);
                    String bookInfo = iBookManager.getBook(book);
                    LogUtil.i(TAG + "接受到Service发过来的字符串:" + haha + "  " + bookName + "  " + bookInfo);
                    iBookManager.addBook(book);
                    List<Book> bookNewList = iBookManager.getBookList();
                    LogUtil.i(TAG + "bookNewList---:" + bookNewList.toString());
                    iBookManager.registerListener(mOnNewBookArrivedListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.i(TAG + "断开了连接");
            }
        };


        //通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定
        //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
        Intent intent = new Intent("com.wyj.treasure.IMyAidlInterface");

        //Android5.0后无法只通过隐式Intent绑定远程Service
        //需要通过setPackage()方法指定包名
        intent.setPackage("com.wyj.treasure");

        //绑定服务,传入intent和ServiceConnection对象
        bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE);


    }

    @OnClick(R.id.bt_start)
    public void onViewClicked() {
        // 实例化一个Message对象
        Message msg = Message.obtain(null, MessengerSer.MSG_SAY_HELLO, 0, 0);
        Bundle bundle = new Bundle();
        bundle.putString("client", "this is client!");
        msg.setData(bundle);

        try {
            //通过 Messenger 就可以向服务端发送消息了
            serviceMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        unbindService(aidlConnection);
        super.onDestroy();
    }

    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_SERVICE:
                    LogUtil.v(msg.getData().getString("service"));
                    break;
                case MESSAGE_NEW_BOOK_ARRIVED:
                    LogUtil.v("receive new book" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }
}
