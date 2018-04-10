package com.example.a140.imagebanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.a140.imagebanner.view.ImageBannerFrameLayout;

import java.util.ArrayList;
import java.util.List;

/*
* 图片轮播
* */

public class MainActivity extends AppCompatActivity implements ImageBannerFrameLayout.FrameLayoutListener {

    private ImageBannerFrameLayout mGroup;

    //图片
    private int[] ids = new int[]{
            R.drawable.banner,
            R.drawable.banner1,
            R.drawable.banner2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //图片所在的页面
        setContentView(R.layout.activity_main);

        //计算当前手机的屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        C.WIDTH = dm.widthPixels;

        mGroup = findViewById(R.id.image_group);

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

    @Override
    public void clickImageIndex(int pos) {
        Toast.makeText(this, "pos=" + pos, Toast.LENGTH_SHORT).show();
    }

}
