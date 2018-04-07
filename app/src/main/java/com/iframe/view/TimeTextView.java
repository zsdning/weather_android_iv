package com.iframe.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iframe.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 时间控件
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TimeTextView extends LinearLayout {
    private long mday, mhour, mmin, msecond;// 天，小时，分钟，秒
    private boolean run = false; // 是否启动了
    Timer timer = new Timer();
    TextView Vday, Vhour, Vmin, Vseconds, last;
    LinearLayout layout;
    private ITimerWatcher timerWatcher;

    public TimeTextView(Context context) {
        super(context);
        iniUI(context);
    }

    public void setTimerWatcher(ITimerWatcher watcher) {
        timerWatcher = watcher;
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        iniUI(context);
    }

    public interface ITimerWatcher {
        public void onTimerStop();
    }

    public void iniUI(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View myView = mInflater.inflate(R.layout.view_counttimer, null);

        layout = (LinearLayout) myView.findViewById(R.id.layout_counttimer);
        Vday = (TextView) myView.findViewById(R.id.tv_day);
        Vhour = (TextView) myView.findViewById(R.id.tv_hour);
        Vmin = (TextView) myView.findViewById(R.id.tv_minute);
        Vseconds = (TextView) myView.findViewById(R.id.tv_second);
        last = (TextView) myView.findViewById(R.id.tv_last);
        addView(myView);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iniUI(context);
    }

    private Handler mHandler = new Handler() {

    };

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void start() {
        if (!isRun()) {
            setRun(true);
            try {
                timer.schedule(task, 1000, 1000);
            } catch (Exception e) {
                e.printStackTrace();
//				timer.cancel();
//				timer.schedule(task, 1000, 1000);
            }
        }
    }

    /**
     * 根据传进来的时间差 为textview 赋值
     *
     * @param duration
     */
    public void setTimes(long duration) {
        mday = duration / 60000 / 60 / 24;
        mhour = (duration - mday * 24 * 60 * 60000) / 60000 / 60;
        mmin = (duration - mhour * 60000 * 60 - mday * 3600000 * 24) / 60000;
        msecond = (duration - mmin * 60000 - mhour * 3600000 - mday * 3600000 * 24) / 1000;

    }

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        msecond--;
        if (msecond < 0) {
            mmin--;
            msecond = 59;
            if (mmin < 0) {
                mmin = 59;
                mhour--;
                if (mhour < 0) {
                    // 倒计时结束
                    mhour = 24;
                    mday--;
                }
            }
        }
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            mHandler.post(new Runnable() { // UI thread
                @Override
                public void run() {
                    run = true;
                    ComputeTime();
                    if (mday < 0) {
                        // setVisibility(View.GONE);
                        setLast(true, "已完结,请刷新");
                        setRun(false);
                        if (timerWatcher != null) {
                            timerWatcher.onTimerStop();
                        }
                    }
                    Vday.setText(mday < 10 ? ("0" + mday) : mday + "");
                    Vhour.setText(mhour < 10 ? ("0" + mhour) : mhour + "");
                    Vseconds.setText(msecond < 10 ? ("0" + msecond) : msecond
                            + "");
                    Vmin.setText(mmin < 10 ? ("0" + mmin) : mmin + "");
                }
            });
        }
    };

    public void setLast(boolean hintOther, String text) {
        if (hintOther) {
            layout.setVisibility(View.GONE);
            last.setText(text);
        } else {
            layout.setVisibility(View.VISIBLE);
            last.setText(text);
        }
    }
}