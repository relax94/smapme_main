package com.studio.a4kings.qr_code_app.Presenters.Implementations;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.studio.a4kings.qr_code_app.Adapters.WallAdapter;
import com.studio.a4kings.qr_code_app.Interfaces.PostItemClickListener;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.WallService;
import com.studio.a4kings.qr_code_app.Models.Events.SignalEvent;
import com.studio.a4kings.qr_code_app.Models.Response.MongoMemberResponse;
import com.studio.a4kings.qr_code_app.Models.Response.SnapshotUploadResponse;
import com.studio.a4kings.qr_code_app.Models.Response.WallResponse;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;
import com.studio.a4kings.qr_code_app.Presenters.Interfaces.WallPresenterInterface;
import com.studio.a4kings.qr_code_app.Presenters.Interfaces.WallView;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitry Pavlenko on 05.06.2016.
 */
public class WallPresenter implements WallPresenterInterface, PostItemClickListener {

    private RecyclerView recyclerView;
    private WallService wallService;
    private WallView wallView;
    private MongoMember mongoMember;
    private WallAdapter wallAdapter;
    private List<Post> postList;
    private Context context;
    private String wallId;



    public WallPresenter(Context context, WallView wallView, String wallId) {
        this.wallService = new WallService(AppSettings.TRIP_BACKEND);
        this.wallView = wallView;
        this.wallId = wallId;
        this.context = context;
    }

    @Override
    public void setRecycleView(RecyclerView recycleView) {
        if (recycleView != null) {
            this.recyclerView = recycleView;
            this.initWall();
            this.wallView.initWall();
        }
    }

    private void initWall() {
        this.postList = new ArrayList<>();
        this.wallAdapter = new WallAdapter(this.context, this.postList, this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.recyclerView.setAdapter(this.wallAdapter);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void addPost(final Post post) {
        if (post != null) {

            postList.add(0, post);
            wallAdapter.notifyDataSetChanged();
            this.wallService.addPost(post, new Callback<WallResponse<Post>>() {
                @Override
                public void onResponse(Call<WallResponse<Post>> call, Response<WallResponse<Post>> response) {
                    if (response.body() != null) {
                        WallResponse<Post> wallResponse = response.body();
                        Post responsePost = wallResponse.getR();
                        if (wallResponse.isState() && responsePost != null) {
                            wallView.addPostResult(response.body().isState(), responsePost);
                            postList.remove(0);
                            postList.add(0, post);
                            wallAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<WallResponse<Post>> call, Throwable t) {
                    wallView.addPostResult(false, post);
                }
            });
        }
    }

    @Override
    public void addPost(String text, String creatorId, int postType, String attachmentUrl) {
        Post post = new Post();
        post.setWallId(this.wallId);
        post.setPostType(postType);
        post.setOwnerId(this.mongoMember);
        post.setAttachmentUrl(attachmentUrl);
        post.setText(text);
        post.setCreatorId(creatorId);
        addPost(post);
    }

    @Override
    public void getHistory() {
        if (!wallId.isEmpty()) {
            this.wallService.getWallPosts(wallId, new Callback<WallResponse<List<Post>>>() {
                @Override
                public void onResponse(Call<WallResponse<List<Post>>> call, Response<WallResponse<List<Post>>> response) {
                    if (response.body() != null) {
                        if (response.body().getR() != null) {
                            postList.addAll(response.body().getR());
                            wallAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<WallResponse<List<Post>>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void uploadPostImage(String imagePath) {
        this.wallService.uploadWallImage(imagePath, new Callback<SnapshotUploadResponse>() {
            @Override
            public void onResponse(Call<SnapshotUploadResponse> call, Response<SnapshotUploadResponse> response) {
                SnapshotUploadResponse uploadResponse = response.body();
                if (uploadResponse != null && uploadResponse.isState()) {
                    if (!uploadResponse.getUrl().isEmpty()) {
                        wallView.onUploadImageAttachment(true, uploadResponse.getUrl());
                        return;
                        //TODO : MAKE MESSAGES INFO
                    }
                }
                wallView.onUploadImageAttachment(false, "");
            }

            @Override
            public void onFailure(Call<SnapshotUploadResponse> call, Throwable t) {
                //TODO : MAKE MESSAGES INFO
                wallView.onUploadImageAttachment(false, "");
            }
        });
    }

    @Override
    public void getMongoMember(String memberId) {
        if (!memberId.isEmpty()) {
            wallService.getMongoMember(memberId, new Callback<MongoMemberResponse>() {
                @Override
                public void onResponse(Call<MongoMemberResponse> call, Response<MongoMemberResponse> response) {
                    if (response.body() != null && response.body().isState()) {
                        mongoMember = response.body().getMongoMember();
                        wallView.onMongoMemberLoaded(true, mongoMember);
                        return;
                    }
                    wallView.onMongoMemberLoaded(false, null);
                }

                @Override
                public void onFailure(Call<MongoMemberResponse> call, Throwable t) {
                    wallView.onMongoMemberLoaded(false, null);
                }
            });
        }
    }

    @Override
    public void handleSignalMessage(SignalEvent signalEvent) {
        if (signalEvent.getMethod().equals("addPost")) {
            final Post incomePost = signalEvent.convertBodyTo(Post.class);
            if (incomePost != null) {
                if (!incomePost.getOwnerId().getMemberId().equals(mongoMember.getMemberId()) && incomePost.getWallId().equals(wallId)) {
                    ((Activity) this.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postList.add(0, incomePost);
                            wallAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onCommentDialogClicked(int position) {
        Log.d("PostClick", "comment " + position);
        this.wallView.showCommentDialog(this.mongoMember, this.postList.get(position));
    }

    @Override
    public void onLikeButtonClicked(int position) {
        Log.d("PostClick", "like " + position);
    }
}
