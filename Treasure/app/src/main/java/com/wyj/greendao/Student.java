package com.wyj.greendao;

/**
 * @author wangyujie
 * @date 2018/9/21.15:56
 * @describe 添加描述
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author wangyujie
 * @date 2018/9/21.16:05
 * @describe
 * @Entity 注解标记了一个Java类作为greenDAO一个presistable实体。
 *         简单理解为，他告诉GreenDao，要根据这个实体类去生成相应的Dao，
 *         方便我们去操作，同样也相当于将我们的实体类和表做了关联
 * @Id     选择long / Long属性作为实体ID。在数据库方面，它是主要的关键参数autoincrement
 *         是使ID值不断增加的标志（不重复使用旧值），也就是咱常说的**自增长**。
 *
 *         1. 编译后自动生成无参，有参构造；
 *         2. 编译后自动生成getter，setter；
 */
@Entity
public class Student {
    @Id(autoincrement = true) // id自增长
    private Long stuId; // 学院id

    @Index(unique = true) // 唯一性
    private String stuNo; // 学员编号

    private String stuName; // 学员姓名

    private String stuSex; // 学员性别

    private String stuScore; // 学员成绩

    @Generated(hash = 315497705)
    public Student(Long stuId, String stuNo, String stuName, String stuSex,
            String stuScore) {
        this.stuId = stuId;
        this.stuNo = stuNo;
        this.stuName = stuName;
        this.stuSex = stuSex;
        this.stuScore = stuScore;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public Long getStuId() {
        return this.stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getStuNo() {
        return this.stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return this.stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuSex() {
        return this.stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuScore() {
        return this.stuScore;
    }

    public void setStuScore(String stuScore) {
        this.stuScore = stuScore;
    }

}
