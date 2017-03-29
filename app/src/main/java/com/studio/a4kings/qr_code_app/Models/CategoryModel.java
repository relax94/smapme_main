package com.studio.a4kings.qr_code_app.Models;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by DUX on 10.02.2016.
 */
public class CategoryModel {
    private int imageDrawableId;
    private String title;
    private Fragment fragment;
    private Intent intent;
    private int bgColor;

    public CategoryModel(int imageDrawableId, String title, int bgColor) {
        this.imageDrawableId = imageDrawableId;
        this.title = title;
        this.bgColor = bgColor;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageDrawableId() {
        return imageDrawableId;
    }

    public void setImageDrawableId(int imageDrawableId) {
        this.imageDrawableId = imageDrawableId;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
