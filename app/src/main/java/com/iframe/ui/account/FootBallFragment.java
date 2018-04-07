package com.iframe.ui.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iframe.R;
import com.iframe.base.BaseFragment;

/**
 * Created by zsdning on 2016/9/2.
 */
public class FootBallFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (OutdoorActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_football, container, false);
        findView(view);
        return view;
    }

    private void findView(View view){

    }

}
