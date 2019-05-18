package com.wyj.treasure.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangyujie
 * @date 2019/3/21.11:46
 * @describe 添加描述
 */
public class FileUtils {

    private static File file;

    public static void createFile() {
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/scaleData/";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        String fileName = "TestFile" + time + ".txt";
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    public static void writeTxtToFile(String content) {
        String strContent = content + "\r\n";
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }
}
