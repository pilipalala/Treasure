package com.wyj.realm;

import android.util.Log;
import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.LogUtil;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author wangyujie
 * @date 2018/11/2.19:23
 * @describe
 * https://blog.csdn.net/qq_31433525/article/details/79067266
 * https://www.jianshu.com/p/37af717761cc
 */
public class RealmActivity extends BaseActivity {
    private static final String TAG = "RealmActivity";
    private Realm mRealm;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_realm;
    }

    @Override
    protected void initData() {
        //在想要使用的时候调用，记得不用时关闭。
        mRealm = Realm.getDefaultInstance();
        Log.d(TAG, "RealmActivity_35-->initData: " + mRealm.getPath());
    }

    public void insert(View view) {
        /*事务操作*/
//        insertOne();
//        insertTwo();

        /*使用事务块*/
//        insertThree();
        insertAsync();

    }

    /**
     * 使用copyToRealmOrUpdate或copyToRealm方法插入数据
     * 当Model中存在主键的时候，推荐使用copyToRealmOrUpdate方法插入数据。
     * 如果对象存在，就更新该对象；反之，它会创建一个新的对象。
     * 若该Model没有主键，使用copyToRealm方法，否则将抛出异常。
     */
    private void insertAsync() {
        RealmEntity entity = new RealmEntity();
        entity.setAge(25);
        entity.setName("嘉靖");
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(entity);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                LogUtil.i("insertAsync onSuccess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                LogUtil.i("insertAsync onError" + error.getMessage());
            }
        });
    }

    private void insertThree() {
        RealmEntity entity = new RealmEntity();
        entity.setAge(26);
        entity.setName("欲王");
        mRealm.executeTransaction(realm -> realm.copyToRealm(entity));
    }

    /**
     * 复制一个对象到Realm数据库
     */
    private void insertTwo() {
        RealmEntity entity = new RealmEntity();
        entity.setAge(25);
        entity.setName("嘉靖");
        mRealm.beginTransaction();
        mRealm.copyToRealm(entity);
        mRealm.commitTransaction();

    }

    /**
     * 新建一个对象，并进行存储
     *
     * 在插入前，先调用beginTransaction()，完成后调用commitTransaction()即可。
     */
    private void insertOne() {
        mRealm.beginTransaction();
        RealmEntity entity = mRealm.createObject(RealmEntity.class);
        entity.setAge(24);
        entity.setName("海瑞");
        mRealm.commitTransaction();
    }

    public void delete(View view) {
//                deleteAsync();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmEntity> realmEntities = mRealm.where(RealmEntity.class).findAll();
                deleteEntity(realmEntities);
            }
        });
    }

    private void deleteAsync() {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmEntity first = realm.where(RealmEntity.class).findFirst();
                first.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                LogUtil.i("deleteAsync onSuccess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                LogUtil.i("deleteAsync onError");
            }
        });
    }

    private void deleteEntity(RealmResults<RealmEntity> realmEntities) {
        RealmEntity realmEntity = realmEntities.get(1);//删除指定位置（第1个数据）的记录
        realmEntity.deleteFromRealm();
        //删除第一个数据
        realmEntities.deleteFirstFromRealm();
        //删除最后一个数据
        realmEntities.deleteLastFromRealm();
        //删除位置为一的数据
        realmEntities.deleteFromRealm(1);
        //删除全部数据
        realmEntities.deleteAllFromRealm();
    }

    public void update(View view) {
        updateOne();

//        updateTwo();

//        updateAsync();
    }

    private void updateAsync() {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmEntity realmEntity = realm.where(RealmEntity.class).equalTo("name", "海瑞").findFirst();
                realmEntity.setName("海瑞牛逼");
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                LogUtil.i("updateAsync onSuccess");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                LogUtil.i("updateAsync onError");
            }
        });
    }

    private void updateTwo() {
        RealmResults<RealmEntity> entities = mRealm.where(RealmEntity.class).equalTo("name", "海瑞").findAll();
        mRealm.beginTransaction();
        for (RealmEntity entity : entities) {
            entity.setAge(20);
        }
        mRealm.commitTransaction();
    }

    private void updateOne() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //先查询后更改
                RealmResults<RealmEntity> result = realm.where(RealmEntity.class).equalTo("name", "海瑞").findAll();
                for (RealmEntity entity : result) {
                    entity.setAge(20);
                }
            }
        });
    }

    public void select(View view) {
        RealmResults<RealmEntity> realmEntities = mRealm.where(RealmEntity.class).findAll();
        RealmEntity realmEntity = mRealm.where(RealmEntity.class).equalTo("age", 25).findFirst();

        //对查询结果进行排序
        /**
         * 对查询结果，按age进行排序，只能对查询结果进行排序
         */
        //增序排列
//        realmEntities.sort("age");
        //降序排列
        realmEntities.sort("age", Sort.DESCENDING);
        mRealm.copyFromRealm(realmEntities);

//        selectAsync();
    }

    /**
     * 异步查询
     * 值得注意的是，这里并不会马上查到数据，是有一定延时的。
     * 也就是说，你马上使用userList的时候，里面是没有数据的。
     * 可以注册RealmChangeListener监听器，或者使用isLoaded()方法，判断是否查询完成
     */
    private void selectAsync() {
        RealmResults<RealmEntity> realmEntities = mRealm.where(RealmEntity.class).findAllAsync();
        realmEntities.isLoaded();
        realmEntities.addChangeListener(new RealmChangeListener<RealmResults<RealmEntity>>() {
            @Override
            public void onChange(RealmResults<RealmEntity> realmEntities) {
                RealmResults<RealmEntity> age = realmEntities.sort("age");
                mRealm.copyFromRealm(age);
            }
        });
    }

    //最后在销毁Activity或Fragment时，要取消掉异步任务
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
