package com.hyg.dropdownmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.dropdownmenu.R;

import java.util.List;

/**
 * Created by HuangYuGuang on 2016/4/14.
 */
public class GridDropDownAdapter extends DropDownAdapter {

    public GridDropDownAdapter(Context context, List<String> list) {
        super(context,list);
    }

    @Override
    protected int inflateItemView() {
        return R.layout.item_grid_drop_down;
    }

    @Override
    protected void actionSelect(TextView textView) {
        textView.setBackgroundResource(R.drawable.shape_drop_down_check_bg);
    }

    @Override
    protected void actionNotSelect(TextView textView) {
        textView.setBackgroundResource(R.drawable.shape_drop_down_uncheck_bg);
    }
}