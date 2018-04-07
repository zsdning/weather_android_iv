package com.iframe.ui.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.iframe.R;
import com.iframe.base.BaseFragment;
import com.iframe.realm.module.Weather;

/**
 * Created by zsdning on 2016/9/2.
 */
public class BasketBallFragment extends BaseFragment {
    private WebView mWebView;
    private Weather tempWeather;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (OutdoorActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_basketball, container, false);
        findView(view);
        return view;
    }

    private void findView(View view){

    }


}
