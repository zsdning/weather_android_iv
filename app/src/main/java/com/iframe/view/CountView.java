package com.iframe.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * 动画显示数字
 * Created by fhp on 15/1/7.
 */
public class CountView extends TextView {
    //动画时长 ms
    int duration = 500;
    float number;

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //调用时，用showNumberWithAnimation方法
    public void showNumberWithAnimation(float number) {
        //修改number属性，会调用setNumber方法
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "number", 0, number);
        objectAnimator.setDuration(duration);
        //加速器，从慢到快到再到慢
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
        DecimalFormat df = new DecimalFormat("#0.00");
        setText(df.format(number));
    }
}