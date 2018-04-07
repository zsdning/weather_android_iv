package com.iframe.ui.product;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.iframe.R;
import com.iframe.base.BaseFragment;
import com.iframe.ui.main.MainActivity;
import com.iframe.ui.product.life.LifeNewsFragment;
import com.iframe.ui.product.weather.WeatherNewsFragment;
import com.iframe.util.Utils;
import com.iframe.view.CustomViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsdning on 16/5/29.  生活列表页
 */
public class ProductListFragment extends BaseFragment {
    private MainActivity activity;
    private TabLayout tab;                      //定义TabLayout
    private CustomViewPager pager;                    //定义viewPager
    private ProductListAdapter adapter;       //定义adapter

    private List<Fragment> list_fragment;       //定义要装fragment的列表
    private List<String> list_title;            //tab名称列表

    private WeatherNewsFragment weatherNewsFragment; //天气资讯fragment
    private LifeNewsFragment lifeNewsFragment; //生活服务fragment

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_productlist, container, false);
        findView(view);

        return view;
    }

    private void findView(View view) {
        tab = (TabLayout)view.findViewById(R.id.product_list_tab);
        pager = (CustomViewPager)view.findViewById(R.id.product_list_pager);

        //初始化各fragment
        weatherNewsFragment = new WeatherNewsFragment();
        lifeNewsFragment = new LifeNewsFragment();

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(weatherNewsFragment);
        list_fragment.add(lifeNewsFragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("天气资讯");
        list_title.add("生活服务");

        //为TabLayout添加tab名称
       /* tab.addTab(tab.newTab().setText(list_title.get(0)));
        tab.addTab(tab.newTab().setText(list_title.get(1)));*/

        adapter = new ProductListAdapter(activity.getSupportFragmentManager(),list_fragment,list_title);

        //viewpager加载adapter
        pager.setAdapter(adapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab.setupWithViewPager(pager);
        //设置TabLayout的模式
        tab.setTabMode(TabLayout.MODE_FIXED);
        if (Build.VERSION.SDK_INT >= 17){
            setTabIndicatorWidth();
        }
    }

    /**
     * 设置tablayout横线宽度
     */
    @TargetApi(17)
    private void setTabIndicatorWidth(){
        Class<?> tablayout = tab.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tablayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout ll_tab= null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tab);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0,0,0,0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);
            params.setMarginStart(Utils.dip2px(getContext(),20f));
            params.setMarginEnd(Utils.dip2px(getContext(),20f));
            child.setLayoutParams(params);
            child.invalidate();
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.i("resume","initData");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
