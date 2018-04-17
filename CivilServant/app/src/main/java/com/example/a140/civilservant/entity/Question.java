package com.example.a140.civilservant.entity;

/**
 * Created by a140 on 2018/4/11.
 * 数据库
 */

public class Question {
    /*
    * 对应的就是Filter1-7，还有一个选中的答案
    * */
    //问题
    public String question;
    //答案
    public String answerA;
    public String answerB;
    public String answerC;
    public String answerD;
    //正确答案
    public int answer;
    //解释
    public String explanation;
    //ID
    public int ID;
    //选中的答案
    public int selectedAnswer;
}
