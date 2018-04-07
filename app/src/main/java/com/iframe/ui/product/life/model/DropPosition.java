package com.iframe.ui.product.life.model;

/**下拉筛选菜单的位置bean
 * Created by zsdning on 2016/9/27.
 */
public class DropPosition {
    private int viewPosition;
    private int itemPosition;

    public DropPosition(int viewPosition, int itemPosition) {
        this.viewPosition = viewPosition;
        this.itemPosition = itemPosition;
    }

    public int getViewPosition() {
        return viewPosition;
    }

    public void setViewPosition(int viewPosition) {
        this.viewPosition = viewPosition;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }
}
