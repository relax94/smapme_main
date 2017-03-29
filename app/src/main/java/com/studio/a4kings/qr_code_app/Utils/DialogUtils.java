package com.studio.a4kings.qr_code_app.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.studio.a4kings.qr_code_app.R;

/**
 * Created by DUX on 03.05.2016.
 */
public class DialogUtils {
    Context context;
    AlertDialog dialog;
    ImageUtils imageUtils;

    public DialogUtils(Context context, ImageUtils imageUtils) {
        this.context = context;
        this.imageUtils = imageUtils;
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(R.array.takePhoto, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        imageUtils.openCamera();
                        break;
                    case 1:
                        imageUtils.openGallery();
                        break;
                }
            }
        });
        dialog = builder.create();
    }

    public void showDialog() {
        if (dialog == null)
            buildDialog();
        dialog.show();
    }
}
