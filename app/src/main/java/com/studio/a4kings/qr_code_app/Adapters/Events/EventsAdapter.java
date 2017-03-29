package com.studio.a4kings.qr_code_app.Adapters.Events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.studio.a4kings.qr_code_app.Models.Enums.ImageSizes;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventResponseModel;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.Helper;
import com.studio.a4kings.qr_code_app.Utils.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DUX on 15.03.2016.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    Context mContext;
    ArrayList<EventResponseModel> eventsModels = new ArrayList<>();
    OnItemClickListener mItemClickListener;

    public EventsAdapter(Context mContext, ArrayList<EventResponseModel> eventsModels) {
        this.mContext = mContext;
        this.eventsModels = eventsModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EventResponseModel eventModel = eventsModels.get(position);
        Float rating = eventModel.getRating();
        if (rating != null) {
            TextDrawable drawable = Helper.getTextDrawable(rating);
            holder.rating.setImageDrawable(drawable);
        }
        //Title
        holder.title.setText(eventModel.getTitle() != null ? eventModel.getTitle() : "");
        //Category

        holder.category.setText(eventModel.getTag() != null ? eventModel.getTag() : "");

        //EventImage
        holder.author.setText(eventModel.getAuthor());
        holder.dateCreated.setText(eventModel.getStartDate());
        if (eventModel.getMainPhoto() == null) {
            eventModel.setMainPhoto("");
        }
        ImageLoader.loadImage(mContext,
                holder.eventImage,
                Constants.PATH_TO_EVENTS_IMAGES
                        + Constants.IMAGE_SIZES.get(ImageSizes.BIG)
                        + eventModel.getMainPhoto());
        //DateCreated
        if (eventModel.getStartDate() != null)
            holder.dateCreated.setText(eventModel.getStartDate());

     /*   if(eventModel.getAddress() != null)
            holder.address.setText(eventModel.getAddress());*/

    }


    @Override
    public int getItemCount() {
        return eventsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.author_signature)
        TextView author;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.eventImg)
        CircleImageView eventImage;
        @Bind(R.id.category)
        TextView category;
        @Bind(R.id.dateCreated)
        TextView dateCreated;
        @Bind(R.id.content)
        LinearLayout content;
        @Bind(R.id.rating)
        ImageView rating;
        @Bind(R.id.address)
        TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            content.setOnClickListener(this);
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