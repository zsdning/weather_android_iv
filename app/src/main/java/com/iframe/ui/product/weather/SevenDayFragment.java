package com.iframe.ui.product.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.iframe.R;
import com.iframe.base.BaseFragment;
import com.iframe.realm.module.Weather;
import com.iframe.ui.main.MainActivity;
import com.iframe.ui.product.weather.module.Parms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsdning on 2016/9/2.
 */
public class SevenDayFragment extends BaseFragment {
    private WebView mWebView;
    private Weather tempWeather;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_sevenday, container, false);
        findView(view);
        return view;
    }

    private void findView(View view){
        mWebView = (WebView) view.findViewById(R.id.sevenday_view);
        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setDisplayZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportMultipleWindows(true);
        settings.setLoadsImagesAutomatically(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getParms();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.loadUrl("file:///android_asset/sevenday.html");
    }


    /////调用JS，传递参数给JS
    public void getParms() {
        List<String> weeks = new ArrayList<String>();
        List<String> states = new ArrayList<String>();
        List<Integer> lowTemps = new ArrayList<Integer>();
        List<Integer> highTemps = new ArrayList<Integer>();
        List<String> windDirects = new ArrayList<String>();
        List<String> windPowers = new ArrayList<String>();
        //从缓存中取出数据
        try {
            tempWeather = MainActivity.weatherDao.getWeather("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(tempWeather != null){
            for(int i = 0;i < tempWeather.getNextDays().size();i++){
                weeks.add(tempWeather.getNextDays().get(i).getDate());
                states.add(tempWeather.getNextDays().get(i).getState());
                lowTemps.add(Integer.parseInt(tempWeather.getNextDays().get(i).getLowtemp()));
                highTemps.add(Integer.parseInt(tempWeather.getNextDays().get(i).getHightemp()));
                windDirects.add(tempWeather.getNextDays().get(i).getWindDirect());
                windPowers.add(tempWeather.getNextDays().get(i).getWindPower());
            }
        }
        Gson mGson = new Gson();
        Parms parms = new Parms(weeks,states,lowTemps,highTemps,windDirects,windDirects);
        //这里调用JS方法并传递参数 方法名要唯一不能与其他页面相同
        mWebView.loadUrl("javascript:showData2('" + mGson.toJson(parms) + "')");
    }

}
