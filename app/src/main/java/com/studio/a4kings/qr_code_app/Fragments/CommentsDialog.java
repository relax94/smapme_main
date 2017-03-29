package com.studio.a4kings.qr_code_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.studio.a4kings.qr_code_app.Adapters.WallAdapter;
import com.studio.a4kings.qr_code_app.Adapters.WallCommentsAdapter;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.WallService;
import com.studio.a4kings.qr_code_app.Models.Response.WallResponse;
import com.studio.a4kings.qr_code_app.Models.WallModels.Comment;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;
import com.studio.a4kings.qr_code_app.Presenters.Implementations.WallPresenter;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitry Pavlenko on 05.06.2016.
 */
public class CommentsDialog extends DialogFragment {

    @Bind(R.id.commentsRecycle)
    RecyclerView commentsRecycle;
    @Bind(R.id.postCommentEditText)
    EditText postCommentEditText;

    private List<Comment> commentList;
    private WallCommentsAdapter commentsAdapter;
    private MongoMember mongoMember;
    private Post post;
    private WallService wallService;

   public static CommentsDialog newInstance() {
        CommentsDialog f = new CommentsDialog();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.comments_dialog_layout, container, false);
        ButterKnife.bind(this, v);

        this.wallService = new WallService(AppSettings.TRIP_BACKEND);
        this.checkIncomeArguments();

        return v;
    }


    private void checkIncomeArguments(){
        Bundle bundle = getArguments();
        if(bundle != null && bundle.size() == 2){
            if(bundle.containsKey("post") && bundle.containsKey("mongoMember")) {
                this.post = (Post) bundle.getSerializable("post");
                this.mongoMember = (MongoMember) bundle.getSerializable("mongoMember");
                this.initComments();
            }
        }
    }

    private void initComments(){
        this.commentList = new ArrayList<>();
        this.commentsAdapter = new WallCommentsAdapter(getActivity(), commentList);
        this.commentsRecycle.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.commentsRecycle.setAdapter(this.commentsAdapter);
        this.commentsRecycle.setItemAnimator(new DefaultItemAnimator());
        this.getCommentsHistory(this.post.getPostId());
    }

    private void getCommentsHistory(String postId){
        if(!postId.isEmpty()) {
            this.wallService.getPostComments(postId, new Callback<WallResponse<List<Comment>>>() {
                @Override
                public void onResponse(Call<WallResponse<List<Comment>>> call, Response<WallResponse<List<Comment>>> response) {
                    WallResponse<List<Comment>> inputResponse = response.body();
                    if(inputResponse != null && inputResponse.isState()){
                        List<Comment> inputList = inputResponse.getR();
                        if(inputList != null && inputList.size() > 0){
                            commentList.addAll(inputList);
                            commentsAdapter.notifyDataSetChanged();
                        }
                    }

                }

                @Override
                public void onFailure(Call<WallResponse<List<Comment>>> call, Throwable t) {

                }
            });
        }
    }

    private void makeCommentAndSend(String commentText, int commentType, String attachmentUrl){
        final Comment comment = new Comment();
        comment.setText(commentText);
        comment.setPostId(post.getPostId());
        comment.setCommentType(commentType);
        comment.setOwnerId(mongoMember);
        comment.setAttachmentUrl(attachmentUrl);

        this.wallService.addPostComment(comment, new Callback<WallResponse<Comment>>() {
            @Override
            public void onResponse(Call<WallResponse<Comment>> call, Response<WallResponse<Comment>> response) {
                WallResponse<Comment> wallResponse = response.body();
                if(wallResponse != null && wallResponse.isState()){
                    commentList.add(comment);
                    commentsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<WallResponse<Comment>> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.postCommentButton)
    public void onAddCommentButtonClicked(){
        String commentText = postCommentEditText.getText().toString();
        if(commentText.length() > 0) {
            this.makeCommentAndSend(commentText, 0, "");
            postCommentEditText.setText("");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Material_Light);

    }



}
