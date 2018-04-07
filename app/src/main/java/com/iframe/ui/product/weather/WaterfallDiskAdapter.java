package com.iframe.ui.product.weather;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.net.model.Scene;
import com.iframe.util.DiskLruCache;
import com.iframe.util.DiskLruCacheUtil;

import java.util.ArrayList;
import java.util.List;

/** 硬盘缓存的解决方案：DiskLruCache
 * Created by zsdning on 16/6/13.
 */
public class WaterfallDiskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private Context context;
    private ArrayList<Scene> datas = new ArrayList<Scene>();
    private Scene scene;
    private String imageUrl;
    private int buttonType = 0;
    private View mHeaderView;

    /**
     * DiskLruCacheUtil实例
     */
    private DiskLruCacheUtil instance;
    /**
     * 图片硬盘缓存核心类。
     */
    private DiskLruCache mDiskLruCache;

    /**
     * 记录每个子项的高度。
     */
    private int mItemHeight = 0;

    /**
     * RecyclerView的实例
     */
    private RecyclerView mPhotoWall;

    private PrdListViewHolder holder;

    public WaterfallDiskAdapter(Context context) {
        this.context = context;
        //DiskLruCache  单例模式
        instance = DiskLruCacheUtil.getIntance(context, mPhotoWall);
        mDiskLruCache = instance.getDiskLruCache();
    }

    public List<Scene> getData() {
        return datas;
    }

    public void setData(ArrayList<Scene> data) {
        datas = data;
    }

    public RecyclerView getmPhotoWall() {
        return mPhotoWall;
    }

    public void setmPhotoWall(RecyclerView mPhotoWall) {
        this.mPhotoWall = mPhotoWall;
    }

    public int getButtonType() {
        return buttonType;
    }

    public void setButtonType(int buttonType) {
        this.buttonType = buttonType;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new PrdListViewHolder(mHeaderView);
        }
        return new PrdListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.infos_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        if (viewHolder instanceof PrdListViewHolder) {
            holder = (PrdListViewHolder) viewHolder;
            scene = datas.get(getRealPosition(viewHolder));

            holder.textView.setText(scene.getTitle());

            //动态设置宽高，因为要实现瀑布流效果，就是高度随机，参差不齐那种视觉效果
            int width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width / 2 - 5;       //间隔5
            //params.height = (int) (200 + Math.random() * 400);   //200-600 尽量不取随机值  会导致取滑动距离 totalDy 变得不精确
            params.height = 400;
            holder.imageView.setLayoutParams(params);

            //根据是否加载过决定从缓存取或异步请求获取图片
            imageUrl = scene.getLink();
            // 给ImageView设置一个Tag，保证异步加载图片时不会乱序
            holder.imageView.setTag(imageUrl);
            holder.imageView.setImageResource(R.drawable.default_image);

            holder.view_bg.setTag(imageUrl);
            holder.view_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoDetailActivity.startAc(context, imageUrl);
                }
            });

            DiskLruCacheUtil.getIntance(context, mPhotoWall).loadBitmaps(holder.imageView, imageUrl);
        }
    }

    /**
     * 设置item子项的高度。
     */
    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        notifyDataSetChanged();
    }

    /**
     * 将缓存记录同步到journal文件中。
     */
    public void fluchCache() {
        instance.fluchCache();
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public void cancelAllTasks() {
        instance.cancelAllTasks();
    }


    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? datas.size() : datas.size() + 1;
    }

    //当前位置是header的位置，那么该item占据2个单元格，正常情况下占据1个单元格
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    //当前位置是header的位置，那么该item占据2个单元格，正常情况下占据1个单元格。
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }


    class PrdListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private View view_bg;

        public PrdListViewHolder(final View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }

            imageView = (ImageView) itemView.findViewById(R.id.masonry_item_img);
            textView = (TextView) itemView.findViewById(R.id.masonry_item_title);
            view_bg = itemView.findViewById(R.id.view_bg);
        }
    }
}
