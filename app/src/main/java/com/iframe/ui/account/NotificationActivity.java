package com.iframe.ui.account;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;

public class NotificationActivity extends BaseActivity {
    private TextView title;
    private TextView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        title = (TextView) findViewById(R.id.title);
        title.setText("系统通知");
        //动画测试
        notification = (TextView) findViewById(R.id.tv_notification);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        notification.startAnimation(alphaAnimation);

    }
}
