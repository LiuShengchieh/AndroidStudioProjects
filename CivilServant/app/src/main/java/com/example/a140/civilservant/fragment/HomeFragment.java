package com.example.a140.civilservant.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a140.civilservant.R;
import com.example.a140.civilservant.ui.ChangsActivity;
import com.example.a140.civilservant.ui.ExamActivity;
import com.example.a140.civilservant.ui.PandActivity;
import com.example.a140.civilservant.ui.ShulActivity;
import com.example.a140.civilservant.ui.YanyuActivity;
import com.example.a140.civilservant.ui.ZiliaoActivity;
import com.example.a140.civilservant.view.ImageBannerFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a140 on 2018/4/10.
 * "考试"页面：三个部分
 * 1、轮播图
 * bug:手动轮播无效
 * 2、横向排列（整卷、错题、收藏、笔记）
 * 错题、收藏、笔记，因为时间问题，来不及写功能代码，点击的话只会弹出Toast
 * 3、纵向排列（不同类型的题目练习）
 * 每个类型5道题目
 */

public class HomeFragment extends android.support.v4.app.Fragment implements ImageBannerFrameLayout.FrameLayoutListener {
    //整卷、错题、收藏、笔记
    private LinearLayout ButtonExam;
    private LinearLayout ButtonWrong;
    private LinearLayout ButtonLike;
    private LinearLayout ButtonNote;

    //类型题目
    private LinearLayout testyanyu;
    private LinearLayout testshul;
    private LinearLayout testpand;
    private LinearLayout testziliao;
    private LinearLayout testchangshi;

    //轮播图
    private ImageBannerFrameLayout mGroup;

    //轮播图的图片
    private int[] ids = new int[]{
            R.drawable.banner,
            R.drawable.banner1,
            R.drawable.banner2
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        /*整卷、错题、收藏、笔记*/
        ButtonExam = view.findViewById(R.id.id_button_exam);
        ButtonExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExamActivity.class);
                startActivity(intent);
            }
        });
        ButtonWrong = view.findViewById(R.id.id_button_wrong);
        ButtonWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "错题", Toast.LENGTH_SHORT).show();
            }
        });
        ButtonLike = view.findViewById(R.id.id_button_like);
        ButtonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "收藏", Toast.LENGTH_SHORT).show();
            }
        });
        ButtonNote = view.findViewById(R.id.id_button_note);
        ButtonNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "笔记", Toast.LENGTH_SHORT).show();
            }
        });

        /*5种类型题的点击事件，点击后跳转做题*/
        testyanyu = view.findViewById(R.id.id_test_yanyu);
        testyanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), YanyuActivity.class);
                startActivity(intent);
            }
        });

        testshul = view.findViewById(R.id.id_test_shul);
        testshul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShulActivity.class);
                startActivity(intent);
            }
        });
        testpand = view.findViewById(R.id.id_test_pand);
        testpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PandActivity.class);
                startActivity(intent);
            }
        });
        testziliao = view.findViewById(R.id.id_test_ziliao);
        testziliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ZiliaoActivity.class);
                startActivity(intent);
            }
        });
        testchangshi = view.findViewById(R.id.id_test_changshi);
        testchangshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangsActivity.class);
                startActivity(intent);
            }
        });

        /*轮播图*/
        mGroup = view.findViewById(R.id.image_group);
        List<Bitmap> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ids[i]);
            list.add(bitmap);
        }
        //将图片加载到ImageBannerFrameLayout
        mGroup.addBitmaps(list);
        //设置点击图片的监听事件
        mGroup.setFrameLayoutListener(this);
    }

    //点击轮播图的图片
    @Override
    public void clickImageIndex(int pos) {
        Toast.makeText(getContext(), "pos=" + pos, Toast.LENGTH_SHORT).show();
    }
}
