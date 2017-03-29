package com.studio.a4kings.qr_code_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.a4kings.qr_code_app.Interfaces.PostItemClickListener;
import com.studio.a4kings.qr_code_app.Models.WallModels.Comment;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dmitry Pavlenko on 05.06.2016.
 */
public class WallCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Comment> posts;
    private PostItemClickListener postItemClickListener;

    public WallCommentsAdapter(Context context, List<Comment> posts) {
        this.context = context;
        this.posts = posts;
       // this.postItemClickListener = postItemClickListener;
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

        @Bind(R.id.postCommentIV)
        ImageView postCommentIV;

        @Bind(R.id.postLikeIV)
        ImageView postLikeIV;

        @Bind(R.id.postCommentTV)
        TextView postCommentTV;

        @Bind(R.id.postLikeTV)
        TextView postLikeTV;

        public PostTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            postCommentIV.setVisibility(View.INVISIBLE);
            postLikeIV.setVisibility(View.INVISIBLE);
            postCommentTV.setVisibility(View.INVISIBLE);
            postLikeTV.setVisibility(View.INVISIBLE);
        }

      /*  @OnClick(R.id.pos)
        public void onPostCommentClick(){
          //  postItemClickListener.onCommentDialogClicked(getAdapterPosition());
        }

        @OnClick(R.id.postLike)
        public void onPostLikeClick(){
         //   postItemClickListener.onLikeButtonClicked(getAdapterPosition());
        }*/
    }

    public class PostImageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.postDate)
        TextView postDate;

        @Bind(R.id.postDescription)
        TextView postDescription;

        @Bind(R.id.postImageDescription)
        ImageView postImageDecription;

        @Bind(R.id.postIcon)
        ImageView postIcon;

        @Bind(R.id.postTitle)
        TextView postTitle;

        public PostImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
            return new PostTextViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card_image, parent, false);
            return new PostImageViewHolder(itemView);
        }

    }

    private void openCommentsDialogFragment(){
      /*  FragmentTransaction ft = this.context.
        CommentsDialog newFragment = CommentsDialog.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(ft, "slideshow");*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Comment post = posts.get(position);
        MongoMember member = post.getOwnerId();

        if(post.getCommentType() == 0) {
            PostTextViewHolder  holderText = (PostTextViewHolder) holder;
            holderText.postTitle.setText(String.format("%s %s", member.getFirstName(), member.getLastName()));
            holderText.postDate.setText(post.getPostedDate());
            holderText.postDescription.setText(post.getText());
            Picasso.with(context).load(String.format("http://smapme.com/files/images/small_%s", post.getOwnerId().getIconUrl())).into(holderText.postIcon);
        }
        else if (post.getCommentType() == 1) {
            PostImageViewHolder holderImage = (PostImageViewHolder)holder;
            holderImage.postTitle.setText(String.format("%s %s", member.getFirstName(), member.getLastName()));
            holderImage.postDate.setText(post.getPostedDate());
            holderImage.postDescription.setText(post.getText());
            Picasso.with(context).load(String.format("http://smapme.com/files/images/small_%s", post.getOwnerId().getIconUrl())).into(holderImage.postIcon);
            Picasso.with(context).load(String.format("%s%s?size=150", AppSettings.TRIP_BACKEND, post.getAttachmentUrl())).into(holderImage.postImageDecription);
        }

    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return posts.get(position).getCommentType();
    }
}