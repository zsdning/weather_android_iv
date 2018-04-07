package com.iframe.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.net.Net;
import com.iframe.net.model.UserInfo;
import com.iframe.ui.main.MainActivity;
import com.iframe.ui.register.RegisterActivity;
import com.iframe.util.RSAUtil;
import com.iframe.util.SPHelper;
import com.iframe.view.ClearEditText;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends BaseActivity {
    private Button loginBtn, registBtn, forgetBtn;
    private ClearEditText userName;
    private ClearEditText password;
    private String name;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getRSAKey();
        findView();
    }

    private void findView(){
        userName = (ClearEditText) findViewById(R.id.et_login_account);
        password = (ClearEditText) findViewById(R.id.et_login_password);

        //根据登录状态判断是否设置用户名
        SharedPreferences sharedPreferences = getSharedPreferences("user",Context.MODE_PRIVATE);
        boolean state = sharedPreferences.getBoolean("loginState",false);
        if(state == true){
            userName.setText(sharedPreferences.getString("userName",""));
        }

        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        registBtn = (Button) findViewById(R.id.login_regist_btn);
        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        forgetBtn = (Button) findViewById(R.id.login_forget_btn);
        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                LoginActivity.this.startActivity(intent);*/
            }
        });
    }


    private void login() {
        name = userName.getText().toString().trim();

        pwd = password.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showSystemShortToast(this.getString(R.string.regesiter_user_hintname));
            userName.setShakeAnimation();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showSystemShortToast(this.getString(R.string.regesiter_user_hintpwd));
            password.setShakeAnimation();
            return;
        }
        if (key == null) {
            showNetErrorToast();
            getRSAKey();
            return;
        }
        String enPwd = RSAUtil.generatePwd(key, pwd);

        Net.getLoginRegisterService().appLogin(name, enPwd, key.getPwkey()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    SPHelper.getInstance().setLoginToken(response.body());
                    //登录成功  查询用户信息并设置登录Token
                    queryUserInfo();
                } else {
                    showShortToast(Net.getError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                showNetErrorToast();
                throwable.printStackTrace();
            }
        });
    }

    private void queryUserInfo() {
        Net.getAccountService().userQuery(SPHelper.getInstance().getLoginToken(), name.toString().trim()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Response<UserInfo> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    SPHelper.getInstance().setLoginInfo(response.body());
                    //登陆时，保存用户名
                    getSharedPreferences("user", Context.MODE_PRIVATE).edit()
                            .putBoolean("loginState", true)
                            .putString("userName",name)
                            .commit();
                    if (SPHelper.getInstance().isFirstLogin(SPHelper.getInstance().getUserName())) {//如果是第一次登陆
                        //startActivity(new Intent(LoginActivity.this, RegisitGestureActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    //新加步骤，确认用户是否已经鉴权
                    getAuthed();
                } else {
                    showShortToast(Net.getError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                showNetErrorToast();
                throwable.printStackTrace();
            }
        });
    }

    //刷新鉴权信息
    public void getAuthed() {
        /*IsUserAuthedRequest request = new IsUserAuthedRequest(SPHelper.getInstance().getUserId());
        request.setListener(new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean result) {
                //设置鉴权属性
                SPHelper.getInstance().setAuthed(SPHelper.getInstance().getUserId(), result);

                //设置登陆跳转
                // 如果同一账户登陆，则跳过手势密码部分
                if (TextUtils.isEmpty(SPHelper.getInstance().getGesture(SPHelper.getInstance().getUserId()))) {
                    startActivity(new Intent(BaseActivity.this, RegisitGestureActivity.class));
                } else {
                    if (ActivityCollector.isExistMainActivity()) {
                        finish();
                    } else {
                        startActivity(new Intent(BaseActivity.this, MainActivity.class));
                    }
                }

                Intent intent = new Intent();
                intent.setAction(MyInvestFragment.REFRESHACTION);
                sendBroadcast(intent);

                intent = new Intent();
                intent.setAction(HomeFragment.REFRESHACTION);
                sendBroadcast(intent);

                finish();
            }
        });
        request.setErrorlistener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showSystemShortToast("获取账户信息错误，请重新登录");
            }
        });
        volleyHttpClient.doNetTask(VolleyHttpClient.GET_AOUTH, request, false, false);*/
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MainActivity选中第一个tab
        Intent intent = new Intent();
        intent.setAction(MainActivity.JUMP_MAIN_ACTION);
        sendBroadcast(intent);
    }
}
