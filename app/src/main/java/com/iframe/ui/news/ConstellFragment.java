package com.iframe.ui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.iframe.R;
import com.iframe.base.BaseFragment;
import com.iframe.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class ConstellFragment extends BaseFragment {
    private MainActivity activity;
    private SegmentTabLayout tabLayout;
    private ViewPager viewPager;
    private String[] mTitles = {"新闻资讯","星座运势"};
    private List<Fragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_constell, container, false);
        findView(view);
        return view;
    }

    private void findView(View view){

    }
}
