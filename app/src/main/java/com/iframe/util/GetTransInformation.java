package com.iframe.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.creditcloud.model.enums.LoanStatus;

import java.util.Date;

public class GetTransInformation {

    public static String getLoanRate(int rate) {
        String format = "%1$.1f";
        return String.format(format, rate / 100.00);
    }

    /**
     * 返回100，000.00元有千位符和后两位小数
     *
     * @param amount
     * @return
     */
    public static String getSeperateWithFloatAmount(double amount) {
        // String format = "%1$s 元";
        String double_format = "%1$.2f";
        String total = String.format(double_format, amount);
        String point_total = "." + total.substring(total.length() - 2);
        // return
        return StringUtils.addSeparateSign((int) amount) + point_total;
    }

    /**
     * 返回100，000元有千位符整数
     *
     * @param amount
     * @return
     */
    public static String getSeperateAmount(double amount) {
        String format = "%1$s 元";
        String double_format = "%1$.2f";
        String total = String.format(double_format, amount);
        // return String.format(format,
        // "" +
        return StringUtils.addSeparateSign((int) Float.parseFloat(total));
    }

    /**
     * 返回***万
     *
     * @param amount
     * @return
     */
    public static String getWanAmount(double amount) {
        int total = (int) (amount / 10000);
        return "" + total + "万";
    }

    /**
     * 获取起息日
     *
     * @param startDate 起息时间
     * @param date
     * @return
     */
    public static String getStartRate(long startDate, long date, String format) {
        try {
            if (startDate > 0) {
                return DealDate.dateToString(new Date(startDate), format);
            } else {
                return getStartRate(date, format);
            }
        } catch (NullPointerException e) {
            try {
                return getStartRate(date, format);
            } catch (Exception e1) {
                return "暂无起息日";
            }
        }
    }

    /**
     * 获取起息日（按月增加）
     *
     * @param status
     * @param date
     * @return
     */
    public static String getStartRate(long date, String format) {
        if (date > 0) {
            return DealDate.getBehindDay(date, 1, format);
        } else {
            return "暂无起息日";
        }
    }

    /*public static String getSettleTime(LoanExt loan, String format) {
        long time;
        if (loan.getTimeSettled() != null && loan.getTimeSettled() > 0) {
            time = loan.getTimeSettled();
        } else {
            time = loan.getTimeOpen() + loan.getTimeout() * 60 * 60 * 1000;
        }
        return DealDate.dateToString(new Date(time), format);
    }*/

    /**
     * 获取预计到帐日
     *
     * @param startDate 起息时间
     * @param date
     * @param number
     * @param format
     * @return
     */

    public static String getExpectGet(long startDate, long date, int number, String format) {

        try {
            if (startDate > 0) {
                return DealDate.getBehindMonth(startDate, number, format);
            } else {
                return getExpectGet(date, number, format);
            }
        } catch (NullPointerException e) {
            try {
                return getExpectGet(date, number, format);
            } catch (Exception e1) {
                return "暂无预计到账日";
            }
        }

    }

    /**
     * 获取预计到帐日
     * @param date
     * @param number
     * @param format
     * @return
     */

    public static String getExpectGet(long date, int number, String format) {
        if (date > 0) {
            return DealDate.getBehindMonth(date + (1000 * 60 * 60 * 24), number, format);
        } else {
            return "暂无预计到账日";
        }
    }

    /**
     * 获取预期收益
     *
     * @param totalDays       理财天数
     * @param currentYearDays 当年年的总天数
     * @param rate            利率
     * @param money           投资金额
     * @return
     */
    public static String getExpectRate(int totalDays, int currentYearDays, int rate, double money) {
        double expectMoney = ((money * rate * (float) totalDays) / ((float) currentYearDays * 10000.00));
        return getSeperateWithFloatAmount(expectMoney);
    }

    /**
     * 获取158****5558样式的电话号码
     *
     * @param mobile
     * @return
     */
    public static String getMobileNum(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return "";
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }

    /**
     * 获取剩余/总额或者总额
     *
     * @param status
     * @param balance
     * @param amount
     * @return
     */
    public static SpannableString getMoney(String status, double balance, double amount, int color) {
        String money;
        String total;
        String pre = null;

        if (amount < 1) {
            total = "0元";
        } else if (amount < 10000) {
            total = amount + "元";
        } else if (amount < 100000000) {
            int hundred = (int) (amount / 100);
            total = "" + (hundred / 100) + "万";
        } else {
            int million = (int) (amount / 1000000);
            total = "" + (million / 100) + "亿";
        }

        if (status.equals(LoanStatus.OPENED.name())) {
            if (balance < 1) {
                pre = "0元";
            } else if (balance < 10000) {
                pre = balance + "元";
            } else if (balance < 100000000) {
                int hundred = (int) (balance / 100);
                pre = "" + (hundred / 100) + "万";
            } else {
                int million = (int) (balance / 1000000);
                pre = "" + (million / 100) + "亿";
            }
        }

        if (pre == null) {
            money = total;
        } else {
            money = pre + "/" + total;
        }

        SpannableString ss = new SpannableString(money);
        if (pre != null) {
            ss.setSpan(new ForegroundColorSpan(color), 0, pre.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(1.4f), 0, pre.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;

    }

    /**
     * 获取投资人名称
     */
    public static String getInvestUserName(String userName) {
        if (userName.equals("missile")) {
            return "匿名购买";
        }
        return userName;
    }

    public static String getBankCardNum(String num) {
        String bankNum = "未检测到银行卡";
        if (!TextUtils.isEmpty(num)) {
            if (num.length() > 10) {
                bankNum = num.substring(0, 4) + "******" + num.substring(10);
            } else {
                bankNum = num;
            }
        }
        return bankNum;
    }

    /**
     * 根据月份计算总天数
     */
    public static int getTotalDays(long startDate, int months) {
        String getDate = DealDate.getBehindMonth(startDate, months, "yyyy-MM-dd");
        String starDate = DealDate.dateToString(new Date(startDate), "yyyy-MM-dd");
        return DealDate.discrepancy(starDate, getDate) - 1;
    }
}
