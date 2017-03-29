package com.studio.a4kings.qr_code_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.a4kings.qr_code_app.Interfaces.NotifyItemClickListener;
import com.studio.a4kings.qr_code_app.Models.Notification.Notification;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dmitry Pavlenko on 07.06.2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.PostTextViewHolder> {

    private Context context;
    private List<Notification> notifications;
    private NotifyItemClickListener notifyItemClickListener;


    public NotificationAdapter(Context context, List<Notification> notifications, NotifyItemClickListener notifyItemClickListener) {
        this.context = context;
        this.notifications = notifications;
        this.notifyItemClickListener = notifyItemClickListener;
    }

    public class PostTextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.postDate)
        TextView postDate;

        @Bind(R.id.postDescription)
        TextView postDescription;

        @Bind(R.id.postIcon)
        ImageView postIcon;

        @Bind(R.id.postTitle)
        TextView postTitle;

        public PostTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            postTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemClickListener.onItemHolderClicked(getAdapterPosition());
                }
            });
        }

    }


    @Override
    public PostTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        return new PostTextViewHolder(itemView);
    }

    private void openCommentsDialogFragment() {
      /*  FragmentTransaction ft = this.context.
        CommentsDialog newFragment = CommentsDialog.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(ft, "slideshow");*/
    }

    @Override
    public void onBindViewHolder(PostTextViewHolder holderText, int position) {
        Notification notification = notifications.get(position);
        MongoMember member = notification.getSender();
        if (member != null) {
            holderText.postTitle.setText(String.format("%s %s", member.getFirstName(), member.getLastName()));
            holderText.postDate.setText(notification.getPostedDate());
            holderText.postDescription.setText(notification.getText());
            Picasso.with(context).load(String.format("http://smapme.com/files/images/small_%s", member.getIconUrl())).into(holderText.postIcon);
        }
    }

    @Override
    public int getItemCount() {
        return this.notifications.size();
    }



  /*  @Override
    public int getItemViewType(int position) {
        return posts.get(position).getPostType();
    }*/
}
