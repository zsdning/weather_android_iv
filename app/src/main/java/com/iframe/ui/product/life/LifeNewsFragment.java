package com.iframe.ui.product.life;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hyg.dropdownmenu.DropDownMenu;
import com.hyg.dropdownmenu.DropDownMenuUtils;
import com.iframe.R;
import com.iframe.base.BaseFragment;
import com.iframe.ui.main.MainActivity;
import com.iframe.ui.product.CircleIndicator;
import com.iframe.ui.product.life.model.DropPosition;
import com.iframe.ui.product.life.model.HeaderViewBean;
import com.iframe.view.TransparentToolBar;
import com.iframe.view.scrollableview.ScrollAbleFragment;
import com.iframe.view.scrollableview.ScrollableLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zsdning on 16/5/29.  产品列表页
 */
public class LifeNewsFragment extends BaseFragment implements TransparentToolBar.OnScrollStateListener {

    public static final String REFRESHACTION = "HomeFragment.REFRESH_ACTION";
    private TransparentToolBar mTransparentToolBar;
    private ViewPager mViewPagerGrid;
    private CircleIndicator circleIndicator;
    private DropDownMenu mDropDownMenu1;
    private RecyclerView recyclerView;
    private DropAdapter adapter;
    private Handler handler;
    private View view;
    private ScrollableLayout mScrollLayout;

    private List<View> mViewPagerGridList = new ArrayList<View>();
    private List<HeaderViewBean> mDatas = new ArrayList<HeaderViewBean>();
    private ArrayList<DropPosition> pDatas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        view = inflater.inflate(R.layout.fragment_lifenews, container, false);
        findView(view);

        return view;
    }

    private void findView(View view) {
        mScrollLayout = (ScrollableLayout) view.findViewById(R.id.scrollableLayout);

        initPagerGrid(view);
        initDropMenu(view);
        initListFragment();
    }

    //添加仿美团Headerview
    private void initPagerGrid(View view) {
        mViewPagerGrid = (ViewPager) view.findViewById(R.id.vp);
        loadDatas();
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        //塞GridView至ViewPager中：
        int pageSize = view.getResources().getInteger(R.integer.HomePageHeaderColumn) * 2;
        //一共的页数等于 总数/每页数量，并取整。
        int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        //ViewPager viewpager = new ViewPager(this);
        for (int index = 0; index < pageCount; index++) {
            //每个页面都是inflate出一个新实例
            GridView grid = (GridView) inflater.inflate(R.layout.item_viewpager, mViewPagerGrid, false);
            grid.setAdapter(new GridViewAdapter(view.getContext(), mDatas, index));
            mViewPagerGridList.add(grid);
        }
        mViewPagerGrid.setAdapter(new ViewListPagerAdapter(mViewPagerGridList));

        circleIndicator = (CircleIndicator) view.findViewById(R.id.find_viewflowindic);
        circleIndicator.setViewPager(mViewPagerGrid);
    }

    private void loadDatas() {
        mDatas.add(new HeaderViewBean("美食", R.drawable.ic_category_0));
        mDatas.add(new HeaderViewBean("电影", R.drawable.ic_category_1));
        mDatas.add(new HeaderViewBean("酒店", R.drawable.ic_category_2));
        mDatas.add(new HeaderViewBean("KTV", R.drawable.ic_category_3));
        mDatas.add(new HeaderViewBean("外卖", R.drawable.ic_category_4));
        mDatas.add(new HeaderViewBean("美女6", R.drawable.ic_category_5));
        mDatas.add(new HeaderViewBean("美女7", R.drawable.ic_category_6));
        mDatas.add(new HeaderViewBean("美女8", R.drawable.ic_category_7));
        mDatas.add(new HeaderViewBean("帅哥", R.drawable.ic_category_8));
        mDatas.add(new HeaderViewBean("帅哥2", R.drawable.ic_category_9));
        mDatas.add(new HeaderViewBean("帅哥3", R.drawable.ic_category_10));
        mDatas.add(new HeaderViewBean("帅哥4", R.drawable.ic_category_11));
        mDatas.add(new HeaderViewBean("帅哥5", R.drawable.ic_category_12));
        mDatas.add(new HeaderViewBean("帅哥6", R.drawable.ic_category_13));
        mDatas.add(new HeaderViewBean("帅哥7", R.drawable.ic_category_14));
        mDatas.add(new HeaderViewBean("帅哥8", R.drawable.ic_category_15));
        mDatas.add(new HeaderViewBean("帅哥9", R.drawable.ic_category_16));
        mDatas.add(new HeaderViewBean("美女6", R.drawable.ic_category_5));
        mDatas.add(new HeaderViewBean("美女7", R.drawable.ic_category_6));
        mDatas.add(new HeaderViewBean("美女8", R.drawable.ic_category_7));
    }


    private void initDropMenu(View view) {
        final List<String> list1 = Arrays.asList("全部", "衣服", "鞋子");
        final List<String> list2 = Arrays.asList("默认排序", "升序", "降序");
        final List<String> list3 = Arrays.asList("好评", "差评");
        final List<String> list4 = Arrays.asList("全部", "充值", "提现", "金钱袋买入", "金钱袋卖出", "回购金额", "入库", "兑换", "定投转入", "回购工费", "定期赎回");

        mDropDownMenu1 = (DropDownMenu) view.findViewById(R.id.dropDownMenu1);
        DropDownMenuUtils.addTextList(view.getContext(), mDropDownMenu1, new DropDownMenuUtils.OnMenuClickListener() {
            @Override
            public void onMenuClick(int viewPosition, int itemPosition) {
                //textView.setText(viewPosition+"---"+itemPosition);

            }
        }, list1, list2, list3);

        //加入网格布局
            /*DropDownMenuUtils.addGridList(mContext, mDropDownMenu1, new DropDownMenuUtils.OnMenuClickListener() {
                @Override
                public void onMenuClick(int viewPosition, int itemPosition) {
                    textView.setText(list4.get(itemPosition));
                }
            }, list4);*/

    }

    private void initListFragment(){
        ScrollAbleFragment fragment = new ListFragment();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.drop_list, fragment);
        fragmentTransaction.commit();
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragment);
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
//            scrollView.scrollTo(location[1],location[1]-location[0]);// 改变滚动条的位置
        }
    };

    public void goTop() {
        handler = new Handler();
        handler.postDelayed(runnable, 200);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
    }


    @Override
    public void updateFraction(float fraction) {

    }


}
