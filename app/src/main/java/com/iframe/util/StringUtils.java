/*
 * Copyright (c) 2014.
 * Jackrex
 */

package com.iframe.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return !(email == null || email.trim().length() == 0) && emailer.matcher(email).matches();
    }

    // used by friendly_time
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    // used by toDate
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    public static boolean isStringEmpty(String string) {
        return string == null || TextUtils.isEmpty(string);

    }

    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static float GetDistance(float lng1, float lat1, float lng2,
                                    float lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return (float) s;
    }

    /**
     * 字符串敏感截取，超出�?..结束，处理后的长�?=num区分字母和汉�?
     *
     * @param str
     * @param num
     * @return String
     */
    public static String subStrSensitive(String str, int num) {
        if (str == null || num <= 3) {
            return str;
        }
        StringBuffer sbStr = new StringBuffer();
        String returnStr = "";
        int length = 0;
        char temp;
        for (int i = 0; i < str.length(); i++) {
            temp = str.charAt(i);
            if (isChinese(temp)) {
                length = length + 2;
            } else {
                length++;
            }
            if (length == num && i == (str.length() - 1)) {
                sbStr.append(temp);
                returnStr = sbStr.toString();
                break;
            } else if (length >= num) {
                sbStr.append("...");
                returnStr = sbStr.toString();
                try {
                    int lastL = returnStr.getBytes("GBK").length;

                    while (lastL > num) {
                        returnStr = returnStr.substring(0,
                                returnStr.length() - 4) + "...";

                        lastL = returnStr.getBytes("GBK").length;
                    }
                    break;
                } catch (Exception e) {
                    break;
                }
            }
            sbStr.append(temp);
            if (i == (str.length() - 1)) {

                returnStr = sbStr.toString();
            }
        }

        return returnStr;
    }

    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    public static boolean containsChinese(String s) {
        if (null == s || "".equals(s.trim()))
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static String defaultString(String value) {
        return defaultString(value, "");
    }

    public static String defaultString(String value, String defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    public static String encodeHexString(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();
        StringUtils.encodeHexString(bytes, buffer);
        return buffer.toString();
    }

    public static void encodeHexString(byte[] bytes, StringBuilder buffer) {
        for (byte b : bytes) {
            int hi = (b >>> 4) & 0x0f;
            int lo = (b >>> 0) & 0x0f;
            buffer.append(Character.forDigit(hi, 16));
            buffer.append(Character.forDigit(lo, 16));
        }
    }

    public static String join(String[] array, String delimiter) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);

            if (i < (array.length - 1)) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static String createUrlWithParam(String url, String param) {
        if (url == null) {
            return null;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (!url.endsWith("/")) {
            urlBuilder.append("/");
        }

        urlBuilder.append(param);
        return urlBuilder.toString();
    }

    public static String decodeMessage(String str, String jsonNodeName) {
        String result = str;
        try {
            JSONObject json = new JSONObject(str);
            result = (String) json.get(jsonNodeName);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static String getMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.substring(8, 24).toString();
    }

    public static String convertStreamToString(InputStream is)
            throws IOException {
        if (is != null) {

            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * Convert and replace "abc","2013-06-15 15:15:12" -> "abc","1371280512000"
     *
     * @param source
     * @return
     */
    public static String convertAndReplace(String source) {
        String formatString = "[\"]\\d{4}[\\-]\\d{2}[\\-]\\d{2} \\d{2}[\\:]\\d{2}[\\:]\\d{2}[\"]";

        Pattern pattern = Pattern.compile(formatString);
        Matcher matcher = pattern.matcher(source);

        while (matcher.find()) {
            String find = matcher.group();
            int len = find.length();
            find = find.substring(1, len - 1);
            Calendar cal = CalendarUtils.YYYYMMDDHHMMSSToCalendar(find);
            // cal.add(Calendar.MONTH, 1);
            if (cal != null) {
                long time = cal.getTimeInMillis();
                String targetString = "\"" + String.valueOf(time) + "\"";
                source = source.replaceFirst(formatString, targetString);
            }
        }

        return source;
    }

    public static String fullTohalf(String fullStr) {
        String outStr = "";
        String Tstr = "";
        byte[] b = null;

        for (int i = 0; i < fullStr.length(); i++) {
            try {
                Tstr = fullStr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else
                outStr = outStr + Tstr;
        }
        return outStr;
    }

    public static String urlWithParameters(String url,
                                           HashMap<String, Object> params) {
        StringBuilder builder = new StringBuilder();

        if (params != null) {
            for (HashMap.Entry<String, Object> entry : params.entrySet()) {
                if (builder.length() == 0) {
                    if (url.contains("?")) {
                        builder.append("&");
                    } else {
                        builder.append("?");
                    }
                } else {
                    builder.append("&");
                }
                builder.append(entry.getKey()).append("=")
                        .append(entry.getValue());
            }
        }

        return url + builder.toString();
    }

    public static String urlWithParameters(String url,
                                           Map<String, Object> params) {
        StringBuilder builder = new StringBuilder();

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (builder.length() == 0) {
                    if (url.contains("?")) {
                        builder.append("&");
                    } else {
                        builder.append("?");
                    }
                } else {
                    builder.append("&");
                }
                builder.append(entry.getKey()).append("=")
                        .append(entry.getValue());
            }
        }

        return url + builder.toString();
    }


    public static String getCountString(float count) {
        int a = (int) count;
        float b = count - a;

        if (b > 0) {
            return String.format("%.1f", count);
        } else {
            return String.valueOf(a);
        }
    }

    // public static void mergeParam(HashMap<String, Object> params,
    // LinkedMultiValueMap valuePair) {
    // if (params != null) {
    // for (HashMap.Entry<String, Object> entry : params.entrySet()) {
    // String key = entry.getKey();
    // String value = String.valueOf(entry.getValue());
    // valuePair.add(key, value);
    // }
    // }
    // }

    public static String[] getIpAndPort(String ip) {
        if (ip == null || ip.trim().length() < 4) {
            return null;
        }
        String[] strs = null;

        int index = ip.indexOf(":");
        if (index != -1) {
            strs = ip.split(":");
        } else {
            strs = new String[]{ip, "80"};
        }
        return strs;
    }

    @SuppressLint("NewApi")
    public String insertWithOnConflict(String table, ContentValues initialValues) {
        StringBuilder col = new StringBuilder();
        col.append("INSERT");
        col.append(" INTO ");
        col.append(table);
        col.append('(');

        StringBuilder val = new StringBuilder();
        val.append(" VALUES (");

        try {
            int size = (initialValues != null && initialValues.size() > 0) ? initialValues
                    .size() : 0;
            if (size > 0) {
                int i = 0;
                for (String colName : initialValues.keySet()) {
                    col.append((i > 0) ? "," : "");
                    col.append("[" + colName + "]");

                    Object o = initialValues.get(colName);
                    val.append((i > 0) ? "," : "");
                    val.append("\"");
                    val.append(o);
                    val.append("\"");
                    i++;
                }
                col.append(')');
                val.append(')');

                col.append(val);
                col.append(";");
                return col.toString();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将整型添加“,”隔位符
     * “123456789”-》“123，456，789”
     *
     * @param number
     * @return
     */
    public static String addSeparateSign(int number) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(number);
    }


    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * ��֤�ֻ����
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isNum(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]{5}$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }

    public static void savePwdMD5(String input) {
        if(!TextUtils.isEmpty(input)) {
            String string = md5(input);
            DataCache.getDataCache().saveToCache("0xdeadbeef", string);
        }
    }

    public static String getPwdMD5() {
        return DataCache.getDataCache().queryCache("0xdeadbeef");
    }

    public static boolean isMatchLoginPwd(String input) {
        boolean result = false;
        String string = md5(input);
        String pwdMd5 = getPwdMD5();
        if(TextUtils.isEmpty(pwdMd5) || TextUtils.isEmpty(string)){
            result = false;
        } else {
            result = string.equals(pwdMd5);
        }

        return result;
    }
}
