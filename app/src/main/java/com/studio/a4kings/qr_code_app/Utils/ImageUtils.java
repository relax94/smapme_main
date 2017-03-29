package com.studio.a4kings.qr_code_app.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by DUX on 13.01.2016.
 */

public class ImageUtils {
    Context context;
    Fragment fragment;

    public ImageUtils(Context context, Fragment fragment) {
        this.fragment = fragment;
        this.context = context;
    }

    public ImageUtils(Context context) {
        this.fragment = null;
        this.context = context;
    }

    String mCurrentPhotoPath;
    Uri imageUri;
    int width;
    int height;
    int[] src;

    public boolean hasCamera() {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    // method to check you have Camera Apps
    public boolean hasDefaultCameraApp(String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                if (fragment != null)
                    fragment.startActivityForResult(takePictureIntent, Constants.REQUEST_TAKE_PHOTO);
                else
                    ((Activity) context).startActivityForResult(takePictureIntent, Constants.REQUEST_TAKE_PHOTO);
            }
        }
    }

    public Bitmap decodeUriAsBitmap() {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(((Activity) context).getContentResolver().openInputStream(getPathUri()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    public void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if (fragment != null)
            fragment.startActivityForResult(photoPickerIntent, Constants.GALLERY_REQUEST);
        else
            ((Activity) context).startActivityForResult(photoPickerIntent, Constants.GALLERY_REQUEST);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "SmapMe");
        boolean success = true;
        File image;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            File storageDir = Environment.getExternalStoragePublicDirectory("SmapMe");
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = image.getAbsolutePath();
            imageUri = getPathUri();
        } else {
            // Do something else on failure
            image = null;
        }
        return image;
    }

    public String getmCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public void setmCurrentPhotoPath(String mCurrentPhotoPath) {
        this.mCurrentPhotoPath = mCurrentPhotoPath;
    }

   // String tempPath;

  /*  public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }*/

    public void doCrop(int targetW, int targetH, int aspectX, int aspectY) {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "SmapMe");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(getPathUri(), "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", aspectX);
            cropIntent.putExtra("aspectY", aspectY);
            cropIntent.putExtra("outputX", targetW);
            cropIntent.putExtra("outputY", targetH);
            cropIntent.putExtra("scale", true);
            //tempPath = Environment.getExternalStorageDirectory().toString() + "/SmapMe/" + data.toString() + ".jpg";
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPathUri());
            cropIntent.putExtra("return-data", false);
            cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            cropIntent.putExtra("noFaceDetection", true); // no face detection
            //start the activity - we handle returning in onActivityResult
            if (fragment != null)
                fragment.startActivityForResult(cropIntent, Constants.CROP_IMAGE_REQUEST);
            else
                ((Activity) context).startActivityForResult(cropIntent, Constants.CROP_IMAGE_REQUEST);
        }
    }

    public void doCrop(ImageView mBigImage, int aspectX, int aspectY) {
        int targetW = mBigImage.getWidth();
        int targetH = mBigImage.getHeight();
        doCrop(targetW, targetH, aspectX, aspectY);
    }

    public void doCrop(int target, int aspectX, int aspectY) {
        doCrop(target, target, aspectX, aspectY);
    }


    public Uri getPathUri() {
        return Uri.parse("file:///" + mCurrentPhotoPath);
    }

   /* public Uri getTempUri() {
        return Uri.parse("file:///" + tempPath);
    }*/

    /*public void deleteTempFile() {
        try {
            // delete the original file
            new File(tempPath).delete();

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }*/

}
