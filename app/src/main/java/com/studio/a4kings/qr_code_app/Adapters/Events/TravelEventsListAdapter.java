package com.studio.a4kings.qr_code_app.Adapters.Events;

import android.content.Context;
import android.support.annotation.BinderThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.a4kings.qr_code_app.Models.CategoryModel;
import com.studio.a4kings.qr_code_app.Models.TravelEventModel;
import com.studio.a4kings.qr_code_app.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DUX on 27.02.2016.
 */
public class TravelEventsListAdapter extends RecyclerView.Adapter<TravelEventsListAdapter.ViewHolder> {
    Context mContext;
    ArrayList<TravelEventModel> travelEventModels = new ArrayList<>();
    OnItemClickListener mItemClickListener;

    public TravelEventsListAdapter(Context context, ArrayList<TravelEventModel> travelEventModels) {
        this.travelEventModels = travelEventModels;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       TravelEventModel travelEventModel = travelEventModels.get(position);
    }

    @Override
    public int getItemCount() {
        return travelEventModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        @Bind(R.id.author_img)ImageView circleImageView;
        @Bind(R.id.placeHolder)LinearLayout placeHolder;
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
