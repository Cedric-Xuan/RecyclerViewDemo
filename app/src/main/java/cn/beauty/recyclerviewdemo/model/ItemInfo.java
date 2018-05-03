package cn.beauty.recyclerviewdemo.model;

/**
 * Created by Simon Meng on 2018/4/23.
 * Guangzhou Beauty Information Technology Co.,Ltd
 */
public class ItemInfo {
    private int mResId;

    public ItemInfo(int resId) {
        this.mResId = resId;
    }

    public void setImageUri(int id) {
        this.mResId = id;
    }

    public int getImageUri() {
        return mResId;
    }

}
