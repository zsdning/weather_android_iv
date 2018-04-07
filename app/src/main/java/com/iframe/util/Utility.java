package com.iframe.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.iframe.net.model.Product;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static void setListViewHeightBasedOnChildren(ListView listView, Activity activity) {
        BaseAdapter adapter = (BaseAdapter) listView.getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            return;
        }

        int totalHeight = 0;

        View listItem = adapter.getView(0, null, listView);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        listItem.measure(w, h);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        totalHeight = (int) (adapter.getCount() * 110 * dm.density);

        // listView.getChildAt(0).getHeight();
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * adapter.getCount());

        listView.setLayoutParams(params);
    }

    /**
     * 是否手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 计算贴息利率
     *
     * @param product
     * @return
     */
    public static double getRate(Product product) {
        double rate = ((double) product.getYearRate() * 100.00);
        return rate;
    }

    /**
     * 计算贴息利率min
     *
     * @param product
     * @return
     */
    public static double getMinRate(Product product) {
        double rate = ((double) product.getYearRate() * 100.00);
        return rate;
    }

    /**
     * 计算贴息利率max
     *
     * @param product
     * @return
     */
    public static double getMaxRate(Product product) {
        double rate = ((double) product.getYearRate() * 100.00);
        return rate;
    }


    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * 取出数字获取验证码
     * @param str
     * @return
     */
    public static String getDynamicPassword(String str) {
        Pattern  continuousNumberPattern = Pattern.compile("[0-9\\.]+");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while(m.find()){
            if(m.group().length() == 6) {
                System.out.print(m.group());
                dynamicPassword = m.group();
            }
        }

        return dynamicPassword;
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick(int mills) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < mills) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
