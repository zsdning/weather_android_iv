package com.iframe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.iframe.net.model.UserInfo;

/**
 * sharedPreference存储类 在App中初始化
 */
public class SPHelper {
    private static SPHelper sphelper;
    private static SharedPreferences sharedPreferences;
    private static Encryption encryption;

    private SPHelper(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //FIXME getDefaultCipher may return error
        //AES算法  有些手机不支持  需要进行异常处理
        try {
            this.encryption = new Encryption(Encryption.getDefaultCipher(), context);
        } catch (AssertionError error) {
            this.encryption = null;
        } catch (Exception e) {
            this.encryption = null;
        }
    }

    public static synchronized SPHelper getInstance() {
        return sphelper;
    }

    public static void init(Context context) {
        sphelper = new SPHelper(context);
    }

    private String getEncryptKey(String key) {
        String enKey = key;
        try {
            Base64.encodeToString(key.getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return enKey;
        }
    }

    public String getLoginToken() {
        return encryption != null ? encryption.decrypt(sharedPreferences.getString(getEncryptKey("token"), ""))
                : sharedPreferences.getString(getEncryptKey("token"), "");
    }

    public boolean isLoginToken() {
        boolean state;
        if (TextUtils.equals(getLoginToken(), "")) {
            state = false;
        } else {
            state = true;
        }
        return state;
    }

    /**
     * 登陆token
     *
     * @param token
     */
    public void setLoginToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getEncryptKey("token"), encryption.encrypt(token));
        Log.i("sp", "setLoginToken: " + token);
        editor.commit();
    }

    public void setLoginInfo(UserInfo userInfo) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getEncryptKey("userName"), encryption.encrypt(userInfo.getUserName()));
        editor.putString(getEncryptKey("realName"), encryption.encrypt(userInfo.getRealName()));
        editor.putString(getEncryptKey("phoneNo"), encryption.encrypt(userInfo.getPhoneNo()));
        editor.putString(getEncryptKey("LegalPersonName"), encryption.encrypt(userInfo.getLegalPersonName()));
        editor.putString(getEncryptKey("LegalPersonPhone"), encryption.encrypt(userInfo.getLegalPersonPhone()));
        editor.putString(getEncryptKey("companyNameFull"), encryption.encrypt(userInfo.getCompanyNameFull()));
        editor.putString(getEncryptKey("companyNameShort"), encryption.encrypt(userInfo.getCompanyNameShort()));
        editor.commit();
    }

    public String getUserName() {
        return encryption != null ? encryption.decrypt(sharedPreferences.getString(getEncryptKey("userName"), ""))
                : sharedPreferences.getString(getEncryptKey("userName"), "");
    }

    public String getRealName() {
        return encryption != null ? encryption.decrypt(sharedPreferences.getString(getEncryptKey("realName"), ""))
                : sharedPreferences.getString(getEncryptKey("realName"), "");
    }

    public String getPhoneNo() {
        return encryption != null ? encryption.decrypt(sharedPreferences.getString(getEncryptKey("phoneNo"), ""))
                : sharedPreferences.getString(getEncryptKey("phoneNo"), "");
    }

    public String getCapitalId() {
        return encryption != null ? encryption.decrypt(sharedPreferences.getString(getEncryptKey("capitalId"), ""))
                : sharedPreferences.getString(getEncryptKey("capitalId"), "");
    }

    public void setCapitalId(String capitalId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getEncryptKey("capitalId"), encryption.encrypt(capitalId));
        Log.i("sp", "setCapitalId: " + capitalId);
        editor.commit();
    }

    public void setGesture(String gesture, String userName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getEncryptKey("gesture" + userName), StringUtils.getMD5(gesture));
        editor.commit();
    }


    public String getGesture(String userName) {
        return sharedPreferences.getString(getEncryptKey("gesture" + userName), "");
    }

    public boolean isFirstLogin(String userName) {
        return sharedPreferences.getBoolean(getEncryptKey("isFirst" + userName), true);
    }

    public void setFirstLogin(String userName, boolean isFirst) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getEncryptKey("isFirst" + userName), isFirst);
        editor.commit();
    }

    public void cleanLoginInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getEncryptKey("userName"), "");
        editor.putString(getEncryptKey("realName"), "");
        editor.putString(getEncryptKey("phoneNo"), "");
        editor.putString(getEncryptKey("LegalPersonName"), "");
        editor.putString(getEncryptKey("LegalPersonPhone"), "");
        editor.putString(getEncryptKey("companyNameFull"), "");
        editor.putString(getEncryptKey("companyNameShort"), "");
        editor.putString(getEncryptKey("token"), "");
        editor.commit();
    }


    public void setAuthed(String userId, boolean isAuthed) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(userId + "isAuthed", isAuthed);
        editor.commit();
    }

    public boolean isAuthed(String userId) {
        return sharedPreferences.getBoolean(userId + "isAuthed", false);
    }

    public String getIdNum() {
        return encryption != null ? encryption.decrypt(sharedPreferences.getString("idNum", ""))
                : sharedPreferences.getString("idNum", "");
    }

    public long getGestureTime() {
        SharedPreferences preferences = sharedPreferences;
        return preferences.getLong("gestureTime", System.currentTimeMillis());
    }

    public void setGestureTime(long time) {
        SharedPreferences preferences = sharedPreferences;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("gestureTime", time);
        editor.commit();
    }

    public long getUpdateTime() {
        SharedPreferences preferences = sharedPreferences;
        return preferences.getLong("updateTime", 0);
    }

    public void setUpdateTime(long time) {
        SharedPreferences preferences = sharedPreferences;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("updateTime", time);
        editor.commit();
    }


}
