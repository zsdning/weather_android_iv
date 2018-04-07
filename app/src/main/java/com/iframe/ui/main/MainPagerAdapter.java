package com.iframe.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.iframe.base.BaseActivity;
import com.iframe.ui.account.MyAccountFragment;
import com.iframe.ui.home.HomeFragment;
import com.iframe.ui.product.ProductListFragment;

import java.util.HashMap;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public static final String MAIN = "main";
    public static final String PRODUCT = "product";
    public static final String MYACCOUNT = "myaccount";

    private BaseActivity activity;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainPagerAdapter(BaseActivity activity) {
        super(activity.getSupportFragmentManager());
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int arg0) {
        System.out.println("1111############" + arg0);
        HashMap<String, Fragment> map = activity.frameApp.getFragmentMap();
        if (map == null) {
            activity.frameApp.setFragmentMap(new HashMap<String, Fragment>());
        }

        if (map.get(arg0) == null) {
            switch (arg0) {
                case 0:
                    map.put(MAIN, new HomeFragment());
                    break;
                case 1:
                    map.put(PRODUCT, new ProductListFragment());
                    break;
                case 2:
                    map.put(MYACCOUNT, new MyAccountFragment());
                    break;
            }
        }
        return map.get(getMapKey(arg0));
    }

    private String getMapKey(int arg0) {
        switch (arg0) {
            case 0:
                return MAIN;
            case 1:
                return PRODUCT;
            case 2:
                return MYACCOUNT;
            default:
                return "";
        }
    }

    @Override
    public int getCount() {
        if (activity.frameApp.getFragmentMap() == null) {
            return 0;
        } else {
            return activity.frameApp.getFragmentMap().size();
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

}
