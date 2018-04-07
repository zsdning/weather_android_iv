package com.iframe.ui.account;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.view.CustomViewPager;

public class OutdoorActivity extends BaseActivity {
    private TextView title;
    private TabLayout tab;
    private CustomViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);

        title = (TextView) findViewById(R.id.title);
        title.setText("活动");

        tab = (TabLayout) findViewById(R.id.outdoor_list_tab);
        pager = (CustomViewPager) findViewById(R.id.outdoor_list_pager);

        OutdoorListAdapter adapter = new OutdoorListAdapter(this.getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
        tab.setTabMode(TabLayout.MODE_FIXED);
    }
}
