package com.iframe.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.iframe.FrameApp;
import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.ui.account.MyAccountFragment;
import com.iframe.ui.home.HomeFragment;
import com.iframe.ui.main.MainActivity;
import com.iframe.ui.product.ProductListFragment;

public class SplashActivity extends BaseActivity {

    private boolean first; // 判断是否第一次打开软件
    private View view;
    private Animation animation;
    private boolean bankNameOver = false, cityOver = false, time = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        into();

        initThirdParty();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFragments();
        getAllInfomation();
    }

    private void initThirdParty() {
        // bugly
        //CrashReport.initCrashReport(getApplicationContext(), "900007119", false);
        // tongji
        initStat();
    }

    private void initStat() {
        // mtj.baidu.com user/pass dwzxapp/dwzx2015

        // 设置exception
        StatService.setOn(this, StatService.EXCEPTION_LOG);
        // 设置发送延迟
        StatService.setLogSenderDelayed(1);
        // 设置发送策略
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        // 设置debug模式
        StatService.setDebugOn(true);
    }

    private void initFragments() {
        FrameApp phone = (FrameApp) this.getApplication();
        if (phone.getMainFragment() == null) {
            phone.setMainFragment(new HomeFragment());
        }
        if (phone.getProductListFragment() == null) {
            phone.setProductListFragment(new ProductListFragment());
        }
        if (phone.getMyAccountFragment() == null) {
            phone.setMyAccountFragment(new MyAccountFragment());
        }
    }

    private void getAllInfomation() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    time = true;
                    startAc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    time = true;
                    startAc();
                }
            }
        }).start();
    }

    private void startAc() {
        if (time) {
            //需要手势密码
            /*if (SPHelper.getInstance().isLogin() && isSameServer() && !SPHelper.getInstance().isFirst()) {
                String userid = SPHelper.getInstance().getUserId();
                if (TextUtils.isEmpty(SPHelper.getInstance().getGesture(userid))) {
                    startActivity(new Intent(this, RegisitGestureActivity.class));
                } else {
                    Intent intent = new Intent(this, GestureBackActivity.class);
                    intent.putExtra("isFirst", true);
                    startActivity(intent);
                }
                finish();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }*/

            //不需要手势密码
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    /**
     * 判断本次app登录的server是否与上次相同，如果不同，则应当直接退出到登录页面
     *
     * @return
     */
    private boolean isSameServer() {
        String server = BASEURL;
        String lastServer = getSharedPreferences("iframe", MODE_PRIVATE).getString("server", "");
        return TextUtils.equals(server, lastServer);
    }

    // 进入主程序的方法
    public void into() {
        // 设置动画效果是alpha，在anim目录下的alpha.xml文件中定义动画效果
        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        // 给view设置动画效果
        view.startAnimation(animation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
