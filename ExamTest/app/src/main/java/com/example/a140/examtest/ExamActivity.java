package com.example.a140.examtest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a140 on 2018/4/4.
 * 答题系统
 */

public class ExamActivity extends AppCompatActivity {
    //题目总数
    private int count;
    //当前题目
    private int current;
    //错题模式
    private boolean wrongNode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        DBService dbService = new DBService();
        final List<Question> list = dbService.getQuestion();

        count = list.size();
        current = 0;
        wrongNode = false;

        final TextView tv_question = (TextView) findViewById(R.id.question);
        final RadioButton[] radioButtons = new RadioButton[4];
        radioButtons[0] = (RadioButton) findViewById(R.id.answerA);
        radioButtons[1] = (RadioButton) findViewById(R.id.answerB);
        radioButtons[2] = (RadioButton) findViewById(R.id.answerC);
        radioButtons[3] = (RadioButton) findViewById(R.id.answerD);
        Button btn_next = (Button) findViewById(R.id.btn_next);
        Button btn_previous = (Button) findViewById(R.id.btn_previous);
        final TextView tv_explanation = (TextView) findViewById(R.id.explanation);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //显示的第一道题目
        Question q = list.get(0);
        tv_question.setText(q.question);
        tv_explanation.setText(q.explanation);
        radioButtons[0].setText(q.answerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);
        radioButtons[3].setText(q.answerD);

        //点击下一题
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否为最后一题
                if (current < count - 1) {
                    //不是最后一题
                    current++;
                    Question q = list.get(current);//final 不然不能从内部类中访问
                    tv_question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);
                    tv_explanation.setText(q.explanation);
                    //设为选中
                    radioGroup.clearCheck();
                    if (q.selectedAnswer != -1) {
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                } else if (current == count - 1 && wrongNode == true) {
                    //最后一题并处于错题模式
                    new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("提示")
                            .setMessage("已是最后一题，是否退出？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ExamActivity.this.finish();
                                }
                            })
                            .setNegativeButton("否", null)
                            .show();
                } else {
                    //判断做过的题目对错并显示结果
                    final List<Integer> wrongList = checkAnswer(list);
                    if (wrongList.size() == 0) {
                        //Dialog提示全对然后退出
                        new AlertDialog.Builder(ExamActivity.this)
                                .setTitle("提示")
                                .setMessage("恭喜你全部回答正确！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ExamActivity.this.finish();
                                    }
                                })
                                .show();
                    }
                    //Dialog提示对错数目并询问是否查看错题
                    new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("提示")
                            .setMessage("您答对了" + (list.size() - wrongList.size())
                                    + "道题目，答错了" + wrongList.size() + "道题目。是否查看错题？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    wrongNode = true;
                                    List<Question> newList = new ArrayList<Question>();
                                    for (int i = 0; i < wrongList.size(); i++) {
                                        newList.add(list.get(wrongList.get(i)));
                                    }
                                    list.clear();
                                    for (int i = 0; i < newList.size(); i++) {
                                        list.add(newList.get(i));
                                    }
                                    current = 0;
                                    count = list.size();
                                    Question q = list.get(current);//final 不然不能从内部类中访问
                                    tv_question.setText(q.question);
                                    radioButtons[0].setText(q.answerA);
                                    radioButtons[1].setText(q.answerB);
                                    radioButtons[2].setText(q.answerC);
                                    radioButtons[3].setText(q.answerD);
                                    tv_explanation.setText(q.explanation);
                                    tv_explanation.setVisibility(View.VISIBLE);
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ExamActivity.this.finish();
                                }
                            })
                            .show();

                }
            }
        });

        //上一题
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current > 0) {
                    current--;
                    Question q = list.get(current);//final 不然不能从内部类中访问
                    tv_question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);
                    tv_explanation.setText(q.explanation);
                    //设为选中
                    radioGroup.clearCheck();
                    if (q.selectedAnswer != -1) {
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                }
            }
        });

        //选中状态
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                for (i = 0; i < 4; i++) {
                    if (radioButtons[i].isChecked() == true) {
                        list.get(current).selectedAnswer = i;
                        break;
                    }
                }
            }
        });
    }

    //判断答案对错
    private List<Integer> checkAnswer(List<Question> list) {
        List<Integer> wrongList = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).answer != list.get(i).selectedAnswer) {
                wrongList.add(i);
            }
        }
        return wrongList;
    }

}