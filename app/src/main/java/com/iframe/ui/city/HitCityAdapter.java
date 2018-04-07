package com.iframe.ui.city;

/**
 * Created by zsdning on 2016/7/19.
 * 最近访问城市GridView
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iframe.R;

import java.util.List;


public class HitCityAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> hotCitys;

    public HitCityAdapter(Context context, List<String> hotCitys) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.hotCitys = hotCitys;
    }

    @Override
    public int getCount() {
        return hotCitys.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_city, null);
        TextView city = (TextView) convertView.findViewById(R.id.city);
        city.setText(hotCitys.get(position));
        return convertView;
    }
}