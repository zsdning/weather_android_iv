package com.iframe.ui.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by majie on 16/1/15.
 */
public class OutdoorListAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;

    public OutdoorListAdapter(FragmentManager fm) {
        super(fm);

        list = new ArrayList<Fragment>();
        list.add(new BasketBallFragment());
        list.add(new FootBallFragment());

    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "篮球";
        } else {
            return "足球";
        }
    }
}
