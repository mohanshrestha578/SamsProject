package com.example.samsproject;

public class ImageUpload {

    private String mItemName;
    public String mImageUrl;
    private String mCategory;
    private String mPrice;


    public ImageUpload(){}

    public ImageUpload(String mItemName, String mImageUrl, String mCategory, String mPrice) {

        if(mItemName.trim().equals("")){
            mItemName = "No Name";
        }
        this.mItemName = mItemName;
        this.mImageUrl = mImageUrl;
        this.mCategory = mCategory;
        this.mPrice = mPrice;
    }

    public String getmItemName() {
        return mItemName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmCategory() {
        return mCategory;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }
}
