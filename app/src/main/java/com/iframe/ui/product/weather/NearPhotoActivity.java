package com.iframe.ui.product.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.creditcloud.event.response.CMSResponse;
import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.net.model.Scene;
import com.iframe.ui.product.CircleIndicator;
import com.iframe.util.RecycleViewDivider;
import com.iframe.view.TransparentToolBar;

import java.util.ArrayList;
import java.util.Arrays;

public class NearPhotoActivity extends BaseActivity implements ViewPager.OnPageChangeListener, TransparentToolBar.OnScrollStateListener{
    private final int SCORLL_WHAT = 2;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private BannerAdapter pagerAdapter;
    private ArrayList<String> bannerList = new ArrayList<>();
    private ArrayList<CMSResponse> cmsLinkList = new ArrayList<>();
    private MyHandler handler;
    private RecyclerView recyclerView;
    private WaterfallDiskAdapter waterfallDiskAdapter;
    private TransparentToolBar mTransparentToolBar;
    private int mHeadValue;
    private ArrayList<Scene> scenes = new ArrayList<Scene>();
    private Scene scene;

    private String[] imageUrls = {"http://pic3.nipic.com/20090617/2082016_085827065_2.jpg",
            "http://pic27.nipic.com/20130305/9713815_104242739130_2.jpg",
            "http://pic23.nipic.com/20120729/7090656_094919954000_2.jpg",
            "http://pic23.nipic.com/20120918/10031483_133215033311_2.jpg"};

    private String[] imageLinks = {"http://ww3.sinaimg.cn/large/006joxyhgw1f62wsx4xcqj30qo0qo4bk.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/21/14690883870.13509600.1068_ios.jpg",
            "http://ww3.sinaimg.cn/large/79a8141egw1f61988km5hj20qo0zkqew.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/21/14690576870.24908900.1549_android.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/21/14690886030.05880400.1670_ios.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/21/14690523340.02428700.1385_android.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/20/14690113190.00147600.1319_ios.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/23/14692275890.85680800.1020_android.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/20/14690086290.51928300.1983_android.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/20/14690083080.30584100.1446_ios.jpg",
            "http://ww3.sinaimg.cn/large/89a55640jw1f6054kqcazj20qo0zkan6.jpg",
            "http://ww3.sinaimg.cn/large/93ff9cb9gw1f6045p6tgoj20qo0zkgum.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/20/14689753450.80698300.1089_android_1468975335417.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/19/14689161230.00544700.1968_android.jpg",
            "http://ww3.sinaimg.cn/large/7ae49b99gw1f5y7a4c0lqj20ku0rsafi.jpg",
            "http://ww2.sinaimg.cn/large/d72ae67dgw1f5y5vymjdij21w02ioe81.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/18/14687982430.48238800.1359_android.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/17/14687204190.05885400.1191_android.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/17/14687070300.80792200.1243_ios.jpg",
            "http://cdn.moji002.com/images/simgs/2016/07/16/14686475240.01128700.1181_android.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearphoto);
        findView();
    }

    private void findView() {
        //添加头布局
        final View headerView = getLayoutInflater().inflate(R.layout.listhead, null);
        //添加banner
        viewPager = (ViewPager) headerView.findViewById(R.id.pl_viewflow);
        circleIndicator = (CircleIndicator) headerView.findViewById(R.id.pl_viewflowindic);
        getBanners();
        recyclerView = (RecyclerView) findViewById(R.id.scene_list);
        waterfallDiskAdapter = new WaterfallDiskAdapter(this);
        waterfallDiskAdapter.setHeaderView(headerView);
        //添加瀑布流
        recyclerView.setAdapter(waterfallDiskAdapter);
        //设置layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, true,
                5, 5, getResources().getColor(R.color.bluegreen)));

        //随着滚动控件滚动，不断改变颜色透明度的ToolBar
        mTransparentToolBar = (TransparentToolBar) findViewById(R.id.nearphoto_bar);
        //必须设置ToolBar颜色值，也就是0~1透明度变化的颜色值
        mTransparentToolBar.setBgColor(getResources().getColor(R.color.title_home));

        mTransparentToolBar.setOnScrollStateListener(this);
        headerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //滑动到headView  1/2高度时变为完全可见
                mHeadValue = headerView.getMeasuredHeight() / 2;
                //必须设置ToolBar最大偏移量，这会影响到0~1透明度变化的范围
                mTransparentToolBar.setOffset(mHeadValue);

                headerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.i(TAG, "mHeadValue:" + mHeadValue);
            }
        });

        //添加监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;      //向下滑动变为不透明  反之则totalDy -= dy
                //将滚动不断变化的top值传递给ToolBar用于计算透明度
                mTransparentToolBar.setChangeTop(totalDy);
            }
        });

        waterfallDiskAdapter.setmPhotoWall(recyclerView);
        //获取图片
        getScenes();

    }


    private void getBanners() {
        bannerList.addAll(Arrays.asList(imageUrls));
        initBanner();
    }


    private void initBanner() {
        pagerAdapter = new BannerAdapter(this, bannerList);
        pagerAdapter.setCMSLinkData(cmsLinkList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);
        pagerAdapter.notifyDataSetChanged();
        circleIndicator.setViewPager(viewPager);
        startScroll(bannerList.size());

    }

    private void startScroll(int size) {
        handler = new MyHandler(size);
        sendScrollMessage(6 * 1000);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCORLL_WHAT);
        handler.sendEmptyMessageDelayed(SCORLL_WHAT, delayTimeInMills);
    }


    //获取实景图片
    private void getScenes() {
        for (int i = 0; i < imageLinks.length; i++) {
            scene = new Scene();
            scene.setTitle("");
            scene.setLink(imageLinks[i]);
            scenes.add(scene);
        }

        waterfallDiskAdapter.setData(scenes);
        waterfallDiskAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        //Log.i("resume","initData");
    }

    @Override
    public void onPause() {
        super.onPause();
        waterfallDiskAdapter.fluchCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        waterfallDiskAdapter.cancelAllTasks();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void updateFraction(float fraction) {

    }

    public void scrollOnce(int size) {
        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % size, true);
    }

    class MyHandler extends Handler {
        private int size;

        public MyHandler(int size) {
            this.size = size;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCORLL_WHAT:
                    scrollOnce(size);
                    sendScrollMessage(6 * 1000);
                    break;
            }
        }
    }

    public static void startAc(Context context){
        Intent intent = new Intent(context, NearPhotoActivity.class);
        context.startActivity(intent);
    }
}
