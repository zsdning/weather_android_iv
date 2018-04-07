package com.iframe;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.iframe.net.model.User;
import com.iframe.util.OkHttpClientManager;
import com.squareup.okhttp.Request;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    //测试时可在HomeFragment中
    //okHttp测试
    private void testOkHttp(){
        OkHttpClientManager.getAsyn("http://retrofit.devwiki.net/param?id=\"123\"",
                new OkHttpClientManager.ResultCallback<User>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(User us)
                    {
                        Log.i("okhttp", us.getDesc());
                    }
                });
    }
}