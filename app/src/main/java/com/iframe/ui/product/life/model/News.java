package com.iframe.ui.product.life.model;

/**多种ITEM的recyclerview的bean
 * Created by zsdning on 2016/9/26.
 */
public class News {
    private int itemType;

    public News(int type){
        this.itemType = type;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
