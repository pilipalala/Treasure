package com.wyj.treasure.activity;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExternalStorageActivity extends BaseActivity {

    @Override
    protected int getContentViewID() {
        return R.layout.activity_external_storage;
    }

    @Override
    protected void initData() {

        /**
         * 为了大家方便记忆，我们可以这么记，
         * 就是我们存相关数据在相应的自己的app包名下的地方，
         * 不管是内部存储还是外部存储，
         * 都是调用context.getXXXX()方法,
         * 毕竟是跟我们的APP相关的，
         * 代码里面直接用context来获取。
         * 如果是一些共用的目录，说明本身就是存在的，
         * 直接用Environment.getXXXX()来获取即可。
         *
         * */
        initInternalStorage();
        initExternalStorage();
//        initByteStreamNoBuffer();
//        initByteStreamBuffer();
//        initCharStreamNoBuffer();
//        initCharStreamBuffer();


    }

    /**
     * 字符流：
     * 不使用缓冲：
     */
    private void initCharStreamNoBuffer() {
        String source = Environment.getExternalStorageDirectory() + File.separator + "com.jhcms.waimaibiz3.7.20180302.apk";
        String target = getExternalCacheDir() + File.separator + "com.jhcms.waimaibiz3.7.20180302.apk";

        FileReader fileReader = null;
        FileWriter fileWriter = null;

        try {
            fileReader = new FileReader(source);
            fileWriter = new FileWriter(target);

            //单字符写入
//            int temp = 0;
//            if ((temp = fileReader.read()) == -1) {
//                fileWriter.write(temp);
//            }
            //字符数组方式写入
            int temp1 = 0;
            char[] buff = new char[1024];
            while  ((temp1 = fileReader.read(buff)) != -1) {
                fileWriter.write(buff,0,temp1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符流（实现文件的复制）
     * 使用缓冲：
     */
    private void initCharStreamBuffer() {
        String source = Environment.getExternalStorageDirectory() + File.separator + "com.jhcms.waimaibiz3.7.20180302.apk";
        String target = getExternalCacheDir() + File.separator + "com.jhcms.waimaibiz3.7.20180302.apk";
        FileReader reader = null;
        FileWriter writer = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            reader = new FileReader(source);
            bufferedReader = new BufferedReader(reader);
            writer = new FileWriter(target);
            bufferedWriter = new BufferedWriter(writer);
            String content = null;
            //readLine()方法只返回换行符之前的数据
            while ((content = bufferedReader.readLine()) != null) {
                bufferedWriter.write(content);
                //写完文件内容之后换行,windows下的换行是\r\n,Linux下则是\n
                bufferedWriter.newLine();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 字节流（实现文件的复制）
     * 使用缓冲：
     */
    private void initByteStreamBuffer() {
        String source = Environment.getExternalStorageDirectory() + File.separator + "com.jhcms.waimaibiz3.7.20180302.apk";
        String target = getExternalCacheDir() + File.separator + "com.jhcms.waimaibiz3.7.20180302.apk";
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            inputStream = new FileInputStream(source);
            bufferedInputStream = new BufferedInputStream(inputStream);
            outputStream = new FileOutputStream(target);
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            int temp = 0;
            byte[] buff = new byte[1024];
            while  ((temp = bufferedInputStream.read(buff)) != -1) {
                bufferedOutputStream.write(buff, 0, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 字节流（实现文件的复制）
     * 不使用缓冲：
     */
    private void initByteStreamNoBuffer() {
        String source = Environment.getExternalStorageDirectory() + File.separator + "com.jhcms.waimaibiz3.7.20180302.apk";
        String target = getExternalCacheDir() + File.separator + "com.jhcms.waimaibiz3.7.20180302.apk";
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target);
            int temp = 0;
            byte[] buf = new byte[1024];
            while ((temp = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * 外部存储
     */
    private void initExternalStorage() {
        //getXXXDirs是同时获取内部存储卡和外部存储卡(SD卡)
        LogUtil.v("---------------外部存储--------------------");
        //存储状态
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LogUtil.v("Environment.getExternalStorageState():正常挂载");
        }

        //外部存储-->/storage/emulated/0
        File directory = Environment.getExternalStorageDirectory();
        LogUtil.v("Environment.getExternalStorageDirectory():" + directory.toString());

        //私有目录  /data/包名--->/storage/emulated/0/Android/data/com.wyj.treasure/cache
        File cacheDir = getExternalCacheDir();
        LogUtil.v("Context.getExternalCacheDir():" + cacheDir.toString());

        File[] cacheDirs = getExternalCacheDirs();
        for (File dir : cacheDirs) {
            LogUtil.v("Context.getExternalCacheDirs():" + dir.toString());
        }


        File filesDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        //私有目录  /data/包名--->/storage/emulated/0/Android/data/com.wyj.treasure/files/Download
        LogUtil.v("Context.getExternalFilesDir():" + filesDir.toString());

        File[] filesDirs = getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS);
        for (File dir : filesDirs) {
            LogUtil.v("Context.getExternalFilesDirs():" + dir.toString());
        }
        // /storage/emulated/0/DCIM
        File publicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        LogUtil.v("Environment.getExternalStoragePublicDirectory():" + publicDirectory.toString());


        LogUtil.v("外置SD卡路径 = " + getExtendedMemoryPath(this));

    }

    /**
     * 内部存储
     */
    private void initInternalStorage() {
        LogUtil.v("---------------内部存储--------------------");
        //内部存储--> /data目录
        File dataDirectory = Environment.getDataDirectory();
        LogUtil.v("Environment.getDataDirectory():" + dataDirectory.toString());

        //app的文件信息--->/data/data/com.wyj.treasure/cache
        //        6.0以上 /data/user/0/com.wyj.treasure/cache
        File filesDir = getFilesDir();
        LogUtil.v("Context.getFilesDir():" + filesDir.toString());

        //app的缓存信息-->/data/data/com.wyj.treasure/files
        //       6.0以上 /data/user/0/com.wyj.treasure/files

        File cacheDir = getCacheDir();
        LogUtil.v("Context.getCacheDir():" + cacheDir.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File dataDir = getDataDir();
            LogUtil.v("Context.getDataDir():" + dataDir.toString());
        }
        //创建目录 经测试该方法会在data/data包名/目录下生成一个以app_开头的目录
        File ceshi = getDir("ceshi", Context.MODE_PRIVATE);
        //   /data/user/0/com.wyj.treasure/app_ceshi
        LogUtil.v("Context.getDir():" + ceshi.toString());
        File cacheDirectory = Environment.getDownloadCacheDirectory();
        //  /data/cache
        LogUtil.v("Environment.getDownloadCacheDirectory():" + cacheDirectory.toString());
    }

    /**
     * @param mContext
     * @return
     * 扩展内存就是我们插入的外置SD卡
     */
    private static String getExtendedMemoryPath(Context mContext) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
