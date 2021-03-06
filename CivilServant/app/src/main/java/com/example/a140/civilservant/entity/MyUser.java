package com.example.a140.civilservant.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by a140 on 2018/4/17.
 * 用户属性
 */

public class MyUser extends BmobUser {
    //年龄
    private int age;
    //性别
    private boolean sex;
    //简介
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
