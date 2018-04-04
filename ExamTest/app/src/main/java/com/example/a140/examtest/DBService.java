package com.example.a140.examtest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a140 on 2018/4/4.
 * 连接数据库
 */

public class DBService {
    private SQLiteDatabase db;

    //构造方法
    public DBService() {
        //连接数据库
        db = SQLiteDatabase.openDatabase("/data/data/com.example.a140.examtest/databases/question.db", null, SQLiteDatabase.OPEN_READWRITE);
    }

    //获取数据库中的数据
    public List<Question> getQuestion() {
        List<Question> list = new ArrayList<>();
        //执行sql语句
        Cursor cursor = db.rawQuery("select * from question", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            //遍历
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                //问题
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                //答案
                question.answerA = cursor.getString(cursor.getColumnIndex("answerA"));
                question.answerB = cursor.getString(cursor.getColumnIndex("answerB"));
                question.answerC = cursor.getString(cursor.getColumnIndex("answerC"));
                question.answerD = cursor.getString(cursor.getColumnIndex("answerD"));
                //正确答案
                question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                //ID
                question.ID = cursor.getInt(cursor.getColumnIndex("ID"));
                //解释
                question.explanation = cursor.getString(cursor.getColumnIndex("explaination"));
                //没有选择任何答案
                question.selectedAnswer = -1;
                list.add(question);
            }
        }
        return list;
    }

}
