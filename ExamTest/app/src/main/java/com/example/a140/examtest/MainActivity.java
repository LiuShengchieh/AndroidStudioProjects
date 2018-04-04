package com.example.a140.examtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
* 答题系统
* */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化数据库
        initDb();
        //初始化View
        initView();
    }

    /*
    * 将数据库拷贝到相应目录
    * */
    private void initDb() {
        //数据库路径
        String DB_PATH = "/data/data/com.example.a140.examtest/databases/";
        //数据库名字
        String DB_NAME = "question.db";

        //判断数据库是否已复制
        if ((new File(DB_PATH + DB_NAME).exists()) == false) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }

            //复制文件
            try {
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
                //用来复制文件
                byte[] buffer = new byte[1024];
                //保存已经复制的文件长度
                int length;
                //开始复制
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                //Refresh
                os.flush();
                //Close
                os.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        //开始答题
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExamActivity.class);
                startActivity(intent);
            }
        });
    }

}
