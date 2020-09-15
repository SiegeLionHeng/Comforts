package com.outrank.global.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;

import com.outrank.global.utils.LogUtils;

public class FlowScrollView extends NestedScrollView {


    private View contentView;
    private final float scalCount = 0.4f;//阻尼系数
    private float downY;//点击时的y点
    private ObjectAnimator objectAnimator;//动画
    private float distanceY;//移动距离
    private boolean isMoveing = false;//动画是正在进行

    public FlowScrollView(@NonNull Context context) {
        super(context);
    }

    public FlowScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isMoveing) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltY = ev.getY() - downY;
                if (deltY > 0 && Math.abs(deltY) > 10 && (getScrollY() == 0 || (getScrollY() + getHeight()) == contentView.getHeight())) {
                    contentView.setY(contentView.getY() + deltY * scalCount);
                    distanceY += deltY * scalCount;
                }
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(distanceY) > 0) {
                    objectAnimator = ObjectAnimator.ofFloat(contentView, "translationY", distanceY, -(float) contentView.getTop());
//                    objectAnimator.setInterpolator(new BounceInterpolator());
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            isMoveing = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            distanceY = 0;
                            isMoveing = false;
                        }
                    });
                    objectAnimator.setDuration(500);
                    objectAnimator.start();
                }

                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
