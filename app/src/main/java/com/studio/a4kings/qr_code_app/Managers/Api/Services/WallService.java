package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import com.studio.a4kings.qr_code_app.Api.Interface.WallInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.Notification.Notification;
import com.studio.a4kings.qr_code_app.Models.WallModels.Comment;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;
import com.studio.a4kings.qr_code_app.Models.Response.MongoMemberResponse;
import com.studio.a4kings.qr_code_app.Models.Response.SnapshotUploadResponse;
import com.studio.a4kings.qr_code_app.Models.Response.WallResponse;
import com.studio.a4kings.qr_code_app.Models.WallModels.Post;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * Created by Dmitry Pavlenko on 01.06.2016.
 */
public class WallService extends ApiManager implements ApiService {

    private WallInterface wallInterface;

    public WallService(String baseUrl) {
        super(baseUrl);
        this.wallInterface = getApiInterface(WallInterface.class);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }

    public void addPost(Post post, Callback<WallResponse<Post>> callback) {
        this.wallInterface.addWallPost(post).enqueue(callback);
    }

    public void getWallPosts(String wallId, Callback<WallResponse<List<Post>>> callback) {
        HashMap<String, String> args = new HashMap<>();
        args.put("wall_id", wallId);
        this.wallInterface.getWallPosts(args).enqueue(callback);
    }


    public void synchronizedMember(MongoMember member, Callback<MongoMemberResponse> callback) {
        this.wallInterface.synchronizeMember(member).enqueue(callback);
    }

    public void getMongoMember(String memberId, Callback<MongoMemberResponse> callback) {
        this.wallInterface.getMongoMember(memberId).enqueue(callback);
    }

    public void uploadWallImage( String uri, Callback<SnapshotUploadResponse> uploadCallback){
        File file = new File(uri);
        if(file.exists()){


            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

            // add another part within the multipart request
            RequestBody description =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "some");


            this.wallInterface.uploadWallImage(description, body).enqueue(uploadCallback);

        }
    }

    public void addPostComment(Comment comment, Callback<WallResponse<Comment>> callback){
        this.wallInterface.addPostComment(comment).enqueue(callback);
    }

    public void getPostComments(String postId, Callback<WallResponse<List<Comment>>> callback){
        this.wallInterface.getPostComments(postId).enqueue(callback);
    }

    public void getAllNotification(String ownerId, Callback<WallResponse<List<Notification>>> callback){
        this.wallInterface.getAllNotificationsByOwner(ownerId).enqueue(callback);
    }
}
