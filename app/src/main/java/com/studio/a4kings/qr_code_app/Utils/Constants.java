package com.studio.a4kings.qr_code_app.Utils;

import com.studio.a4kings.qr_code_app.Models.Enums.ImageSizes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DUX on 24.11.2015.
 */
public class Constants {
    public static final String SITE_URL = "http://smapme.azurewebsites.net/";
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int GALLERY_REQUEST = 2;
    public static final int CROP_IMAGE_REQUEST = 3;
    public static final String PATH_TO_FILES = SITE_URL + "files/";
    public static final String IMAGE_FOLDER = "images/";
    public static final String EVENT_IMAGE_FOLDER = "images/events/";
    public static final Map<ImageSizes, String> IMAGE_SIZES;

    public static final String PATH_TO_EVENTS_IMAGES = PATH_TO_FILES + EVENT_IMAGE_FOLDER;
    public static final String PATH_TO_USER_IMAGES = PATH_TO_FILES + IMAGE_FOLDER;

    static {
        IMAGE_SIZES = new HashMap<>();
        IMAGE_SIZES.put(ImageSizes.BIG, "big_");
        IMAGE_SIZES.put(ImageSizes.EXTRASMALL, "extrasmall_");
        IMAGE_SIZES.put(ImageSizes.SMALL, "small_");
        IMAGE_SIZES.put(ImageSizes.MEDIUM, "medium_");
    }

    public static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.8f;
    public static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    public static final int ALPHA_ANIMATIONS_DURATION = 200;
}
