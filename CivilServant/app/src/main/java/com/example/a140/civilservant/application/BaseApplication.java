package com.example.a140.civilservant.application;

import android.app.Application;

import com.example.a140.civilservant.utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * Created by a140 on 2018/4/17.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
