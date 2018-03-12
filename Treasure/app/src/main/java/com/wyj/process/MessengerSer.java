package com.wyj.process;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.wyj.treasure.utils.LogUtil;

public class MessengerSer extends Service {
    static final int MSG_SAY_HELLO = 1;
    MHandler mHandler = new MHandler();
    Messenger mMessenger = new Messenger(mHandler);


    public MessengerSer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    public class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_SAY_HELLO:
                    LogUtil.v(msg.getData().getString("client"));
                    Messenger client = msg.replyTo;
                    Message serviceMessage = Message.obtain(null, ProcessActivity.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("service","this is service");
                    serviceMessage.setData(bundle);
                    try {
                        client.send(serviceMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }


}
