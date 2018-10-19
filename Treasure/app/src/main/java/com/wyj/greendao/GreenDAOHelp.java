package com.wyj.greendao;

import android.content.Context;

/**
 * @author wangyujie
 * @date 2018/9/25.15:18
 * @describe
 */
public class GreenDAOHelp {

    private static DaoSession daoSession;

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static void create(Context context,String name) {
        //通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, name);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

}
