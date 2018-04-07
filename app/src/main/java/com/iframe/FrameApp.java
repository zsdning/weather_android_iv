package com.iframe;

import android.app.Application;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;
import com.iframe.base.BaseActivity;
import com.iframe.net.Net;
import com.iframe.ui.account.MyAccountFragment;
import com.iframe.ui.home.HomeFragment;
import com.iframe.ui.product.ProductListFragment;
import com.iframe.util.DataCache;
import com.iframe.util.SPHelper;
import com.iframe.util.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by zsdning on 2016/5/28.
 */
public class FrameApp extends Application {
    private static final String TAG = "FrameApp";
    private static final String APATCH_PATH = "out.apatch";
    /**
     * patch manager
     */
    private PatchManager mPatchManager;

    private MyAccountFragment myAccountFragment;

    private ProductListFragment productListFragment;

    private HomeFragment mainFragment;

    private HashMap<String, Fragment> fragmentMap = new HashMap<String, Fragment>();

    private boolean isDownload;

    private String banks;

    @Override
    public void onCreate() {
        super.onCreate();
        // init sharePreference
        //andfix
        loadPatch();
        //sphelper
        SPHelper.init(this.getApplicationContext());
        //network
        Net.init();
        // share sdk initx
        try {
            ShareSDK.initSDK(this);
        } catch (Exception e) {
            Log.e(TAG, "share sdk error" + e.toString());
        }
        //Data缓存
        DataCache.newInstance(getApplicationContext());
        BaseActivity.setURL("120");// "120"  "101"  "uat"  "real"  "product"

    }

    private void loadPatch() {
        // initialize
        mPatchManager = new PatchManager(this);

        String versionName = Utils.getVersionName(getApplicationContext());
        mPatchManager.init(versionName);
        Log.d(TAG, "PatchManager init.");

        // load existing patch
        mPatchManager.loadPatch();
        Log.d(TAG, "PatchManager loaded.");

        // get new patch
        downloadPatch(versionName);
    }

    /**
     * 首先使用apkpatch-1.0.3 根据两个apk包，生成一个差异文件，就是所谓的补丁文件即patch文件。
     * 然后将这个补丁文件上传到服务器，在原始的代码中由于加入了AndFix，启动时会在后台自动请求
     * 服务器下载这个补丁文件，放到SD卡中。接着使用String patchFileString = file.getAbsolutePath();
     * 得到patch的路径，调用mPatchManager.addPatch(patchFileString)使补丁立即生效。
     * 复制加载补丁成功后，删除sdcard的补丁，避免每次进入程序都重新加载一次。
     */
    private void downloadPatch(String versionName) {
        //TODO url based on versionName
        String url = "http://www.idwzx.com/ccc/download/img/pic_banner7-2-feafa719.jpg";

        Request request = new Request.Builder().url(url).build();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();

                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(SDPath, APATCH_PATH);
                    fos = new FileOutputStream(file);
                    long sum = 0;

                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        Log.d(TAG, "progress=" + progress);
                    }
                    fos.flush();
                    Log.i(TAG, "patch download success");

                    // add patch at runtime
                    String patchFileString = file.getAbsolutePath();
                    File patch = new File(patchFileString);
                    if (patch.exists()) {
                        mPatchManager.addPatch(patchFileString);
                        Log.d(TAG, "apatch:" + patchFileString + " added.");
                        patch.delete();  //delete after usage
                    }

                } catch (Exception e) {
                    Log.i(TAG, "patch download failed");

                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                Log.i(TAG, "patch download failed");
            }
        });
    }

    @Override
    public void onTerminate() {
        productListFragment = null;
        mainFragment = null;
        myAccountFragment = null;
        ShareSDK.stopSDK();
        super.onTerminate();
    }

    public String getBanks() {
        return banks;
    }

    public void setBanks(String banks) {
        this.banks = banks;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

    public MyAccountFragment getMyAccountFragment() {
        return myAccountFragment;
    }

    public void setMyAccountFragment(MyAccountFragment myAccountFragment) {
        this.myAccountFragment = myAccountFragment;
    }

    public ProductListFragment getProductListFragment() {
        return productListFragment;
    }

    public void setProductListFragment(ProductListFragment productListFragment) {
        this.productListFragment = productListFragment;
    }

    public HomeFragment getMainFragment() {
        return mainFragment;
    }

    public void setMainFragment(HomeFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public HashMap<String, Fragment> getFragmentMap() {
        return fragmentMap;
    }

    public void setFragmentMap(HashMap<String, Fragment> fragmentMap) {
        this.fragmentMap = fragmentMap;
    }
}
