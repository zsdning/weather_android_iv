package com.iframe.ui.main;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iframe.FrameApp;
import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.realm.dao.WeatherDao;
import com.iframe.realm.dao.WeatherDaoImpl;
import com.iframe.ui.account.HelpActivity;
import com.iframe.ui.account.MyAccountFragment;
import com.iframe.ui.city.AddCityActivity;
import com.iframe.ui.home.HomeFragment;
import com.iframe.ui.product.ProductListFragment;
import com.iframe.ui.settings.MoreActivity;
import com.iframe.view.CustomViewPager;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zsdning on 2016/5/29.
 */
public class MainActivity extends BaseActivity {

    public static final int INDEX_MAIN = 0x0;
    public static final int INDEX_PRODUCT = 0x1;
    public static final int INDEX_ACCOUNT = 0x2;
    public final static String JUMP_MAIN_ACTION = "com.idwzx.action.jumpmain";
    public static WeatherDao weatherDao;

    public RadioGroup rg_tab;
    private TextView title;
    public  MyAccountFragment myAccountFragment;
    private ProductListFragment productListFragment;
    private HomeFragment mainFragment;
    private CustomViewPager mViewPager;
    private MainPagerAdapter viewAdapter;
    private ImageView more, back, findCity, help, set;
    private ChooseTabReceiver chooseReceiver;
    private boolean isExit;
    private String district;
    private String city;
    private String cityAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(R.layout.activity_main);
        weatherDao = new WeatherDaoImpl(this);        //缓存
        findView();
        initFragment();
        register();
    }

    @Override
    protected void onDestroy() {
        viewAdapter = null;
        try {
            weatherDao.finishRealm(); //清理缓存
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     *沉浸标题栏
     */
    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void findView() {

        rg_tab = (RadioGroup) findViewById(R.id.rg_main);

        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setInstanceByIndex(checkedId);
            }
        });

        mViewPager = (CustomViewPager) findViewById(R.id.layout_content);

        title = (TextView) findViewById(R.id.title);
        title.setText(R.string.tab_main);

        findCity = (ImageView) findViewById(R.id.btn_findcity);
        findCity.setVisibility(View.VISIBLE);   //一开始加载时就可见
        findCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AddCityActivity.startAc(activity, city, district);
                Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                intent.putExtra("city", cityAt);
                intent.putExtra("district",district);
                startActivityForResult(intent, 1);  //带返回值的处理方式  不能是activity.startActivityForResult
            }
        });

        more = (ImageView) findViewById(R.id.btn_more);
        more.setVisibility(View.VISIBLE);//一开始加载时就可见
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //暂时采用SettingActivity
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                startActivity(intent);
                //设置切换动画，必需紧挨着startActivity()或者finish()函数之后调用
                overridePendingTransition(R.anim.activity_open_out_anim, R.anim.activity_close_in_anim);
            }
        });

        back = (ImageView) findViewById(R.id.btn_back);
        back.setVisibility(View.GONE);

        help = (ImageView) findViewById(R.id.iv_help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
            }
        });

    }

    private void initFragment() {
        FrameApp frameApp = (FrameApp) this.getApplication();
        if (frameApp.getMainFragment() != null) {
            mainFragment = frameApp.getMainFragment();
        } else {
            mainFragment = new HomeFragment();
        }
        if (frameApp.getProductListFragment() != null) {
            productListFragment = frameApp.getProductListFragment();
        } else {
            productListFragment = new ProductListFragment();
        }
        if (frameApp.getMyAccountFragment() != null) {
            myAccountFragment = frameApp.getMyAccountFragment();
        } else {
            myAccountFragment = new MyAccountFragment();
        }

        if (frameApp.getFragmentMap() == null) {
            frameApp.setFragmentMap(new HashMap<String, Fragment>());
        }
        frameApp.getFragmentMap().put(MainPagerAdapter.MAIN, mainFragment);
        frameApp.getFragmentMap().put(MainPagerAdapter.PRODUCT, productListFragment);
        frameApp.getFragmentMap().put(MainPagerAdapter.MYACCOUNT, myAccountFragment);
//        dwzxApp.getFragmentMap().put(MainPagerAdapter.SETTING, settingFragment);
        setAdapter();
    }

    public void setAdapter() {
        viewAdapter = new MainPagerAdapter(this);
        mViewPager.setAdapter(viewAdapter);
        mViewPager.setOffscreenPageLimit(3);
        // 滑动viewpager执行mUnderline的移动动画
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPosition = -1;
            private int nextPosition = -1;

            //页面跳转结束
            @Override
            public void onPageSelected(int position) {
                nextPosition = position;
                mViewPager.setCurrentItem(position);
                setInstanceByIndex(position);

            }

            //页面滑动
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
                /*Intent intent = new Intent();
                intent.setAction(HomeFragment.REFRESHACTION);
                sendBroadcast(intent);*/
            }

            //状态改变
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    public void setInstanceByIndex(int index) {
        switch (index) {
            case INDEX_MAIN:
            case R.id.rb_tab_main:
                mViewPager.setCurrentItem(0);
                rg_tab.check(R.id.rb_tab_main);
                findCity.setVisibility(View.VISIBLE);
                title.setText(R.string.tab_main);
                help.setVisibility(View.GONE);
                break;
            case INDEX_PRODUCT:
            case R.id.rb_tab_product:
                mViewPager.setCurrentItem(1);
                rg_tab.check(R.id.rb_tab_product);
                findCity.setVisibility(View.GONE);
                title.setText(R.string.tab_product);
                help.setVisibility(View.GONE);
                break;
            case INDEX_ACCOUNT:
            case R.id.rb_tab_my:
                rg_tab.check(R.id.rb_tab_my);
                mViewPager.setCurrentItem(2);
                findCity.setVisibility(View.GONE);
                title.setText(R.string.tab_my);
                help.setVisibility(View.VISIBLE);
                break;
            default:
                mViewPager.setCurrentItem(0);
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                if (rg_tab.getCheckedRadioButtonId() == R.id.rb_tab_main) {
                    isExit = true;
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                    Timer tExit = new Timer();
                    tExit.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            isExit = false; // 取消退出
                        }
                    }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
                    return true;
                } else {
                    setInstanceByIndex(R.id.rb_tab_main);
                    return true;
                }
            } else {
                System.exit(0);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    //处理来自AddCityActivity的返回值
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            //city = data.getStringExtra("city");

            //getInformation(city);
        }
    }

    /**
     * 注册广播
     */
    public void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(JUMP_MAIN_ACTION);
        chooseReceiver = new ChooseTabReceiver();
        registerReceiver(chooseReceiver, intentFilter);
    }

    private class ChooseTabReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(JUMP_MAIN_ACTION)){
                mViewPager.setCurrentItem(0);
            }
        }

    }

}
