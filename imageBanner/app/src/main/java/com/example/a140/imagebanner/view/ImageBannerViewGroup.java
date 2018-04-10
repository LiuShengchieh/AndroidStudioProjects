package com.example.a140.imagebanner.view;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by a140 on 2018/4/8.
 * 实现图片轮播的核心类
 */

public class ImageBannerViewGroup extends ViewGroup {

    //子视图的总个数
    private int children;
    //子视图的宽度
    private int childWidth;
    //子视图的高度
    private int childHeight;

    private int x;
    private int index = 0;

    private Scroller scroller;

    /*
    * 图片的单击事件
    * */
    private boolean isClick;
    private ImageBannerListener listener;

    public ImageBannerListener getListener() {
        return listener;
    }

    public void setListener(ImageBannerListener listener) {
        this.listener = listener;
    }

    public interface ImageBannerListener {
        void clickImageIndex(int pos);
    }

    private ImageBannerViewGroupListener imageBannerViewGroupListener;

    public ImageBannerViewGroupListener getImageBannerViewGroupListener() {
        return imageBannerViewGroupListener;
    }

    public void setImageBannerViewGroupListener(ImageBannerViewGroupListener imageBannerViewGroupListener) {
        this.imageBannerViewGroupListener = imageBannerViewGroupListener;
    }

    /*
    * 自动轮播
    * */
    private boolean isAuto = true;
    private Timer timer = new Timer();
    private TimerTask task;
    private android.os.Handler autoHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (++index >= children) {
                        index = 0;
                    }
                    scrollTo(childWidth * index, 0);
                    imageBannerViewGroupListener.selectImage(index);
                    break;
            }
        }
    };

    //开始轮播
    private void startAuto() {
        isAuto = true;
    }

    //停止轮播
    private void stopAuto() {
        isAuto = false;
    }

    public ImageBannerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj() {
        scroller = new Scroller(getContext());
        task = new TimerTask() {
            @Override
            public void run() {
                if (isAuto) {
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(task, 100, 3000);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    /*
        * 自定义ViewGroup布局，测量-》布局-》绘制
        * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //1 子视图的个数
        children = getChildCount();
        if (children == 0) {
            setMeasuredDimension(0, 0);
        } else {
            //2 测量子视图的宽度和高度
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            View view = getChildAt(0);
            childWidth = view.getMeasuredWidth();
            //3 根据子视图的宽度和高度求出ViewGroup的宽度和高度
            childHeight = view.getMeasuredHeight();
            //所有子视图的宽度之和
            int width = view.getMeasuredWidth() * children;
            setMeasuredDimension(width, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        if (change) {
            int leftMargin = 0;
            for (int i = 0; i < children; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin, 0, leftMargin + childWidth, childHeight);
                leftMargin += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //按下的一瞬间
            case MotionEvent.ACTION_DOWN:
                stopAuto();
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                x = (int) event.getX();
                isClick = true;
                break;
            //在屏幕上移动
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance, 0);
                x = moveX;
                isClick = false;
                break;
            //抬起的一瞬间
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                index = (scrollX + childWidth / 2) / childWidth;
                if (index < 0) {
                    //滑动到最左边一张图片
                    index = 0;
                } else if (index > children - 1) {
                    //滑动到最右边一张图片
                    index = children - 1;
                }
                if (isClick = true) {
                    listener.clickImageIndex(index);
                } else {
                    int dx = index * childWidth - scrollX;
                    scroller.startScroll(scrollX, 0, dx, 0);
                    postInvalidate();
                    imageBannerViewGroupListener.selectImage(index);
                }
                startAuto();
                break;
            default:
                break;
        }
        //返回true表示该ViewGroup的parent view已经处理好该事件
        return true;
    }

    public interface ImageBannerViewGroupListener {
        void selectImage(int index);
    }

}
