package com.example.lion_personal.lionopensource.database.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Car {
    // id
    @Id
    private long id;

    // num
    @Unique
    @NotNull
    private String num;

    // name
    private String name;

    @Generated(hash = 239533764)
    public Car(long id, @NotNull String num, String name) {
        this.id = id;
        this.num = num;
        this.name = name;
    }

    @Generated(hash = 625572433)
    public Car() {
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
}
