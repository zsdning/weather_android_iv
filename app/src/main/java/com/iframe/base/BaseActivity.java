package com.iframe.base;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.iframe.FrameApp;
import com.iframe.R;
import com.iframe.net.Net;
import com.iframe.net.model.FrameRSAKey;
import com.iframe.net.model.UserInfo;
import com.iframe.ui.login.LoginActivity;
import com.iframe.util.ActivityCollector;
import com.iframe.util.OnClickUtil;
import com.iframe.util.SPHelper;
import com.iframe.view.wheelview.ArrayWheelAdapter;
import com.iframe.view.wheelview.WheelView;

import java.lang.reflect.Field;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BaseActivity extends AppCompatActivity {

    // yiren测试地址
    public static String BASEURL = "http://120.26.64.232:8081";
    public static String BASEURLS = "http://120.26.64.232:8081";
    public static String URL_BASE = "http://120.26.64.232:8081/api/v3";
    public static String URL_BASE_V2 = "http://120.26.64.232:8081/api/v2";
    public static String CLIENT_ID = "client-id-for-mobile-dev";
    public static String CLIENT_SECRET = "client-secret-for-mobile-dev";

    /**
     * rsa加密信息，用于所有需要传输密码的地方
     * 如果key为空，报错并重试
     */
    public FrameRSAKey key;

    private boolean isActive = true;

    protected String TAG = "FrameApp: " + this.getClass().getSimpleName();

    public Dialog progressDialog;
    public Dialog inputPwdDialog;
    public Dialog call_dialog;
    public AlertDialog logOutDialog;
    private Dialog chooseSingeDialog;

    protected Context mContext;
    public FrameApp frameApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName() + " onCreate()");

        this.mContext = BaseActivity.this;
        ActivityCollector.addActivity(this);
        frameApp = (FrameApp) this.getApplication();

       /* httpService = HttpService.newInstance(getApplicationContext());
        volleyHttpClient = VolleyHttpClient.newInstance(httpService, URL_BASE, CLIENT_ID, CLIENT_SECRET, this);*/
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, getClass().getSimpleName() + " onDestory()");
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onPause() {
        StatService.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
        Log.d(TAG, getClass().getSimpleName() + " onResume()");
    }

    public String getTAG() {
        return TAG;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, getClass().getSimpleName() + " onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            isActive = false;
        }
        Log.d(TAG, getClass().getSimpleName() + " onStop()");
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // overridePendingTransition(R.anim.slide_in_left,
        // R.anim.slide_out_left);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        // overridePendingTransition(R.anim.slide_in_left,
        // R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition(R.anim.slide_in_right,
        // R.anim.slide_out_right);
    }

    public void showLoadingDialog(String strMsg) {
        /*try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            progressDialog = null;
        }

        progressDialog = new Dialog(this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        if (strMsg.isEmpty())
            msg.setText("正在加载");
        else
            msg.setText(strMsg);

        try {
            if (!this.isFinishing()) {
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    public void cancelLoadingDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            progressDialog = null;
        }
    }

    Button btn_call;



    public void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showNetErrorToast() {
        Toast.makeText(this, R.string.base_net_error, Toast.LENGTH_SHORT).show();
    }

    public void showSystemShortToast(String msg) {
        if (!OnClickUtil.isFastDoubleClick(2000)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void getRSAKey() {
        Net.getLoginRegisterService().getPwdKey().enqueue(new Callback<FrameRSAKey>() {
            @Override
            public void onResponse(Response<FrameRSAKey> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    key = response.body();
                }
                Log.i(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: key");
            }
        });
    }

    /**
     * 左上角返回
     *
     * @param view
     */
    public void back(View view) {
        onBackPressed();
    }

    public interface OnPwdCallback {
        void onResult(String pwd);
    }

    /**
     * 程序是否在前台运行
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    protected void delayFinish(long delayTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    finish();
                }
            }
        }, delayTime);
    }

    public void showPwdDialog(final OnPwdCallback onPwdCallback) {
        this.showPwdDialog(onPwdCallback, "请输入交易密码");
    }

    public void showPwdDialog(final OnPwdCallback onPwdCallback, String title) {
        /*View view = LayoutInflater.from(this).inflate(R.layout.input_dialog_edit, null);
        final TextInputLayout input = (TextInputLayout) view.findViewById(R.id.input_dialog_text);
        inputPwdDialog = new AlertDialog.Builder(this).setTitle(title).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CheckPwd(input.getEditText().getText().toString().trim(), dialog, onPwdCallback);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();

        inputPwdDialog.setCancelable(false);
        inputPwdDialog.setCanceledOnTouchOutside(false);
        inputPwdDialog.show();*/
    }


    public void showCallDialog(final String phone_num) {
        if (call_dialog == null) {
            View view = getLayoutInflater().inflate(R.layout.call_dialog, null);
            call_dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
            call_dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Window window = call_dialog.getWindow();
            // 设置显示动画
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = getWindowManager().getDefaultDisplay().getHeight();
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            // 设置显示位置
            call_dialog.onWindowAttributesChanged(wl);
            // 设置点击外围解散
            call_dialog.setCanceledOnTouchOutside(true);
            call_dialog.show();
            btn_call = (Button) view.findViewById(R.id.btn_call);
            // btn_call.setText(phone_num);
            Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (phone_num != null && !phone_num.equals("")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_num));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        call_dialog.dismiss();
                    }
                }
            });
            btn_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call_dialog.dismiss();
                }
            });
        } else {
            btn_call.setText(phone_num);
            call_dialog.show();
        }
    }

    public void showLogOutDialog(final Context context) {
        if (logOutDialog == null) {
            logOutDialog = new AlertDialog.Builder(context).setTitle(R.string.app_name).setMessage("您确定要退出登录账户吗").setPositiveButton("退 出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    logout(context);
                    dialog.dismiss();
                }
            }).setNegativeButton("返 回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
        }
        try {
            if (!isFinishing()) {
                logOutDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void logout(Context context) {
        SPHelper.getInstance().setLoginInfo(new UserInfo());
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        ActivityCollector.finishAllExceptLogin();
    }


    protected void showChooseDateDialog(final String[] data, final String title) {
        View view = getLayoutInflater().inflate(R.layout.wheelview_dialog, null);
        chooseSingeDialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        chooseSingeDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = chooseSingeDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        chooseSingeDialog.onWindowAttributesChanged(wl);
        TextView tv = (TextView) view.findViewById(R.id.wheelview_dialog_tv);
        tv.setText(title);
        final WheelView byWhat = (WheelView) view.findViewById(R.id.empty);
        byWhat.setAdapter(new ArrayWheelAdapter<String>(data));
        byWhat.setVisibleItems(5);
        Button btn = (Button) view.findViewById(R.id.wheelview_dialog_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getChooseDialog(byWhat.getCurrentItem(), data[byWhat.getCurrentItem()], title);
                chooseSingeDialog.dismiss();
            }
        });
        // 设置点击外围解散
        chooseSingeDialog.setCanceledOnTouchOutside(true);
        chooseSingeDialog.show();
    }

    protected void getChooseDialog(int index, String message, String title) {

    }

    private void CheckPwd(final String pwd, final DialogInterface dialog, final OnPwdCallback onPwdCallback) {
        //FIXME
        if (onPwdCallback != null) {
            onPwdCallback.onResult(pwd);
        }
    }


    /**
     * 获取版本号
     *
     * @return
     */
    public int getAppVersion() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static void setURL(String serverIP) {
        /*switch (serverIP) {
            case "120":
                BASEURL = "http://120.26.64.232:8081";
                BASEURLS = "http://120.26.64.232:8081";
                URL_BASE = "http://120.26.64.232:8081/api/v3";
                URL_BASE_V2 = "http://120.26.64.232:8081/api/v2";
                CLIENT_ID = "client-id-for-mobile-dev";
                CLIENT_SECRET = "client-secret-for-mobile-dev";
                API_HUIFU_URL = "http://mertest.chinapnr.com/muser/publicRequests";
                break;
            case "101":
                BASEURL = "http://101.200.185.31:8081";
                BASEURLS = "http://101.200.185.31:8081";
                URL_BASE = "http://101.200.185.31:8081/api/v3";
                URL_BASE_V2 = "http://101.200.185.31:8081/api/v2";
                CLIENT_ID = "client-id-for-mobile-dev";
                CLIENT_SECRET = "client-secret-for-mobile-dev";
                API_HUIFU_URL = "http://mertest.chinapnr.com/muser/publicRequests";
                break;
            case "uat":
                BASEURL = "http://uat.idwzx.com";
                BASEURLS = "https://uat.idwzx.com";
                URL_BASE = "http://uat.idwzx.com/api/v3";
                CLIENT_ID = "8974bc51-4fd3-40b9-ad52-fc946d7ce788";
                URL_BASE_V2 = "http://uat.idwzx.com/api/v2";
                CLIENT_SECRET = "570a1c8fe6fcbcad949ea81bc2b76b1771b5783805a0d682a58bc8af094b351e";
                API_HUIFU_URL = "http://mertest.chinapnr.com/muser/publicRequests";
                break;
            case "real":
                BASEURL = "http://real.idwzx.com";
                BASEURLS = "https://real.idwzx.com";
                URL_BASE_V2 = "http://real.idwzx.com/api/v2";
                URL_BASE = "http://real.idwzx.com/api/v3";
                CLIENT_ID = "8974bc51-4fd3-40b9-ad52-fc946d7ce788";
                CLIENT_SECRET = "570a1c8fe6fcbcad949ea81bc2b76b1771b5783805a0d682a58bc8af094b351e";
                API_HUIFU_URL = "https://lab.chinapnr.com/muser/publicRequests";
                break;
            case "product":
                BASEURL = "http://www.idwzx.com";
                BASEURLS = "https://www.idwzx.com";
                URL_BASE_V2 = "http://www.idwzx.com/api/v2";
                URL_BASE = "http://www.idwzx.com/api/v3";
                CLIENT_ID = "8974bc51-4fd3-40b9-ad52-fc946d7ce788";
                CLIENT_SECRET = "570a1c8fe6fcbcad949ea81bc2b76b1771b5783805a0d682a58bc8af094b351e";
                API_HUIFU_URL = "https://lab.chinapnr.com/muser/publicRequests";
                break;


        }*/

    }

}
