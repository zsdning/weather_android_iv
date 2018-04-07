package com.iframe.ui.account;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.ui.web.StaticPageActivity;

public class HelpActivity extends BaseActivity {

    private TextView title;

    @Override
    @TargetApi(14)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        findView();
    }

    private void findView() {

        title = (TextView) findViewById(R.id.title);
        title.setText("帮助中心");

    }

    public void back(View view) {
        finish();
    }

    //onclick = "goAccount"
    public void goAccount(View view) {
        StaticPageActivity.startAc(this, StaticPageActivity.ACCOUNT);
    }

    public void goTuijian(View view){
        StaticPageActivity.startAc(this, StaticPageActivity.TUIJIAN);
    }

    public void goRegist(View view){
        StaticPageActivity.startAc(this, StaticPageActivity.REGIST);
    }
}
