package com.iframe.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.aidl.BookManagerActivity;
import com.iframe.provider.ProviderActivity;

public class FeedbackActivity extends BaseActivity {
    private TextView title;
    private TextView aidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        title = (TextView) findViewById(R.id.title);
        title.setText("意见反馈");

        aidl = (TextView) findViewById(R.id.aidl);

        //AIDL测试  Provider测试
        aidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackActivity.this, BookManagerActivity.class));
                startActivity(new Intent(FeedbackActivity.this, ProviderActivity.class));
            }
        });
    }
}
