package com.iframe.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.iframe.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by majie on 15/12/11.
 */
public class ShareUtils {

    private static void share(Context context, String url, String title, String text, String imageUrl) {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle(title);  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText(text);  //最多40个字符

        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
       // oks.setImagePath(getAssetPath("icon.png", context));//确保SDcard下面存在此张图片

        //网络图片的url：所有平台
        if(!TextUtils.isEmpty(imageUrl)){
            oks.setImageUrl(imageUrl);//网络图片url
        }
        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl(url);  //网友点进链接后，可以看到分享的详情

        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        // 启动分享GUI
        oks.show(context);
    }

    public static void shareInvite(Context context, String url) {
        share(context, url, "邀请注册有好礼", "赶快来注册极速云天气吧。","");
    }

    public static void shareHongbao(Context context, String url) {
        share(context, url, "我们之间，只隔了一个红包的距离", "你在那里，我们在这里，想要靠近？点击分享即可。","");
    }

    public static void shareHongbao(Context context, String url, String imageUrl) {
        share(context, url, "我们之间，只隔了一个红包的距离", "你在那里，我们在这里，想要靠近？点击分享即可。", imageUrl);
    }

    private static String getAssetPath(String path, Context context) {
        InputStream is;
        String fPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.i("file", "getAssetPath() fPath: " + fPath);
        try {
            is = context.getAssets().open(path);

            FileOutputStream fos = new FileOutputStream(fPath + "/" + path);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(fPath + "/" + path);
        Log.i("file", "getAssetPath() returned: " + file.getAbsolutePath());
        return file.getAbsolutePath();
    }

}
