package com.geoff.mytrainer;

import android.media.Image;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class RowItem {
    private int imageId;
    private String title;
    private String desc;
    private boolean isSelected;

    public RowItem(int imageId, String title, String desc) {
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
        isSelected = false;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public boolean getIsSelected() {return isSelected; }
    public void setIsSelected(boolean isSelected) { this.isSelected = isSelected; }

    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}
