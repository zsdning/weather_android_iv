package com.iframe.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iframe.R;
import com.iframe.base.BaseFragment;

/**
 * Created by zsdning on 16/5/31.  设置页
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private SettingActivity activity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View set_view = inflater.inflate(R.layout.fragment_setting, container, false);
        activity = (SettingActivity) getActivity();
        findView(set_view);
        getInfo();
        return set_view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (SettingActivity) getActivity();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView(View view) {

    }

    private void getInfo() {

    }

    @Override
    public void onClick(View v) {

    }
}
