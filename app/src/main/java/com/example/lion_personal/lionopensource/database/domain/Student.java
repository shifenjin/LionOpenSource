package com.example.lion_personal.lionopensource.database.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.identityscope.IdentityScopeLong;

@Entity
public class Student {

    // id
    @Id
    private long id;

    // 编号
    @Unique
    @NotNull
    private String num;

    // 姓名
    private String name;

    // 性别
    private String sex;

    // 成绩
    private String score;

    @Generated(hash = 1539662914)
    public Student(long id, @NotNull String num, String name, String sex,
            String score) {
        this.id = id;
        this.num = num;
        this.name = name;
        this.sex = sex;
        this.score = score;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}
