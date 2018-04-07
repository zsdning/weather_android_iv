package com.iframe.ui.testapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iframe.base.BaseActivity;
import com.iframe.ui.main.MainActivity;

public class TestApiActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void back(View view) {
//        super.back(view);
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
