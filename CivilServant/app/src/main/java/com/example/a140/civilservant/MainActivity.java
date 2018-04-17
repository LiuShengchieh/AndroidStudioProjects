package com.example.a140.civilservant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a140.civilservant.fragment.HomeFragment;
import com.example.a140.civilservant.fragment.KnowFragment;
import com.example.a140.civilservant.fragment.MeFragment;
import com.example.a140.civilservant.utils.StaticClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/*
* 公务员考试app
* */

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    //ViewPager
    private ViewPager mViewPager;
    //Fragment
    private List<Fragment> mFragment;

    //导航栏Tab
    private LinearLayout mTabHome;
    private LinearLayout mTabKnow;
    private LinearLayout mTabMe;

    //按钮的图片
    private ImageButton mHomeImg;
    private ImageButton mKnowImg;
    private ImageButton mMeImg;

    //按钮的文字
    private TextView mHomeText;
    private TextView mKnowText;
    private TextView mMeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //计算当前手机的屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        StaticClass.WIDTH = dm.widthPixels;

        //初始化View
        initView();
        //切换事件
        initEvents();
        //初始化数据库数据
        initData();
    }

    private void initData() {
        //数据库路径
        String DB_PATH = "/data/data/com.example.a140.civilservant/databases/";
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
        mViewPager = findViewById(R.id.id_viewpager);

        mTabHome = findViewById(R.id.id_tab_home);
        mTabKnow = findViewById(R.id.id_tab_know);
        mTabMe = findViewById(R.id.id_tab_me);

        mHomeImg = findViewById(R.id.id_tab_home_img);
        mKnowImg = findViewById(R.id.id_tab_know_img);
        mMeImg = findViewById(R.id.id_tab_me_img);

        mHomeText = findViewById(R.id.id_tab_home_text);
        mKnowText = findViewById(R.id.id_tab_know_text);
        mMeText = findViewById(R.id.id_tab_me_text);

        //添加碎片
        mFragment = new ArrayList<>();
        mFragment.add(new HomeFragment());
        mFragment.add(new KnowFragment());
        mFragment.add(new MeFragment());

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        });
    }

    //切换事件
    private void initEvents() {
        mTabHome.setOnClickListener(this);
        mTabKnow.setOnClickListener(this);
        mTabMe.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //根据页面的切换设置按钮图片和按钮文字颜色
            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "Position: " + position);
                int currentItem = mViewPager.getCurrentItem();
                resetImg();
                resetText();
                switch (currentItem) {
                    case 0:
                        mHomeImg.setImageResource(R.drawable.home_pressed);
                        mHomeText.setTextColor(getColor(R.color.textPressed));
                        break;
                    case 1:
                        mKnowImg.setImageResource(R.drawable.know_pressed);
                        mKnowText.setTextColor(getColor(R.color.textPressed));
                        break;
                    case 2:
                        mMeImg.setImageResource(R.drawable.me_pressed);
                        mMeText.setTextColor(getColor(R.color.textPressed));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //按钮的点击事件
    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.id_tab_home:
                mViewPager.setCurrentItem(0);
                mHomeImg.setImageResource(R.drawable.home_pressed);
                mHomeText.setTextColor(getColor(R.color.textPressed));
                break;
            case R.id.id_tab_know:
                mViewPager.setCurrentItem(1);
                mKnowImg.setImageResource(R.drawable.know_pressed);
                mKnowText.setTextColor(getColor(R.color.textPressed));
                break;
            case R.id.id_tab_me:
                mViewPager.setCurrentItem(2);
                mMeImg.setImageResource(R.drawable.me_pressed);
                mMeText.setTextColor(getColor(R.color.textPressed));
                break;
            default:
                break;
        }
    }

    //复原按钮图片
    private void resetImg() {
        mHomeImg.setImageResource(R.drawable.home_normal);
        mKnowImg.setImageResource(R.drawable.know_normal);
        mMeImg.setImageResource(R.drawable.me_normal);
    }

    //复原按钮文字颜色
    private void resetText() {
        mHomeText.setTextColor(this.getResources().getColor(R.color.textNormal));
        mKnowText.setTextColor(this.getResources().getColor(R.color.textNormal));
        mMeText.setTextColor(this.getResources().getColor(R.color.textNormal));
    }
}
