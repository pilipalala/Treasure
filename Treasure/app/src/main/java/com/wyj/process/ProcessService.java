package com.wyj.process;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.wyj.treasure.utils.LogUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProcessService extends Service {
    private static final String TAG = "ProcessService";

    private AtomicBoolean mIsSercviceDestoryed = new AtomicBoolean(false);
    //CopyOnWriteArrayList 支持并发读写
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<IOnNewBookArrivedListener>();
    // 实例化AIDL的Stub类(Binder的子类)
    Binder binder = new IBookManager.Stub() {


        //重写接口里定义的方法
        @Override
        public String getInfor(String infor) throws RemoteException {
            LogUtil.i(TAG + "接收到Activity的字符串infor: " + infor);
            return "service传过去的字符串infor";
        }

        @Override
        public String getName(char name) throws RemoteException {
            LogUtil.i(TAG + "接收到Activity的字符串name: " + name);
            return "service传过去的字符串name";
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public String getBook(Book book) throws RemoteException {
            LogUtil.i(TAG + "接收到Activity的字符串book.getBookName: " + book.getBookName());
            return "service传过去的字符串book";
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
            LogUtil.v("registerListener:" + mListenerList.beginBroadcast());
            mListenerList.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
            LogUtil.v("unregisterListener:" + mListenerList.beginBroadcast());
            mListenerList.finishBroadcast();
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            int check = checkCallingOrSelfPermission("com.wyj.process.permission.ACCESS_BOOK_SERVICE");
            LogUtil.d("check=" + check);
            if (check == PackageManager.PERMISSION_DENIED) {
                return false;
            }
            String[] packagesForUid = getPackageManager().getPackagesForUid(getCallingUid());
            if (packagesForUid != null && packagesForUid.length > 0) {
                packageName = packagesForUid[0];
            }
            LogUtil.d("onTransact: " + packageName);
            if (!packageName.startsWith("com.wyj")) {
                return false;
            }


            return super.onTransact(code, data, reply, flags);

        }
    };


    public ProcessService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.wyj.process.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }

        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG + "执行了onCreat()");
        mBookList.add(new Book("book1", 1, true));
        mBookList.add(new Book("book2", 2, true));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsSercviceDestoryed.get()) {
                    try {
                        Thread.sleep(2000);
                        int size = mBookList.size() + 1;
                        Book book = new Book("new book # " + size, size, true);
                        onNewBookArrived(book);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    /**
     * beginBroadcast 和 finishBroadcast 必须要配对使用
     */
    private void onNewBookArrived(Book book) {
        mBookList.add(book);
        int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                try {
                    listener.IOnNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        User user = (User) intent.getSerializableExtra("data");
        LogUtil.i(TAG + "user" + user.toString());
        LogUtil.i(TAG + "执行了onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG + "执行了onDestory()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.i(TAG + "执行了onUnbind()");
        return super.onUnbind(intent);
    }
}
