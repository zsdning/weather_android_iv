package com.iframe.ui.product.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.iframe.R;
import com.iframe.base.BaseFragment;
import com.iframe.ui.main.MainActivity;
import com.iframe.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsdning on 16/5/29.  产品列表页
 */
public class WeatherNewsFragment extends BaseFragment {

    public static final String REFRESHACTION = "HomeFragment.REFRESH_ACTION";
    private MainActivity activity;
    private SegmentTabLayout tabLayout;
    private CustomViewPager viewPager;
    private RelativeLayout relativeLayout;
    private String[] mTitles = {"三天", "一周", "半月"};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_weathernews, container, false);
        findView(view);

        return view;
    }

    private void findView(View view) {
        tabLayout = (SegmentTabLayout) view.findViewById(R.id.mtablayout);
        viewPager = (CustomViewPager) view.findViewById(R.id.mviewpager);
        fragments.add(new ThreeDayFragment());
        fragments.add(new SevenDayFragment());
        fragments.add(new FifteenDayFragment());
        tabLayout.setTabData(mTitles);//给Tablayout设置标题

        viewPager.setAdapter(new MyPagerAdpater(getFragmentManager()));
        //为tablayout设置选中监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        relativeLayout = (RelativeLayout) view.findViewById(R.id.layout_nearphoto);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NearPhotoActivity.startAc(activity);
            }
        });
    }

    private class MyPagerAdpater extends FragmentPagerAdapter {
        public MyPagerAdpater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
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
