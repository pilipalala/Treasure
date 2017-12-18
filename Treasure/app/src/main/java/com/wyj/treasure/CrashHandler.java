package com.wyj.treasure;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;

import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyujie
 * Date 2017/11/21
 * Time 23:19
 * TODO 捕获全局的异常
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private volatile static CrashHandler mInstance;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    /**
     * 存储设备信息和异常信息
     */
    private Map<String, String> mInfo = new HashMap<>();
    /**
     * 文件日期格式
     */
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    public CrashHandler() {
    }

    /**
     * 创建单例
     *
     * @return
     */
    public static CrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }


    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handlerExection(e)) {
            /*未处理  调用系统默认的处理器处理*/
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(t, e);
            }
        } else {
            /*已经人为处理*/
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 人为处理异常
     *
     * @param e
     * @return true：已处理  false：没有处理
     */
    private boolean handlerExection(Throwable e) {
        if (e == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtil.show("uncaughtException");
                Looper.loop();
            }
        }.start();
        //收集错误信息
        collectErrorInfo();
        //保存错误信息
        saveErrorInfo(e);
        return true;
    }

    /**
     * 保存错误信息
     *
     * @param e
     */
    private void saveErrorInfo(Throwable e) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mInfo.entrySet()) {
            String keyName = entry.getKey();
            String value = entry.getValue();
            sb.append(keyName + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = e.getCause();
        }
        printWriter.close();
        String result = writer.toString();


        sb.append(result);
        LogUtil.e("===" + sb + "===" + result + "===");

        long timeMillis = System.currentTimeMillis();
        String time = dateFormat.format(new Date());
        File dir = new File(mContext.getFilesDir() +
                File.separator + "crash" + File.separator);
        String fileName = dir.toString() + File.separator + "crash-" + time + "-" + timeMillis + ".log";

        /**
         * 判断有没有SD卡
         * */
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = "/sdcard/crash/";
//            File dir = new File(path);
            //先删除之前的异常信息
            if (dir.exists()) {
                //删除该目录下的所有子文件
                deleteDir(dir);
            }
            //创建新文件夹
            if (!dir.exists()) {
                dir.mkdir();
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        cacheCrashFile(fileName);
    }

    private void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                file.delete();
            }
        }
    }

    /**
     * 收集错误信息
     */
    private void collectErrorInfo() {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = TextUtils.isEmpty(pi.versionName) ? "未设置版本名称" : pi.versionName;
                String versionCode = String.valueOf(pi.versionCode);
                mInfo.put("versionName", versionName);
                mInfo.put("versionCode", versionCode);
            }
            Field[] fields = Build.class.getFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        mInfo.put(field.getName(), field.get(null).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void cacheCrashFile(String fileName) {
        SharedPreferences sp = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME", fileName).commit();

    }

    private String getCrashFile() {
        SharedPreferences sp = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        return sp.getString("CRASH_FILE_NAME", "");

    }
}
