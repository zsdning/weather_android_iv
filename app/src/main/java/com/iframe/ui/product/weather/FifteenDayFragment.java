package com.iframe.ui.product.weather;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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
public class FifteenDayFragment extends BaseFragment {
    private WebView mWebView;
    private Weather tempWeather;
    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_fifteenday, container, false);
        findView(view);
        return view;
    }

    private void findView(View view){
        mWebView = (WebView) view.findViewById(R.id.fifteenday_view);
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
        mWebView.loadUrl("file:///android_asset/fifteenday.html");

        mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");
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
            int i;
            for(i = 0;i < tempWeather.getNextDays().size();i++){
                weeks.add(tempWeather.getNextDays().get(i).getDate());
                states.add(tempWeather.getNextDays().get(i).getState());
                lowTemps.add(Integer.parseInt(tempWeather.getNextDays().get(i).getLowtemp()));
                highTemps.add(Integer.parseInt(tempWeather.getNextDays().get(i).getHightemp()));
                windDirects.add(tempWeather.getNextDays().get(i).getWindDirect());
                windPowers.add(tempWeather.getNextDays().get(i).getWindPower());
            }
            //模拟数据  需要修改
            for(i = 8;i < 15;i++){
                weeks.add(tempWeather.getNextDays().get(i-8).getDate());
                states.add(tempWeather.getNextDays().get(i-8).getState());
                lowTemps.add(Integer.parseInt(tempWeather.getNextDays().get(i-8).getLowtemp()));
                highTemps.add(Integer.parseInt(tempWeather.getNextDays().get(i-8).getHightemp()));
                windDirects.add(tempWeather.getNextDays().get(i-8).getWindDirect());
                windPowers.add(tempWeather.getNextDays().get(i-8).getWindPower());
            }
        }
        Gson mGson = new Gson();
        Parms parms = new Parms(weeks,states,lowTemps,highTemps,windDirects,windDirects);
        //这里调用JS方法并传递参数 方法名要唯一不能与其他页面相同
        mWebView.loadUrl("javascript:showData3('" + mGson.toJson(parms) + "')");
    }

    final class DemoJavaScriptInterface {
        DemoJavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * <p/>
         * loadUrl on the UI thread.
         */
        @JavascriptInterface
        // 注意这里的名称。它为clickOnAndroid(),注意，注意，严重注意
        public void clickOnAndroid() {
            /*
            handler.post这个函数的作用是把Runnable里面的run方法里面的这段代码发送到消息队列中，等待运行。
            如果handler是以UI线程消息队列为参数构造的，那么是把run里面的代码发送到UI线程中，等待UI线程运行这段代码。
            如果handler是以子线程线程消息队列为参数构造的，那么是把run里面的代码发送到子线程中，等待子线程运行这段代码。
             */
            mHandler.post(new Runnable() {
                public void run() {
                    Toast.makeText(getContext(), "后面8天是模拟数据", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
