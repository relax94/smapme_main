package com.studio.a4kings.qr_code_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.a4kings.qr_code_app.Activities.ForeignProfile;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.ImageSizes;
import com.studio.a4kings.qr_code_app.Models.Enums.PeopleEnum;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.SubscriberModel;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DUX on 23.03.2016.
 */
public class SubscriberListAdapter extends RecyclerView.Adapter<SubscriberListAdapter.ViewHolder> {

    ArrayList<? extends SubscriberModel> models;
    Context context;
    OnItemClickListener onAddBtnClickListener;
    OnItemClickListener onRemoveBtnClickListener;
    PeopleEnum peopleEnum;
    String userId;

    public SubscriberListAdapter(Context context, ArrayList<? extends SubscriberModel> models, PeopleEnum peopleEnum) {
        this.models = models;
        this.context = context;
        this.peopleEnum = peopleEnum;
        userId = PrefsManager.getInstance(this.context).get(PrefsParam.USER_ID);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SubscriberModel subscriberModel = models.get(position);

        holder.subscribe.setVisibility(View.VISIBLE);
        switch (peopleEnum) {
            case SUBSCRIBERS:
                if (!(subscriberModel.getIsSubscriber() != null && subscriberModel.getIsSubscriber()))
                    holder.subscribe.setText("Subscribe");
                else holder.subscribe.setText("Remove");
                break;
            case REQUESTS:
                holder.subscribe.setText("Add to event");
                break;
            case EVENT_USERS:
                holder.subscribe.setText("Remove from event");
                break;
            default:
                holder.subscribe.setVisibility(View.INVISIBLE);
                break;
        }
        if (subscriberModel.getUserId().equals(userId))
            holder.subscribe.setVisibility(View.INVISIBLE);
        ImageLoader.loadImage(context,
                holder.photo,
                Constants.PATH_TO_USER_IMAGES + Constants.IMAGE_SIZES.get(ImageSizes.MEDIUM) + subscriberModel.getPhoto());
        holder.fullName.setText(subscriberModel.getFullName());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.photo)
        CircleImageView photo;
        @Bind(R.id.fullName)
        TextView fullName;
        @Bind(R.id.wasOnline)
        TextView wasOnline;
        @Bind(R.id.addToFriends)
        Button subscribe;
        @Bind(R.id.sendMessage)
        Button sendMessage;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(itemView, getAdapterPosition());
                }
            });
            subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddBtnClickListener != null)
                        onAddBtnClickListener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }
    }

    private void onItemClick(View v, int position) {
        Intent transitionIntent = new Intent(context, ForeignProfile.class);
        transitionIntent.putExtra(context.getString(R.string.userId), models.get(position).getUserId());
        ImageView placeImage = (ImageView) v.findViewById(R.id.photo);
        transitionIntent.putExtra("profileImg", ((BitmapDrawable) placeImage.getDrawable()).getBitmap());
        context.startActivity(transitionIntent);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnAddClickListener(final OnItemClickListener mItemClickListener) {
        this.onAddBtnClickListener = mItemClickListener;
    }

   /* public void setOnRemoveClickListener(final OnItemClickListener mItemClickListener) {
        this.onAddBtnClickListener = mItemClickListener;
    }*/

}
