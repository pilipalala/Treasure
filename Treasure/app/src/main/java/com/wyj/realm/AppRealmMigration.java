package com.wyj.realm;

import com.wyj.treasure.utils.LogUtil;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * @author wangyujie
 * @date 2018/11/2.15:06
 * @describe 数据库升级迁移合并
 */
public class AppRealmMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        LogUtil.e("oldVersion:" + oldVersion + "，newVersion:" + newVersion);
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 2) {
            schema.create("RealmEntityModel")
                    .addField("id", String.class)
                    .addField("name", String.class)
                    .addField("age", int.class)
                    .addPrimaryKey("id");//这一行一定要在最后写  不然会报错
        }
        oldVersion += 1;

    }
}
