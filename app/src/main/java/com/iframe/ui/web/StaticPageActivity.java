package com.iframe.ui.web;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;

public class StaticPageActivity extends BaseActivity {

    public final static int ABOUTUS = 1;
    public final static int TUIJIAN = 2;
    public final static int ACCOUNT = 3;
    public final static int REGIST = 4;
    private TextView title;
    private WebView webView;
    private int mode;

    public static void startAc(Context context, int mode) {
        Intent intent = new Intent(context, StaticPageActivity.class);
        intent.putExtra("mode", mode);
        context.startActivity(intent);
    }

    @Override
    @TargetApi(14)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getIntents();
        findView();
    }

    private void getIntents() {
        Intent intent = this.getIntent();
        if (intent != null) {
            mode = intent.getIntExtra("mode", 0);
        }
    }

    private void findView() {
        title = (TextView) findViewById(R.id.title);
        webView = (WebView) findViewById(R.id.webview);
        String name = "";
        switch (mode) {
            case ABOUTUS:
                title.setText("关于我们");
                name = "aboutus.html";
                break;
            case TUIJIAN:
                title.setText("推荐好友");
                name = "tuijian.html";
                break;
            case ACCOUNT:
                title.setText("账户密码");
                name = "account.html";
                break;
            case REGIST:
                title.setText("注册登陆");
                name = "regist.html";
                break;
        }
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        // webView.getSettings().setUseWideViewPort(true);
        settings.setRenderPriority(RenderPriority.HIGH);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(false);
        // settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        // mWebView.getSettings().setBlockNetworkImage(true);

        webView.loadUrl("file:///android_asset/" + name);
    }

    public void back(View view) {
        finish();
    }

}
