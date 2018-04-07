package com.iframe.ui.register;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.net.Net;
import com.iframe.util.RSAUtil;
import com.iframe.util.Utils;
import com.iframe.view.ClearEditText;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by majie on 16/1/14.
 */
public class RegisterActivity extends BaseActivity {
    private ClearEditText nameInput, phoneInput, verifyInput, pwdInput, recommendInput,pwdConfirmInput;
    private Button registBtn, verifyBtn;

    private IdwxzCountDownTimer countDownTimer;
    private boolean isFinishing=false;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getRSAKey();

        nameInput = (ClearEditText) findViewById(R.id.regist_name_input);
        phoneInput = (ClearEditText) findViewById(R.id.regist_phone_input);
        verifyInput = (ClearEditText) findViewById(R.id.regist_verify_input);
        pwdInput = (ClearEditText) findViewById(R.id.regist_pwd_input);
        pwdConfirmInput= (ClearEditText) findViewById(R.id.regist_confirm_pwd_input);
        recommendInput = (ClearEditText) findViewById(R.id.regist_recommend_input);
        title = (TextView) findViewById(R.id.title);
        title.setText(this.getString(R.string.regist_title));
        registBtn = (Button) findViewById(R.id.regist_btn);

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });
        verifyBtn = (Button) findViewById(R.id.regist_verify_btn);
        countDownTimer=new IdwxzCountDownTimer(50*1000,1000);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verify();
            }
        });
    }

    private void regist() {
        String user = nameInput.getText().toString().trim();
        String phoneNo = phoneInput.getText().toString().trim();
        String verifyCode = verifyInput.getText().toString().trim();
        String pass = pwdInput.getText().toString().trim();
        String passConfirm=pwdConfirmInput.getText().toString().trim();
        if(TextUtils.isEmpty(user)||TextUtils.isEmpty(phoneNo)||TextUtils.isEmpty(verifyCode)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(passConfirm)){
            showShortToast("输入不能为空");
            return;
        }
        if(!TextUtils.equals(pass,passConfirm)){
            showShortToast("两次输入的密码不一致！");
            pwdConfirmInput.setText("");
            pwdInput.setText("");
            return;
        }
        if (key == null) {
            showNetErrorToast();
            getRSAKey();
            return;
        }
        String enPass = RSAUtil.generatePwd(key, pass);
        String recommendNo = recommendInput.getText().toString().trim();
        Net.getLoginRegisterService().register(user, phoneNo, verifyCode, enPass, key.getPwkey(), recommendNo, "android").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    showShortToast("注册成功，请登录");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //onSaveInstanceState
                            finish();
                        }
                    }, 1000);
                } else {
                    showShortToast(Net.getError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                showShortToast("注册失败");
            }
        });
    }


    private void verify() {
        String phoneNo = phoneInput.getText().toString().trim();
        if (!TextUtils.isEmpty(phoneNo) && Utils.isMobileNO(phoneNo)) {
            verifyBtn.setEnabled(false);
            verifyBtn.setTextColor(getResources().getColor(R.color.black));
            countDownTimer.start();
            Net.getLoginRegisterService().registerCode(phoneNo).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    if(throwable.getMessage().contains("No content to map due to end-of-input")){
                        Log.d(TAG,"get registerCode success");
                    }else{
                        showShortToast("获取验证码失败，请重试");
                        countDownTimer.cancel();
                        verifyBtn.setEnabled(true);
                        verifyBtn.setText("获取验证码");
                        verifyBtn.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            });
        } else {
            showShortToast("请输入手机号码");
        }
    }

    private class IdwxzCountDownTimer extends CountDownTimer{

        public IdwxzCountDownTimer(long millisInFuture, long countDownInterval){
            super(millisInFuture,countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            verifyBtn.setText(millisUntilFinished/1000+"秒后重新获得验证码");
        }

        @Override
        public void onFinish() {

            verifyBtn.setText("获取验证码");
            verifyBtn.setTextColor(getResources().getColor(R.color.white));
            verifyBtn.setEnabled(true);
        }
    }

}
