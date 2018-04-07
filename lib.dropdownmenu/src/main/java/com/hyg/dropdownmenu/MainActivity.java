//package com.hyg.dropdownmenu;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.GridView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.hyg.dropdownmenu.adapter.GridDropDownAdapter;
//import com.hyg.dropdownmenu.adapter.ListIconDropDownAdapter;
//import com.hyg.dropdownmenu.adapter.ListTextDropDownAdapter;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    DropDownMenu mDropDownMenu;
//    private String headers[] = {"城市", "年龄", "性别", "星座"};
//    private List<View> popupViews = new ArrayList<>();
//
//    private ListIconDropDownAdapter cityAdapter;
//    private ListTextDropDownAdapter ageAdapter;
//    private ListTextDropDownAdapter sexAdapter;
//    private GridDropDownAdapter constellationAdapter;
//
//    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
//    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
//    private String sexs[] = {"不限", "男", "女"};
//    private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};
//
//    private int constellationPosition = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initView();
//    }
//
//    private void initView() {
//        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
//        //init city menu
//        final ListView cityView = new ListView(this);
//        cityAdapter = new ListIconDropDownAdapter(this, Arrays.asList(citys));
//        cityView.setDividerHeight(0);
//        cityView.setAdapter(cityAdapter);
//
//        //init age menu
//        final ListView ageView = new ListView(this);
//        ageView.setDividerHeight(0);
//        ageAdapter = new ListTextDropDownAdapter(this, Arrays.asList(ages));
//        ageView.setAdapter(ageAdapter);
//
//        //init sex menu
//        final ListView sexView = new ListView(this);
//        sexView.setDividerHeight(0);
//        sexAdapter = new ListTextDropDownAdapter(this, Arrays.asList(sexs));
//        sexView.setAdapter(sexAdapter);
//
//        //init constellation
//        final View constellationView = getLayoutInflater().inflate(R.layout.grid_drop_down_layout, null);
//        GridView constellation = (GridView) constellationView.findViewById(R.id.constellation);
//        constellationAdapter = new GridDropDownAdapter(this, Arrays.asList(constellations));
//        constellation.setAdapter(constellationAdapter);
//        TextView ok = (TextView) constellationView.findViewById(R.id.ok);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
//                mDropDownMenu.closeMenu();
//            }
//        });
//
//        //init popupViews
//        popupViews.add(cityView);
//        popupViews.add(ageView);
//        popupViews.add(sexView);
//        popupViews.add(constellationView);
//
//        //add item click event
//        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                cityAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
//                mDropDownMenu.closeMenu();
//            }
//        });
//
//        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ageAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
//                mDropDownMenu.closeMenu();
//            }
//        });
//
//        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                sexAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
//                mDropDownMenu.closeMenu();
//            }
//        });
//
//        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                constellationAdapter.setCheckItem(position);
//                constellationPosition = position;
//            }
//        });
//
//        //init dropdownview
//        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews);
//    }
//
//    @Override
//    public void onBackPressed() {
//        //退出activity前关闭菜单
//        if (mDropDownMenu.isShowing()) {
//            mDropDownMenu.closeMenu();
//        } else {
//            super.onBackPressed();
//        }
//    }
//}
