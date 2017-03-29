package com.studio.a4kings.qr_code_app.Adapters.Category;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.a4kings.qr_code_app.Models.CategoryModel;
import com.studio.a4kings.qr_code_app.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DUX on 10.02.2016.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<CategoryModel> category = new ArrayList<>();
    OnItemClickListener mItemClickListener;

    public CategoryListAdapter(Context context, ArrayList<CategoryModel> category) {
        this.category = category;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoryModel categoryModel = category.get(position);
        holder.placeName.setText(categoryModel.getTitle());
        holder.placeImage.setImageResource(categoryModel.getImageDrawableId());
        holder.placeHolder.setBackgroundColor(categoryModel.getBgColor());
    }

    @Override
    public int getItemCount() {
        return category.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.mainHolder) public LinearLayout placeHolder;
        @Bind(R.id.placeName) public TextView placeName;
        @Bind(R.id.placeImage) public ImageView placeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            placeHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
