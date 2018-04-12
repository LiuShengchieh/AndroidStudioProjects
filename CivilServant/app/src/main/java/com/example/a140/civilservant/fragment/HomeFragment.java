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

import com.example.a140.civilservant.ui.ExamActivity;
import com.example.a140.civilservant.R;
import com.example.a140.civilservant.ui.ShulActivity;
import com.example.a140.civilservant.ui.YanyuActivity;
import com.example.a140.civilservant.view.ImageBannerFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a140 on 2018/4/10.
 * 首页：三个部分
 * 1、轮播图
 * 2、横向排列（整卷、错题、收藏、笔记）
 * 3、纵向排列（不同类型的题目练习）
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

        //横向排列的四个部分
        ButtonExam = view.findViewById(R.id.id_button_exam);
        ButtonWrong = view.findViewById(R.id.id_button_wrong);
        ButtonLike = view.findViewById(R.id.id_button_like);
        ButtonNote = view.findViewById(R.id.id_button_note);

        //"整卷"的点击事件
        ButtonExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExamActivity.class);
                startActivity(intent);
            }
        });

        //类型题
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
        testziliao = view.findViewById(R.id.id_test_ziliao);
        testchangshi = view.findViewById(R.id.id_test_changshi);

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
