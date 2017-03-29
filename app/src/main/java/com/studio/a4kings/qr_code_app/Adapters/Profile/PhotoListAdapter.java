package com.studio.a4kings.qr_code_app.Adapters.Profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.studio.a4kings.qr_code_app.Models.Enums.ImageSizes;
import com.studio.a4kings.qr_code_app.Models.ImageEventModel;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DUX on 18.01.2016.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    Context mContext;
    ArrayList<ImageEventModel> photos;
    OnItemClickListener mItemClickListener;
    OnItemClickListener mLongItemClickListener;

    public PhotoListAdapter(Context context, ArrayList<ImageEventModel> photos) {
        this.mContext = context;
        this.photos = photos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageEventModel imageEventModel = photos.get(position);
        if(imageEventModel.getPhotoName().contains("file:///"))
            ImageLoader.loadImage(mContext, holder.image, imageEventModel.getPhotoName());
        else ImageLoader.loadImage(mContext, holder.image, Constants.PATH_TO_EVENTS_IMAGES + Constants.IMAGE_SIZES.get(ImageSizes.BIG) + imageEventModel.getPhotoName());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        @Bind(R.id.mainHolder)
        public LinearLayout placeHolder;
        @Bind(R.id.image)
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            placeHolder.setOnClickListener(this);
            placeHolder.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongItemClickListener != null) {
                mLongItemClickListener.onItemClick(itemView, getPosition());
            }
            return true;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnLongItemClick(final OnItemClickListener mItemClickListener) {
        this.mLongItemClickListener = mItemClickListener;
    }
}
