package com.example.a140.civilservant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a140.civilservant.R;

/**
 * Created by a140 on 2018/4/11.
 * 知识：公务员考试知识点
 */

public class KnowFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_know, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }

}
