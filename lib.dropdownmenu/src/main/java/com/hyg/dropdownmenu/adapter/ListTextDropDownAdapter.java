package com.hyg.dropdownmenu.adapter;

import android.content.Context;
import android.widget.TextView;

import com.hyg.dropdownmenu.R;

import java.util.List;

/**
 * Created by HuangYuGuang on 2016/4/14.
 */
public class ListTextDropDownAdapter extends DropDownAdapter {

    public ListTextDropDownAdapter(Context context, List<String> list) {
        super(context,list);
    }

    @Override
    protected int inflateItemView() {
        return R.layout.item_list_text_drop_down;
    }

    @Override
    protected void actionNotSelect(TextView textView) {
        textView.setBackgroundResource(R.color.drop_white);
    }

    @Override
    protected void actionSelect(TextView textView) {
        textView.setBackgroundResource(R.color.check_bg);
    }
}