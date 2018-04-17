package com.example.a140.civilservant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a140.civilservant.R;
import com.example.a140.civilservant.ui.AnnouncementActivity;
import com.example.a140.civilservant.ui.BasicActivity;
import com.example.a140.civilservant.ui.HistoryActivity;
import com.example.a140.civilservant.ui.InstitutionActivity;
import com.example.a140.civilservant.ui.ProcessActivity;
import com.example.a140.civilservant.ui.SecretActivity;
import com.example.a140.civilservant.ui.TimeActivity;
import com.example.a140.civilservant.ui.TitleActivity;

/**
 * Created by a140 on 2018/4/11.
 * "知识"页面
 */

public class KnowFragment extends Fragment {
    /*
    * 历史沿革
    * 基本形式
    * 考试流程
    * 招考公告
    * 考试时间
    * 录用机关
    * 国考揭秘
    * 申论题目
    * */
    private TextView tv_history;
    private TextView tv_basic;
    private TextView tv_process;
    private TextView tv_announcement;
    private TextView tv_time;
    private TextView tv_institution;
    private TextView tv_secret;
    private TextView tv_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_know, null);
        initView(view);
        return view;
    }

    //初始化View
    private void initView(View view) {
        /*点击事件，点击进入各个页面*/
        //历史沿革
        tv_history = view.findViewById(R.id.tv_history);
        tv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        //基本形式
        tv_basic = view.findViewById(R.id.tv_basic);
        tv_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BasicActivity.class);
                startActivity(intent);
            }
        });
        //考试流程
        tv_process = view.findViewById(R.id.tv_process);
        tv_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProcessActivity.class);
                startActivity(intent);
            }
        });
        //招考公告
        tv_announcement = view.findViewById(R.id.tv_announcement);
        tv_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AnnouncementActivity.class);
                startActivity(intent);
            }
        });
        //考试时间
        tv_time = view.findViewById(R.id.tv_time);
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimeActivity.class);
                startActivity(intent);
            }
        });
        //录用机关
        tv_institution = view.findViewById(R.id.tv_institution);
        tv_institution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InstitutionActivity.class);
                startActivity(intent);
            }
        });
        //国考揭秘
        tv_secret = view.findViewById(R.id.tv_secret);
        tv_secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SecretActivity.class);
                startActivity(intent);
            }
        });
        //申论题目
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TitleActivity.class);
                startActivity(intent);
            }
        });
    }
}
