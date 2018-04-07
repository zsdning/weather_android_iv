package com.iframe.ui.product.weather;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.net.model.Scene;
import com.iframe.ui.main.MainActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsdning on 16/6/13.
 */
public class WaterfallMemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Scene> datas = new ArrayList<Scene>();
    private ArrayList<ImageView> views = new ArrayList<ImageView>();
    private Scene scene;
    private Bitmap mBitmap; //
    private LruCache<String ,BitmapDrawable> mMemoryCache;//
    private String imageUrl;
    private int buttonType = 0;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;

    public WaterfallMemAdapter(Context context) {
        this.context = context;
        //默认显示的图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.default_image);
        //计算内存，并且给Lrucache 设置缓存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/6;
        mMemoryCache = new LruCache<String ,BitmapDrawable>(cacheSize){
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return  value.getBitmap().getByteCount();
            }
        };
    }

    public void setData(ArrayList<Scene> data) {
        datas = data;
    }

    public List<Scene> getData() {
        return datas;
    }

    public int getButtonType() {
        return buttonType;
    }

    public void setButtonType(int buttonType) {
        this.buttonType = buttonType;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
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
            PrdListViewHolder holder = (PrdListViewHolder) viewHolder;
            scene = datas.get(getRealPosition(viewHolder));

            holder.textView.setText(scene.getTitle());

            //动态设置宽高，因为要实现瀑布流效果，就是高度随机，参差不齐那种视觉效果
            int width = ((MainActivity) context).getWindowManager().getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width / 2 - 5;       //间隔5
            params.height = (int) (200 + Math.random() * 400);   //200-600
            holder.imageView.setLayoutParams(params);

            //根据是否加载过决定从缓存取或异步请求获取图片
            imageUrl = scene.getLink();
            BitmapDrawable drawable = getBitmapDrawableFromMemoryCache(imageUrl);
            if (drawable != null){
                holder.imageView.setImageDrawable(drawable);
            }else if (cancelPotentialTask(imageUrl,holder.imageView)){
                //执行下载操作
                DownLoadTask task = new DownLoadTask(holder.imageView);
                AsyncDrawable asyncDrawable = new AsyncDrawable(context.getResources(),mBitmap,task);
                holder.imageView.setImageDrawable(asyncDrawable);
                task.execute(imageUrl);
            }

        }
    }

    /**
     * 检查复用的ImageView中是否存在其他图片的下载任务，如果存在就取消并且返回ture 否则返回 false
     * @param imageUrl
     * @param imageView
     * @return
     */
    private boolean cancelPotentialTask(String imageUrl, ImageView imageView) {
        DownLoadTask task = getDownLoadTask(imageView);
        if (task != null) {
            String url = task.url;
            if (url == null || !url .equals(imageUrl)){
                task.cancel(true);
            }else{
                return false;
            }
        }
        return true;
    }


    /**
     * 從缓存中获取已存在的图片
     * @param imageUrl
     * @return
     */
    private BitmapDrawable getBitmapDrawableFromMemoryCache(String imageUrl) {
        return mMemoryCache.get(imageUrl);
    }

    /**
     * 添加图片到缓存中
     * @param imageUrl
     * @param drawable
     */
    private void addBitmapDrawableToMemoryCache(String imageUrl,BitmapDrawable drawable){
        if (getBitmapDrawableFromMemoryCache(imageUrl) == null ){
            mMemoryCache.put(imageUrl, drawable);
        }
    }

    /**
     * 获取当前ImageView 的图片下载任务
     * @param imageView
     * @return
     */
    private DownLoadTask getDownLoadTask(ImageView imageView){
        if (imageView != null){
            Drawable drawable  = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable ){
                return  ((AsyncDrawable) drawable).getDownLoadTaskFromAsyncDrawable();
            }
        }
        return null;
    }

    /**
     * 新建一个类 继承BitmapDrawable
     * 目的： BitmapDrawable 和DownLoadTask建立弱引用关联
     */
    class AsyncDrawable extends  BitmapDrawable{
        private WeakReference<DownLoadTask> downLoadTaskWeakReference;

        public AsyncDrawable(Resources resources, Bitmap bitmap, DownLoadTask downLoadTask){
            super(resources,bitmap);
            downLoadTaskWeakReference = new WeakReference<DownLoadTask>(downLoadTask);
        }

        private DownLoadTask getDownLoadTaskFromAsyncDrawable(){
            return downLoadTaskWeakReference.get();
        }
    }

    /**
     * 异步加载图片
     * DownLoadTash 和 ImagaeView建立弱引用关联。
     */
    class DownLoadTask extends AsyncTask<String ,Void,BitmapDrawable> {
        String url;
        private WeakReference<ImageView> imageViewWeakReference;
        public DownLoadTask(ImageView imageView){
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }
        @Override
        protected BitmapDrawable doInBackground(String... params) {
            url = params[0];
            Bitmap bitmap = downLoadBitmap(url);
            BitmapDrawable drawable = new BitmapDrawable(context.getResources(),bitmap);
            addBitmapDrawableToMemoryCache(url,drawable);
            return  drawable;
        }

        /**
         * 验证ImageView 中的下载任务是否相同 如果相同就返回
         * @return
         */
        private ImageView getAttachedImageView() {
            ImageView imageView = imageViewWeakReference.get();
            if (imageView != null){
                DownLoadTask task = getDownLoadTask(imageView);
                if (this == task ){
                    return  imageView;
                }
            }
            return null;
        }

        /**
         * 下载图片 这里使用google 推荐使用的OkHttp
         * @param url
         * @return
         */
        private Bitmap downLoadBitmap(String url) {
            Bitmap bitmap = null;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 5;//图片大小，设置越大，图片越不清晰，占用空间越小  1/4
                bitmap = BitmapFactory.decodeStream(response.body().byteStream(), null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            super.onPostExecute(drawable);
            ImageView imageView = getAttachedImageView();
            if ( imageView != null && drawable != null){
                imageView.setImageDrawable(drawable);
            }
        }
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

        public PrdListViewHolder(final View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }

            imageView = (ImageView) itemView.findViewById(R.id.masonry_item_img);
            textView = (TextView) itemView.findViewById(R.id.masonry_item_title);

        }
    }
}
