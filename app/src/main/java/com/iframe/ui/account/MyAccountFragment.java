package com.iframe.ui.account;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iframe.R;
import com.iframe.base.BaseFragment;
import com.iframe.ui.login.LoginActivity;
import com.iframe.ui.main.MainActivity;
import com.iframe.ui.settings.SettingActivity;
import com.iframe.util.SPHelper;

/**
 * Created by zsdning on 16/5/29.  我的账户页
 */
public class MyAccountFragment extends BaseFragment implements View.OnClickListener{

    public static final String REFRESHACTION = "HomeFragment.REFRESH_ACTION";
    private MainActivity activity;
    private TextView loanTitle;
    private TextView collect, goNight, set;
    private View layoutLogout, layoutLogin;
    private Button login_register;
    private Button goFriend, goNotification, goOutline, goOutdoor, goNews, goFeedback;
    private ImageView help;

    private RefreshReceiver refreshReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_myaccount, container, false);
        findView(view);

        return view;
    }

    private void findView(View view) {
        layoutLogout = view.findViewById(R.id.layout_logout);
        layoutLogin = view.findViewById(R.id.layout_login);
        login_register = (Button) view.findViewById(R.id.btn_login_register);
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, LoginActivity.class));
            }
        });
        collect = (TextView) view.findViewById(R.id.btn_collect);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, CollectionActivity.class));
            }
        });
        goNight = (TextView) view.findViewById(R.id.btn_gonight);
        goNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        set = (TextView) view.findViewById(R.id.btn_set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, SettingActivity.class));
            }
        });

        goFriend = (Button) view.findViewById(R.id.btn_myfriend);
        goNotification = (Button) view.findViewById(R.id.btn_notification);
        goOutline = (Button) view.findViewById(R.id.btn_outline);
        goOutdoor = (Button) view.findViewById(R.id.btn_outdoor);
        goNews = (Button) view.findViewById(R.id.btn_news);
        goFeedback = (Button) view.findViewById(R.id.btn_feedback);

        goFriend.setOnClickListener(this);
        goNotification.setOnClickListener(this);
        goOutline.setOnClickListener(this);
        goOutdoor.setOnClickListener(this);
        goNews.setOnClickListener(this);
        goFeedback.setOnClickListener(this);
    }

    private void initData() {
        //未登录状态
        if (TextUtils.isEmpty(SPHelper.getInstance().getLoginToken())) {
            showLogin(true);
        }else{
            showLogin(false);
        }
    }

    private void showLogin(Boolean show) {
        if (show) {
            layoutLogout.setVisibility(View.VISIBLE);
            layoutLogin.setVisibility(View.INVISIBLE);
        } else {
            layoutLogout.setVisibility(View.INVISIBLE);
            layoutLogin.setVisibility(View.VISIBLE);
        }
    }

    private void register() {
        IntentFilter refreshFilter = new IntentFilter();
        refreshFilter.addAction(REFRESHACTION);
        refreshReceiver = new RefreshReceiver();
        activity.registerReceiver(refreshReceiver, refreshFilter);
    }

    @Override
    public void onDetach() {
        if (refreshReceiver != null) {
            activity.unregisterReceiver(refreshReceiver);
        }
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        activity = (MainActivity) this.getActivity();
        super.onActivityCreated(savedInstanceState);
        register();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        //Log.i("resume","initData");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_myfriend:
                activity.startActivity(new Intent(activity, FriendActivity.class));
                break;
            case R.id.btn_notification:
                activity.startActivity(new Intent(activity, NotificationActivity.class));
                break;
            case R.id.btn_outline:
                activity.startActivity(new Intent(activity, OutlineActivity.class));
                break;
            case R.id.btn_outdoor:
                activity.startActivity(new Intent(activity, OutdoorActivity.class));
                break;
            case R.id.btn_news:
                activity.startActivity(new Intent(activity, NewsActivity.class));
                break;
            case R.id.btn_feedback:
                activity.startActivity(new Intent(activity, FeedbackActivity.class));
                break;
            default:
                break;
        }
    }

    class RefreshReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (REFRESHACTION.equals(action)) {
                initData();
            }
        }
    }
}
