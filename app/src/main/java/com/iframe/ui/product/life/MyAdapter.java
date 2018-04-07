package com.iframe.ui.product.life;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iframe.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cpoopc on 2015-02-10.
 */
public class MyAdapter extends BaseAdapter {

    private List<String> strList;
    private Animation slide_top_to_bottom, slide_bottom_to_top;
    private Map<Integer, Boolean> isFrist;
    private Context mContext;

    public MyAdapter(Context mContext, List<String> strList) {
        this.strList = strList;
        this.mContext = mContext;

        slide_top_to_bottom = AnimationUtils.loadAnimation(mContext, R.anim.slide_top_to_bottom);
        slide_bottom_to_top = AnimationUtils.loadAnimation(mContext, R.anim.slide_bottom_to_top);
        isFrist = new HashMap<Integer, Boolean>();
    }

    @Override
    public int getCount() {
        return strList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.drop_list_item, null, false);
        convertView.setBackgroundColor(mContext.getResources().getColor(ColorsConstant.colors[position % ColorsConstant.colors.length]));
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        tv.setText(strList.get(position));
        //convertView.setAnimation(slide_bottom_to_top);
        // 如果是第一次加载该view，则使用动画
        if (isFrist.get(position) == null || isFrist.get(position)) {
            convertView.startAnimation(slide_bottom_to_top);
            isFrist.put(position, false);
        }

        return convertView;
    }
}
