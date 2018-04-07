package com.iframe.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zsdning on 2016/7/16.
 */
public class NoSlidingViewpager extends ViewPager {

    public NoSlidingViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NoSlidingViewpager(Context context) {

        super(context);
    }

    private boolean isCanScroll = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isCanScroll) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }

    }

    public boolean isScrollble() {
        return isCanScroll;
    }

    public void setScrollble(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
}