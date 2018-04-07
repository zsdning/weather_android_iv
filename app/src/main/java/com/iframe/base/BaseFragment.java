package com.iframe.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by majie on 15/12/29.
 */
public class BaseFragment extends Fragment {
    protected final String TAG = "TAG:  " + this.getClass().getSimpleName();
    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void showShortToast(String msg) {
        getBaseActivity().showShortToast(msg);
    }


    public void showNetErrorToast() {
        getBaseActivity().showNetErrorToast();
    }
}
