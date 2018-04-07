package com.iframe.ui.product.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseActivity;
import com.iframe.util.DiskLruCache;
import com.iframe.util.ShareUtils;
import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {
    private ImageView photo;
    private ImageView share;
    private RecyclerView recyclerView;
    private TextView title;
    //图片硬盘缓存核心类。
    private DiskLruCache mDiskLruCache;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        getIntents();
        findView();
    }

    private void findView() {
        photo = (ImageView) findViewById(R.id.iv_photo);
        share = (ImageView) findViewById(R.id.btn_share);
        share.setVisibility(View.VISIBLE);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShare();
            }
        });
        title = (TextView) findViewById(R.id.title);
        title.setText("详情");
        //mDiskLruCache = DiskLruCacheUtil.getIntance(this, photo).getDiskLruCache();

        // 给photo设置一个Tag，保证加载的
        /*View view = getLayoutInflater().inflate(R.layout.activity_nearphoto, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.scene_list);
        recyclerView.setTag(imageUrl);*/
        photo.setImageResource(R.drawable.default_image);
        Picasso.with(PhotoDetailActivity.this).load(imageUrl).into(photo);
       // DiskLruCacheUtil.getIntance(this, recyclerView).loadBitmaps(photo, imageUrl);
    }

    private void getIntents() {
        if (getIntent() != null) {
            imageUrl = getIntent().getStringExtra("imageUrl");
        }
    }

    private void goShare(){
        //要注意一点分享自己服务器的图片腾讯那边会有一些限制，分享图片失败的时候建议换一张普通的网络图片试试看。
        ShareUtils.shareHongbao(PhotoDetailActivity.this, "http://sharesdk.cn", "http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
    }


    public static void startAc(Context context, String imageUrl) {
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        intent.putExtra("imageUrl", imageUrl);
        context.startActivity(intent);
    }

}
