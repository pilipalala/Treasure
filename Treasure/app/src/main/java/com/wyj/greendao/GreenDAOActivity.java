package com.wyj.greendao;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.activity.MainActivity;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;


import org.greenrobot.greendao.query.QueryBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class GreenDAOActivity extends BaseActivity {

    private static final String TAG = "GreenDAOActivity";
    private StudentDao studentDao;
    private StringBuilder builder;
    private SQLiteDatabase db;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_green_dao;
    }

    @Override
    protected void initData() {
        setTitle("GreenDAO的基本使用");
        create();

//        copyRawDB();

    }

    /**
     * 读取 assets 资源文件夹里面的.db文件
     */
    private void copyRawDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 初始化，只需要调用一次
                AssetsDatabaseManager.initManager(GreenDAOActivity.this);
                // 获取管理对象，因为数据库需要通过管理对象才能够获取
                AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
                // 通过管理对象获取数据库
                db = mg.getDatabase("data_s.db");
                // 对数据库进行操作
                String sql = "SELECT * FROM Demo " + " WHERE height > 50 and height < 210 and age >10 and age < 99 and weight >40 and weight < 120";
                Cursor cursor = db.rawQuery("select * from Demo", null);
                int count = 0;
                long start = System.currentTimeMillis();
                long end = 0;
                while (cursor.moveToNext()) {
                    count++;
                    if (count <= 1000) {
                        Log.e(TAG, "GreenDAOActivity_58-->count: " + count);
                    }
                    if (count == 1000) {
                        end = System.currentTimeMillis();
                        Log.e(TAG, "GreenDAOActivity_58-->time: " + (end - start));
                    }
                }
                cursor.close();
                db.close();
            }
        }).start();

    }

    private void create() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "treasure.db");
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        studentDao = daoSession.getStudentDao();
    }

    @OnClick({R.id.bt_gd_add, R.id.btn_add_lsit, R.id.btn_inquiry_all, R.id.btn_inquiry_only,
            R.id.btn_inquiry_sort, R.id.btn_combined_query, R.id.btn_inquiry_one, R.id.btn_inquiry_two,
            R.id.btn_delete_designation, R.id.btn_update_designation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_gd_add:
                insert();
                break;
            case R.id.btn_add_lsit:
                insertist();
                break;
            case R.id.btn_inquiry_all:
                searchAll();
                break;
            case R.id.btn_inquiry_only:
                searchAssign();
                break;
            case R.id.btn_inquiry_sort://升序
                searchAssignOrderAsc();
                break;
            case R.id.btn_combined_query:
                searchCombined();
                break;
            case R.id.btn_inquiry_one:
                searchLimit();
                break;
            case R.id.btn_inquiry_two:
                searchLimitOffset();
                break;
            case R.id.btn_delete_designation:
                delete();
                break;
            case R.id.btn_update_designation:
                update();
                break;
        }
    }

    /**
     * 更新指定信息
     */
    private void update() {
        Student student = studentDao.queryBuilder().where(StudentDao.Properties.StuName.eq("哩哩哩哩")).build().unique();
        if (student != null) {
            student.setStuScore("100");
            studentDao.update(student);
        }
    }

    /**
     * 删除指定信息
     */
    private void delete() {
        studentDao.queryBuilder().where(StudentDao.Properties.StuName.eq("啦啦啦666"))
                .buildDelete().executeDeleteWithoutDetachingEntities();
        ToastUtil.show("删除成功~");
//        studentDao.delete(new Student());// 删除指定对象
//        studentDao.deleteAll();// 删除所有

    }

    /**
     * 查询所有返回数据 但只返回前三条数据 并且跳过第一条数据
     */
    private void searchLimitOffset() {
        List<Student> studentList = studentDao.queryBuilder().limit(3).offset(1).list();
        if (studentList.size() > 0) {
            String searchAllInfo = "";
            for (Student stu : studentList) {
                searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
            }
            LogUtil.d(searchAllInfo);
        }
    }

    /**
     * 查询所有返回数据 但只返回前三条数据
     */
    private void searchLimit() {
        List<Student> studentList = studentDao.queryBuilder().limit(3).list();
        if (studentList.size() > 0) {
            String searchAllInfo = "";
            for (Student stu : studentList) {
                searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
            }
            LogUtil.d(searchAllInfo);
        }
    }

    /**
     * 组合查询数据 查询姓名为"噼里啪啦酱" 并且成绩小于等于20
     */
    private void searchCombined() {
        QueryBuilder<Student> stuQB = studentDao.queryBuilder();
        List<Student> studentList = stuQB.where(StudentDao.Properties.StuName.eq("噼里啪啦酱")
                , StudentDao.Properties.StuScore.le("20")).list();
        if (studentList.size() > 0) {
            String searchAllInfo = "";
            for (Student stu : studentList) {
                searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
            }
            LogUtil.d(searchAllInfo);
        }
    }

    /**
     * 查询指定数据 查询姓名为"啦啦啦"的信息并按照成绩排序-升序
     */
    private void searchAssignOrderAsc() {
        // 升序 orderAsc
        // 降序 orderDesc
        // 自定义顺序 orderCustom
        List<Student> stuSortList = studentDao.queryBuilder()
                .where(StudentDao.Properties.StuName.eq("啦啦啦"))
                .orderAsc(StudentDao.Properties.StuScore)
                .list();
        if (stuSortList.size() > 0) {
            String searchAllInfo = "";
            for (Student stu : stuSortList) {
                searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
            }
            LogUtil.d(searchAllInfo);
        }
    }

    /**
     * 查询指定数据 查询姓名为"贺da宝"的信息
     */
    private void searchAssign() {
        List<Student> stuList = studentDao.queryBuilder().where(StudentDao.Properties.StuName.eq("啦啦啦")).list();
        if (stuList.size() > 0) {
            String searchAllInfo = "";
            for (Student stu : stuList) {
                searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
            }
            LogUtil.d(searchAllInfo);
        }
    }

    /**
     * 查询所有
     * 查询所有信息总条数
     */
    private void searchAll() {
        List<Student> students = studentDao.queryBuilder().list();
        int size = studentDao.queryBuilder().list().size();
        if (students.size() > 0) {
            String searchAllInfo = "";
            for (Student stu : students) {
                searchAllInfo += "id：" + stu.getStuId() + "编号：" + stu.getStuNo() + "姓名：" + stu.getStuName() + "性别：" + stu.getStuSex() + "成绩：" + stu.getStuScore() + "\n";
            }
            LogUtil.d("信息总条数" + size + "\n" + searchAllInfo);
        }
    }

    /**
     * 新增List集合数据
     */
    private void insertist() {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student(null, "002", "劈啪啪啪", "男p孩", "50"));
        studentList.add(new Student(null, "003", "啦啦啦", "男a孩", "23"));
        studentList.add(new Student(null, "007", "啦啦啦666", "男666孩", "23"));
        studentList.add(new Student(null, "004", "啪啪啪啪", "男q孩", "12"));
        studentList.add(new Student(null, "008", "啪啪啦啦啦啪啪", "男q孩", "12"));
        studentList.add(new Student(null, "005", "辟辟辟辟", "男c孩", "23"));
        studentList.add(new Student(null, "009", "辟辟啦啦啦辟辟", "男n孩", "23"));
        studentList.add(new Student(null, "006", "哩哩哩哩", "男v孩", "66"));
        studentDao.insertInTx(studentList);
        ToastUtil.show("添加成功~");
    }

    /**
     * 新增一条数据
     */
    private void insert() {
        Student student = new Student(null, "001", "噼里啪啦酱", "男孩", "50");
        long insert = studentDao.insert(student);
        if (insert > 0) {
            ToastUtil.show("001新增成功~");
        } else {
            ToastUtil.show("新增失败~");
        }
    }
}
