package com.iframe.ui.settings;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        title = (TextView) super.findViewById(R.id.title);
        title.setText("我的设置");


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.setting, new SettingFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void back(View view) {
        super.back(view);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
