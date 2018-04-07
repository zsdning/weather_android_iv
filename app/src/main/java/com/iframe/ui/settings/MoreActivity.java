package com.iframe.ui.settings;

import android.os.Bundle;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;

public class MoreActivity extends BaseActivity {
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        title = (TextView) super.findViewById(R.id.title);
        title.setText("更多");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_open_in_anim, R.anim.activity_close_out_anim);
    }
}
