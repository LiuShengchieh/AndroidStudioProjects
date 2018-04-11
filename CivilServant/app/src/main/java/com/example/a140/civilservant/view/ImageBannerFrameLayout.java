package com.example.a140.civilservant.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a140.civilservant.C;
import com.example.a140.civilservant.R;

import java.util.List;

/**
 * Created by a140 on 2018/4/10.
 * 图片轮播
 */

public class ImageBannerFrameLayout extends FrameLayout implements ImageBannerViewGroup.ImageBannerViewGroupListener,
        ImageBannerViewGroup.ImageBannerListener {

    private ImageBannerViewGroup imageBannerViewGroup;

    private LinearLayout linearLayout;

    private FrameLayoutListener frameLayoutListener;

    public FrameLayoutListener getFrameLayoutListener() {
        return frameLayoutListener;
    }

    public void setFrameLayoutListener(FrameLayoutListener frameLayoutListener) {
        this.frameLayoutListener = frameLayoutListener;
    }

    public ImageBannerFrameLayout(@NonNull Context context) {
        super(context);
        initImageBannerViewGroup();
        initDotLinearLayout();
    }

    public ImageBannerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageBannerViewGroup();
        initDotLinearLayout();
    }

    public ImageBannerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBannerViewGroup();
        initDotLinearLayout();
    }

    public void addBitmaps(List<Bitmap> list) {
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = list.get(i);
            addBitmapToImageBannerViewGroup(bitmap);
            addDotToLinearLayout();
        }
    }

    //添加底部圆点
    private void addDotToLinearLayout() {
        ImageView iv = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 5, 5, 5);
        iv.setLayoutParams(lp);
        iv.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(iv);
    }

    //添加图片
    private void addBitmapToImageBannerViewGroup(Bitmap bitmap) {
        ImageView iv = new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new ViewGroup.LayoutParams(C.WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
        iv.setImageBitmap(bitmap);
        imageBannerViewGroup.addView(iv);
    }

    //初始化图片轮播核心类
    private void initImageBannerViewGroup() {
        imageBannerViewGroup = new ImageBannerViewGroup(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        imageBannerViewGroup.setLayoutParams(lp);
        //将listener传递给FrameLayout
        imageBannerViewGroup.setImageBannerViewGroupListener(this);
        imageBannerViewGroup.setListener(this);
        addView(imageBannerViewGroup);
    }

    //初始化底部圆点布局
    private void initDotLinearLayout() {
        linearLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 40);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.RED);
        addView(linearLayout);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setAlpha(0.5f);
    }

    //切换图片显示时的圆点状态
    @Override
    public void selectImage(int index) {
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView iv = (ImageView) linearLayout.getChildAt(i);
            if (i == index) {
                iv.setImageResource(R.drawable.dot_select);
            } else {
                iv.setImageResource(R.drawable.dot_normal);
            }
        }
    }

    //点击图片
    @Override
    public void clickImageIndex(int pos) {
        frameLayoutListener.clickImageIndex(pos);
    }

    public interface FrameLayoutListener {
        void clickImageIndex(int pos);
    }
}
