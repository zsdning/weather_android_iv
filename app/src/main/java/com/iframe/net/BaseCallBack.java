package com.iframe.net;

import android.content.Intent;
import android.util.Log;

import com.creditcloud.utils.NetWorkUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iframe.base.BaseActivity;
import com.iframe.net.model.NetError;
import com.iframe.ui.login.LoginActivity;
import com.iframe.util.SPHelper;
import com.iframe.util.Utils;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BaseCallBack<T> implements Callback<T> {

    private BaseActivity activity = null;

    public BaseCallBack(BaseActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        if (activity == null) {
            return;
        }
        if (!response.isSuccess() || response.errorBody() != null) {//对于登陆超时的处理
            NetError error;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                error = objectMapper.readValue(response.errorBody().string(), NetError.class);
                if (error.getCode().equals("TokenNotExistsException")) {
                    SPHelper.getInstance().setLoginToken("");
                    SPHelper.getInstance().cleanLoginInfo();
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.finish();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onFailure(Throwable throwable) {
        if (activity == null) {
            return;
        }
        Log.e(activity.getTAG(), "" + throwable.getMessage());
        if (!NetWorkUtils.detect(activity)) {
            Utils.showShortToast(activity, "暂无网络连接，请检查您的网络");
            return;
        }
    }
}


