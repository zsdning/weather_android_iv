package com.iframe.ui.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.net.Net;
import com.iframe.net.model.ImageCode;
import com.iframe.util.SPHelper;
import com.iframe.util.Utils;
import com.iframe.view.ClearEditText;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by zsdning on 16/5/14.
 */
public class ForgetPasswordActivity extends BaseActivity {
    private ClearEditText phone, real, id, img;
    private ImageView image;
    private Button fpBtn;

    private String imgKey;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);

        phone = (ClearEditText) findViewById(R.id.fgpwd_phone_input);
        real = (ClearEditText) findViewById(R.id.fgpwd_real_input);
        id = (ClearEditText) findViewById(R.id.fgpwd_id_input);
        img = (ClearEditText) findViewById(R.id.fgpwd_img_input);

        title = (TextView) findViewById(R.id.title);
        title.setText(this.getString(R.string.forgetpwd));

        image = (ImageView) findViewById(R.id.fgpwd_img);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageCode();
            }
        });

        fpBtn = (Button) findViewById(R.id.fgpwd_btn);
        fpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPwd();
            }
        });

        getImageCode();

    }

    private void getImageCode() {
        Net.getLoginRegisterService().getImageCode().enqueue(new Callback<ImageCode>() {
            @Override
            public void onResponse(Response<ImageCode> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    String imageStr = response.body().getImageCode();
                    imgKey = response.body().getImageKey();
                    String split = "base64,";
                    int index = imageStr.indexOf(split);
                    final Bitmap bitmap = Utils.getBitmapFromBase64(imageStr.substring(index + split.length()));
                    if (bitmap == null) {

                    } else {
                        ForgetPasswordActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                image.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
                Log.i(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.i(TAG, "onFailure: img");
            }
        });
    }

    private void forgetPwd() {
        String phoneNo = phone.getText().toString().trim();
        String realName = real.getText().toString().trim();
        String idStr = id.getText().toString().trim();
        String imgStr = img.getText().toString().trim();
        phoneNo = SPHelper.getInstance().getPhoneNo();
        realName = SPHelper.getInstance().getRealName();
        if (TextUtils.isEmpty(phoneNo) || TextUtils.isEmpty(realName) || TextUtils.isEmpty(idStr) || TextUtils.isEmpty(imgStr)) {
            showShortToast("输入不能为空");
            return;
        }

        Net.getAccountService().forgotPwd(phoneNo, realName, idStr, imgStr, imgKey).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    showShortToast("新密码已经发送到您的手机上");
                    delayFinish(1500);
                } else {
                    String errorText = Net.getError(response.errorBody());
                    showShortToast(errorText);
                    Log.d(TAG, errorText);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (Net.handleError(throwable)) {
                    showShortToast("新密码已经发送到您的手机上");
                    delayFinish(1500);
                } else {
                    showShortToast("网络错误，请稍后再试");
                }

            }
        });
    }
}
