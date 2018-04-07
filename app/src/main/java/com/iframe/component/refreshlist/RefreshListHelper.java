package com.iframe.component.refreshlist;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import com.iframe.component.refreshlist.swiper.SwipeRefreshLoadLayout;

/**
 * Created by majie on 15/12/29.
 */
public class RefreshListHelper {
    /**
     * A quick init for recyclerView & SwipeRefreshLoadLayout.
     * Same Style in App level.
     *
     * @param recyclerView        animation, decoration
     * @param swipeRefreshLayout color, mode
     */
    public static void init(RecyclerView recyclerView, SwipeRefreshLoadLayout swipeRefreshLayout) {
        swipeRefreshLayout.setMode(SwipeRefreshLoadLayout.Mode.BOTH);
        swipeRefreshLayout.setColor(0xff00ddff, 0xff99cc00, 0xffffbb33, 0xffff4444);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration();
    }
}
