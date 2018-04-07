package com.iframe.ui.account;

import android.os.Bundle;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;

public class FriendActivity extends BaseActivity {
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        title = (TextView) findViewById(R.id.title);
        title.setText("好友动态");
    }
}
