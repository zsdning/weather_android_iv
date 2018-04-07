package com.iframe.ui.product.life;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.hyg.dropdownmenu.DropDownMenu;
import com.hyg.dropdownmenu.DropDownMenuUtils;
import com.iframe.R;
import com.iframe.ui.product.CircleIndicator;
import com.iframe.ui.product.life.model.DropPosition;
import com.iframe.ui.product.life.model.HeaderViewBean;
import com.iframe.ui.product.life.model.News;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**搭配多种item的RecyclerView
 * Created by zsdning on 2016/9/26.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_ONE = 1;
    public final static int TYPE_TWO = 2;
    public final static int TYPE_THREE = 3;
    public final static int TYPE_FOUR = 4;
    private Context mContext;
    private List<News> newses = new ArrayList<>();
    private List<HeaderViewBean> mDatas = new ArrayList<HeaderViewBean>();
    private ArrayList<DropPosition> pDatas = new ArrayList<DropPosition>();
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(List<News> newses,Context context) {
        super();
        this.mContext = context;
        this.newses = newses;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void OnItemClick(View view, int position);

        void OnItemLongClick(View view, int position);
    }

    public void add(News news) {
        this.newses.add(news);
        notifyDataSetChanged();
    }

    public ArrayList<DropPosition> getpDatas() {
        return pDatas;
    }

    public void setpDatas(ArrayList<DropPosition> pDatas) {
        this.pDatas = pDatas;
    }

    //每一项item的类型 position < 3 ? newses.get(position).getItemType() : 4;
    @Override
    public int getItemViewType(int position) {
        //list数组第0个开始位置所对应的type
        return newses.get(position).getItemType();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find_item1, parent, false);
                holder = new ViewHolderOne(view);
                break;
            case TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find_item2, parent, false);
                holder = new ViewHolderTwo(view);
                break;
            case TYPE_THREE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find_item3, parent, false);
                holder = new ViewHolderThree(view);
                break;
            case TYPE_FOUR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find_item4, parent, false);
                holder = new ViewHolderFour(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ONE:
                final ViewHolderOne holderOne = (ViewHolderOne) holder;
                onItemEventClick(holderOne);
                break;
            case TYPE_TWO:
                ViewHolderTwo holderTwo = (ViewHolderTwo) holder;
                onItemEventClick(holderTwo);
                break;
            case TYPE_THREE:
                ViewHolderThree holderThree = (ViewHolderThree) holder;
                onItemEventClick(holderThree);
                break;
            case TYPE_FOUR:
                ViewHolderFour holderFour = (ViewHolderFour) holder;
                onItemEventClick(holderFour);
                break;
        }
    }

    //recyclerview中item的个数 newses.size()+datas.size() 为前两个headview加上后面itemview
    @Override
    public int getItemCount() {
        return newses.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        private ViewPager mViewPagerGrid;
        private CircleIndicator circleIndicator;
        private List<View> mViewPagerGridList = new ArrayList<View>();
        public ViewHolderOne(View itemView) {
            super(itemView);
            //添加仿美团Headerview
            mViewPagerGrid = (ViewPager) itemView.findViewById(R.id.vp);
            loadDatas();
            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
            //塞GridView至ViewPager中：
            int pageSize = itemView.getResources().getInteger(R.integer.HomePageHeaderColumn) * 2;
            //一共的页数等于 总数/每页数量，并取整。
            int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
            //ViewPager viewpager = new ViewPager(this);
            for (int index = 0; index < pageCount; index++) {
                //每个页面都是inflate出一个新实例
                GridView grid = (GridView) inflater.inflate(R.layout.item_viewpager, mViewPagerGrid, false);
                grid.setAdapter(new GridViewAdapter(itemView.getContext(), mDatas, index));
                mViewPagerGridList.add(grid);
            }
            mViewPagerGrid.setAdapter(new ViewListPagerAdapter(mViewPagerGridList));

            circleIndicator = (CircleIndicator) itemView.findViewById(R.id.find_viewflowindic);
            circleIndicator.setViewPager(mViewPagerGrid);
        }
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

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        public ViewHolderTwo(View itemView) {
            super(itemView);
        }
    }

    class ViewHolderThree extends RecyclerView.ViewHolder {
        private DropDownMenu mDropDownMenu1;
        final List<String> list1 = Arrays.asList("全部", "衣服","鞋子");
        final List<String> list2 = Arrays.asList("默认排序","升序","降序");
        final List<String> list3 = Arrays.asList("好评","差评");
        final List<String> list4 = Arrays.asList("全部", "充值", "提现","金钱袋买入","金钱袋卖出","回购金额","入库","兑换","定投转入","回购工费","定期赎回");

        private ArrayList<DropPosition> datas = new ArrayList<>();

        public ViewHolderThree(View itemView) {
            super(itemView);
            mDropDownMenu1 = (DropDownMenu) itemView.findViewById(R.id.dropDownMenu1);
            DropDownMenuUtils.addTextList(mContext, mDropDownMenu1, new DropDownMenuUtils.OnMenuClickListener() {
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
    }

    class ViewHolderFour extends RecyclerView.ViewHolder {
        private TextView textView;
        private DropPosition dropPosition;

        public ViewHolderFour(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textview1);
//            dropPosition = pDatas.get(getPosition());
            if(dropPosition != null){
                textView.setText(dropPosition.getViewPosition()+"---"+dropPosition.getItemPosition());
            }
        }
    }

    protected void onItemEventClick(RecyclerView.ViewHolder holder) {
        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.OnItemLongClick(v, position);
                return true;
            }
        });
    }


    //广播
}
