package com.example.a140.civilservant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.a140.civilservant.MainActivity;
import com.example.a140.civilservant.R;
import com.example.a140.civilservant.utils.ShareUtils;
import com.example.a140.civilservant.utils.StaticClass;
import com.example.a140.civilservant.utils.UtilTools;

/**
 * Created by a140 on 2018/4/17.
 * 闪屏页
 */

public class SplashActivity extends AppCompatActivity {
    /*
    * 1. 延时2000ms
    * 2. 判断程序是否第一次运行
    * 3. 自定义字体
    * 4. Activity全屏主题
    * */

    private TextView tv_splash;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case (StaticClass.HANDLER_SPLASH):
                    //判断程序是否第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    //初始化view
    private void initView() {

        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);

        //设置字体
        UtilTools.setFont(this, tv_splash);

    }

    public boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            //标记已经启动过app
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }

    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

}
