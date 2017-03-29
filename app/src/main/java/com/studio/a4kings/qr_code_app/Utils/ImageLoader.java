package com.studio.a4kings.qr_code_app.Utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.studio.a4kings.qr_code_app.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DUX on 22.03.2016.
 */
public class ImageLoader {

    public static void loadImage(final Context context, @NotNull final ImageView imageView, String pathToImage) {
        Picasso.with(context)
                .load(pathToImage)
                .error(R.drawable.camera_50)
                .into(imageView);
    }

    public static void loadImage(final Context context, final ImageView imageView, String pathToImage, Integer placeholderResourceId) {
        if (placeholderResourceId == null)
            loadImage(context, imageView, pathToImage);
        else
            Picasso.with(context)
                    .load(pathToImage)
                    .placeholder(placeholderResourceId)
                    .error(placeholderResourceId)
                    .fit()
                    .centerCrop()
                    .into(imageView);
    }

    public static void loadImage(final Context context, final ImageView imageView, Uri uriToImage) {
        Picasso.with(context).load(uriToImage).error(R.drawable.camera_50).into(imageView);
    }
}
